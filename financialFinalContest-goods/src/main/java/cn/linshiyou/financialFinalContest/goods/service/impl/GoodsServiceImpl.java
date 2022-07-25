package cn.linshiyou.financialFinalContest.goods.service.impl;


import cn.linshiyou.financialFinalContest.common.feign.CodeStateFeign;
import cn.linshiyou.financialFinalContest.common.feign.CommonFeign;
import cn.linshiyou.financialFinalContest.goods.config.RabbitMQConfig;
import cn.linshiyou.financialFinalContest.goods.convert.GoodsConvert;
import cn.linshiyou.financialFinalContest.goods.dao.dto.GoodsDTO;
import cn.linshiyou.financialFinalContest.goods.dao.entity.Goods;
import cn.linshiyou.financialFinalContest.goods.dao.mapper.GoodsMapper;
import cn.linshiyou.financialFinalContest.goods.service.GoodsService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LJ
 * @since 2022-07-23
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private CodeStateFeign codeStateFeign;

    @Autowired
    private CommonFeign commonFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 添加新的物品
     * @param good 物品类
     * @param file 图片图片
     * @return
     */
    @Override
    public void add(Goods good, MultipartFile file) {

        String imageUrl = (String) commonFeign.fileUpload(file).getData();
        good.setImage(imageUrl);
        goodsMapper.insert(good);

        // 发送到MQ中
        GoodsDTO goodsDTO = GoodsConvert.INSTANCE.goods2DTO(good);

        LinkedHashMap<String, String> data = (LinkedHashMap<String, String>) codeStateFeign.getById(goodsDTO.getTypeId()).getData();
        goodsDTO.setTypeName(data.get("name"));
        //手动序列化发送
        rabbitTemplate.convertAndSend(RabbitMQConfig.AD_GOODS_QUEUE, JSON.toJSONString(goodsDTO));

    }

    /**
     *  根据条件查询GoodsDTO
     * @param startPage
     * @param sizePage
     * @param name
     * @param typeId
     * @param userId
     * @return
     */
    @Override
    public Page<Goods> getBycondition(int startPage, int sizePage, String name, Integer typeId, Integer userId){

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(name)){
            lambdaQueryWrapper.eq(Goods::getName, name);
        }
        if (typeId!=null){
            lambdaQueryWrapper.eq(Goods::getTypeId,typeId);
        }
        if (userId!=null){
            lambdaQueryWrapper.eq(Goods::getUserId, userId);
        }
        PageHelper.startPage(startPage, sizePage);
        Page<Goods> goodsPage = (Page<Goods>) goodsMapper.selectList(lambdaQueryWrapper);

        return goodsPage;

    }



}
