package cn.linshiyou.financialFinalContest.swap.service.impl;

import cn.linshiyou.financialFinalContest.swap.config.RabbitMQConfig;
import cn.linshiyou.financialFinalContest.swap.dao.entity.Goods;
import cn.linshiyou.financialFinalContest.swap.dao.entity.Swap;
import cn.linshiyou.financialFinalContest.swap.dao.entity.SwapBill;
import cn.linshiyou.financialFinalContest.swap.dao.entity.SwapMq;
import cn.linshiyou.financialFinalContest.swap.dao.mapper.GoodsMapper;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    private GoodsMapper goodsMapper;

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

        SwapBill swapList = new SwapBill();
        swapList.setStatusId(3);
        swapList.setCreateTime(new Date());
        swapList.setUpdateTime(new Date());
        swapList.setUserAid(userAId);
        swapList.setUserBid(userBId);
        String description =  goodsMapper.selectById(swaps.get(0).getGoodId()).getName()+"等物品";
        swapList.setDescription(description);
        swapBillMapper.insert(swapList);

        for (Swap swap: swaps){
            swap.setListId(swapList.getId());
            swapMapper.insert(swap);
        }

        // MQ消息体
        SwapMq swapMq = new SwapMq();
        swapMq.setDescription(description);
        swapMq.setUserAid(userAId);
        swapMq.setUserBid(userBId);
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
    public void stageTwoSwap(SwapBill swapBill) {

        SwapBill billData = swapBillMapper.selectById(swapBill.getId());

        swapBill.setUpdateTime(new Date());
        swapBillMapper.update(swapBill, new LambdaUpdateWrapper<SwapBill>()
                .eq(SwapBill::getId, swapBill.getId())
                .set(SwapBill::getStatusId, swapBill.getStatusId())
                .set(SwapBill::getUpdateTime, swapBill.getUpdateTime()));

        // MQ消息体

        List<Swap> swaps = swapMapper.selectList(new LambdaQueryWrapper<Swap>()
                .eq(Swap::getListId, swapBill.getId()));
        SwapMq swapMq = new SwapMq();
        swapMq.setDescription(billData.getDescription());
        swapMq.setUserAid(billData.getUserAid());
        swapMq.setUserBid(billData.getUserBid());
        swapMq.setSwaps(swaps);

        String swapListJson = JSON.toJSONString(swapMq);


        // 发送到MQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.SWAP_TWO_QUEUE, swapListJson);

    }

    /**
     * 交易第三阶段
     * @param swapList
     */
    @Override
    @Transactional
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

            goodsMapper.update(goods, new LambdaUpdateWrapper<Goods>()
                    .eq(Goods::getId, goods.getId())
                    .set(Goods::getUserId, goods.getUserId())
                    .set(Goods::getStatusId, 13));
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
//        Page<SwapBill> swapBills = (Page<SwapBill>) swapBillMapper.selectList(
//                new LambdaQueryWrapper<SwapBill>()
//                        .eq(SwapBill::getUserAid, userId)
//                        .or()
//                        .eq(SwapBill::getUserBid, userId)
//                        .orderByAsc(SwapBill::getUpdateTime));


        return swapBillVos;
    }

    @Override
    public List<GoodsDTO> selectSwapLit(Long swapBillId) {
        List<Swap> swapList = swapMapper.selectList(new LambdaQueryWrapper<Swap>().eq(Swap::getListId, swapBillId));
        List<Long> longs = new ArrayList<>();
        swapList.forEach(swap -> longs.add(swap.getGoodId()));
        List<GoodsDTO> goodsDTOList = goodsFeign.getGoods(longs);


        return goodsDTOList;
    }


}
