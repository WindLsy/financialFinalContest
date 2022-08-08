package cn.linshiyou.financialFinalContest.swap.service.impl;

import cn.linshiyou.financialFinalContest.swap.config.RabbitMQConfig;
import cn.linshiyou.financialFinalContest.swap.dao.entity.Goods;
import cn.linshiyou.financialFinalContest.swap.dao.entity.Swap;
import cn.linshiyou.financialFinalContest.swap.dao.entity.SwapBill;
import cn.linshiyou.financialFinalContest.swap.dao.entity.SwapMq;
import cn.linshiyou.financialFinalContest.swap.dao.mapper.SwapBillMapper;
import cn.linshiyou.financialFinalContest.swap.dao.mapper.SwapMapper;
import cn.linshiyou.financialFinalContest.swap.dao.vo.GoodsDTO;
import cn.linshiyou.financialFinalContest.swap.dao.vo.SwapBillVo;
import cn.linshiyou.financialFinalContest.swap.feign.GoodsFeign;
import cn.linshiyou.financialFinalContest.swap.service.SwapService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LJ
 * @since 2022-07-28
 */
@Service
public class SwapServiceImpl extends ServiceImpl<SwapMapper, Swap> implements SwapService {

    @Autowired
    private SwapMapper swapMapper;

    @Autowired
    private SwapBillMapper swapBillMapper;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private GoodsFeign goodsFeign;


    /**
     * 交易第一阶段
     * @param swapList
     */
    @Transactional
    @Override
    public void stageOneSwap(Long userAId, Long userBId, List<Swap> swaps) {

        SwapBill swapBill = new SwapBill();
        swapBill.setStatusId(3);
        swapBill.setCreateTime(new Date());
        swapBill.setUpdateTime(new Date());
        swapBill.setUserAid(userAId);
        swapBill.setUserBid(userBId);
        //根据id获取物品名称
        LinkedHashMap<String, String> data = (LinkedHashMap<String, String>) goodsFeign.getById(swaps.get(0).getGoodId()).getData();
        String description =  data.get("name")+"等物品";
        swapBill.setDescription(description);
        swapBillMapper.insert(swapBill);

        for (Swap swap: swaps){
            swap.setListId(swapBill.getId());
            swapMapper.insert(swap);
        }

        // 对所有参与交换的物品进行冻结
        changeGoodsStatus(15, swapBill);

        // MQ消息体
        SwapMq swapMq = new SwapMq();
        swapMq.setDescription(description);
        swapMq.setUserAid(userAId);
        swapMq.setUserBid(userBId);
        swapMq.setStatusId(3);
        swapMq.setSwaps(swaps);
        String swapListJson = JSON.toJSONString(swapMq);
        // 发送到MQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.SWAP_ONE_QUEUE, swapListJson);

    }


    /**
     * 交易第二阶段
     * @param swapList
     */
    @Override
    @GlobalTransactional
    public void stageTwoSwap(SwapBill swapBill) {

        SwapBill billData = swapBillMapper.selectById(swapBill.getId());

        swapBill.setUpdateTime(new Date());
        swapBillMapper.update(swapBill, new LambdaUpdateWrapper<SwapBill>()
                .eq(SwapBill::getId, swapBill.getId())
                .set(SwapBill::getStatusId, swapBill.getStatusId())
                .set(SwapBill::getUpdateTime, swapBill.getUpdateTime()));


        // 如果拒绝，则对所有物品进行解冻
        if (swapBill.getStatusId()==6){
            changeGoodsStatus(12, swapBill);
        }

        // MQ消息体
        List<Swap> swaps = swapMapper.selectList(new LambdaQueryWrapper<Swap>()
                .eq(Swap::getListId, swapBill.getId()));
        SwapMq swapMq = new SwapMq();
        swapMq.setDescription(billData.getDescription());
        swapMq.setUserAid(billData.getUserAid());
        swapMq.setUserBid(billData.getUserBid());
        swapMq.setStatusId(billData.getStatusId());
        swapMq.setSwaps(swaps);
        String swapListJson = JSON.toJSONString(swapMq);

        // 发送到MQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.SWAP_TWO_QUEUE, swapListJson);

    }

    /**
     * 交易第三阶段
     * 使用XA分布式事务
     * @param swapList
     */
    @Override
    @GlobalTransactional
    public void stageThreeSwap(SwapBill swapBill) {

        swapBill.setStatusId(5);
        swapBill.setUpdateTime(new Date());
        swapBillMapper.update(swapBill, new LambdaUpdateWrapper<SwapBill>()
                .eq(SwapBill::getId, swapBill.getId())
                .set(SwapBill::getStatusId, swapBill.getStatusId())
                .set(SwapBill::getUpdateTime, swapBill.getUpdateTime()));

        List<Swap> swaps = swapMapper.selectList(new LambdaQueryWrapper<Swap>()
                .eq(Swap::getListId, swapBill.getId()));

        //交换物品同时下架
        for (Swap swap: swaps){
            Goods goods = new Goods();
            goods.setId(swap.getGoodId());
            goods.setUserId(swap.getUserFinalId());
            goods.setStatusId(13);

            goodsFeign.updateById(goods);
        }

        // MQ消息体
        SwapBill billData = swapBillMapper.selectById(swapBill.getId());
        SwapMq swapMq = new SwapMq();
        swapMq.setDescription(billData.getDescription());
        swapMq.setUserAid(billData.getUserAid());
        swapMq.setUserBid(billData.getUserBid());
        swapMq.setSwaps(swaps);
        String swapListJson = JSON.toJSONString(swapMq);
        // 发送到MQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.SWAP_THREE_QUEUE, swapListJson);


    }

    /**
     * 查询交易记录
     * @param startPage
     * @param sizePage
     * @param userId
     * @return
     */
    @Override
    public Page<SwapBillVo> selectByUserid(int startPage, int sizePage, Long userId) {

        PageHelper.startPage(startPage, sizePage);
        Page<SwapBillVo> swapBillVos = (Page<SwapBillVo>) swapBillMapper.selectByUserid(userId);

        return swapBillVos;
    }

    /**
     * 查询具体交易物品
     * @param swapBillId
     * @return
     */
    @Override
    public List<GoodsDTO> selectSwapLit(Long swapBillId) {
        List<Swap> swapList = swapMapper.selectList(new LambdaQueryWrapper<Swap>().eq(Swap::getListId, swapBillId));
        List<Long> longs = new ArrayList<>();
        swapList.forEach(swap -> longs.add(swap.getGoodId()));
        List<GoodsDTO> goodsDTOList = goodsFeign.getGoods(longs);

        return goodsDTOList;
    }

    /**
     * 更改物品状态
     * @param statusId
     */
    private void changeGoodsStatus(int statusId, SwapBill swapBill){

        List<Swap> swapList = swapMapper.selectList(new LambdaQueryWrapper<Swap>().eq(Swap::getListId, swapBill.getId()));

        List<Goods> goodsList = new ArrayList<>();
        // 如果拒绝，则对所有物品进行解冻
        for (Swap swap: swapList){
            Goods goods = new Goods();
            goods.setId(swap.getGoodId());
            goods.setStatusId(statusId);
            goodsList.add(goods);
        }

        goodsFeign.updateByList(goodsList);

    }


}
