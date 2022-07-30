package cn.linshiyou.financialFinalContest.goods.service.impl;


import cn.linshiyou.financialFinalContest.common.feign.CodeStateFeign;
import cn.linshiyou.financialFinalContest.common.feign.CommonFeign;
import cn.linshiyou.financialFinalContest.goods.config.RabbitMQConfig;
import cn.linshiyou.financialFinalContest.goods.convert.GoodsConvert;
import cn.linshiyou.financialFinalContest.goods.dao.dto.GoodsDTO;
import cn.linshiyou.financialFinalContest.goods.dao.entity.Goods;
import cn.linshiyou.financialFinalContest.goods.dao.mapper.GoodsMapper;
import cn.linshiyou.financialFinalContest.goods.service.GoodsService;
import cn.linshiyou.financialFinalContest.goods.util.Constant;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        good.setStatusId(12);
        goodsMapper.insert(good);


        GoodsDTO goodsDTO = goodsToDTO(good);
        String goodsDTOString = JSON.toJSONString(goodsDTO);
        // 发送到redis
        stringRedisTemplate.opsForValue().set(Constant.REDIS_PRE+goodsDTO.getId(), goodsDTOString);
        stringRedisTemplate.expire(Constant.REDIS_PRE+ goodsDTO.getId(), Constant.REDIS_EXPIRE, TimeUnit.HOURS);
        // 发送到MQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.AD_GOODS_QUEUE, goodsDTOString);

    }


    /**
     * 修改
     * @param entity
     * @return
     */
    @Override
    public boolean updateById(Goods good) {

        goodsMapper.updateById(good);

        GoodsDTO goodsDTO = goodsToDTO(good);
        String goodsDTOString = JSON.toJSONString(goodsDTO);
        // 发送到redis
        stringRedisTemplate.opsForValue().set(Constant.REDIS_PRE+goodsDTO.getId(), goodsDTOString);
        stringRedisTemplate.expire(Constant.REDIS_PRE+ goodsDTO.getId(), Constant.REDIS_EXPIRE, TimeUnit.HOURS);
        // 发送到MQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.AD_GOODS_QUEUE, goodsDTOString);

        return true;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public GoodsDTO getByIdSelf(Serializable id) {

        // TODO 先从redis中获取，如果没有才查数据库
        GoodsDTO goodsDTO;
        String goodDtoString = stringRedisTemplate.opsForValue().get(Constant.REDIS_PRE + id);

        if (!StringUtils.isEmpty(goodDtoString) && !goodDtoString.equals("null")){
            goodsDTO = JSON.parseObject(goodDtoString, GoodsDTO.class);
        }else {
            Goods goods = goodsMapper.selectById(id);
            if (goods==null){
                stringRedisTemplate.opsForValue().set(Constant.REDIS_PRE+id, "null");
                return null;
            }
            goodsDTO = goodsToDTO(goods);
            String goodsDTOString = JSON.toJSONString(goodsDTO);
            // 发送到redis
            stringRedisTemplate.opsForValue().set(Constant.REDIS_PRE+goodsDTO.getId(), goodsDTOString);
            stringRedisTemplate.expire(Constant.REDIS_PRE+ id, Constant.REDIS_EXPIRE, TimeUnit.HOURS);
        }

        return goodsDTO;
    }

    /**
     * 根据用户id和欲交换的物品价值生成最小物品集合
     * @param userId
     * @param price
     * @return
     */
    @Override
    public List<Goods> getLeastContainer(Long userId, Double price) {

        List<Goods> goodsList = new ArrayList<>();

        List<Goods> goodsListBydata = goodsMapper.selectList(new LambdaQueryWrapper<Goods>().eq(Goods::getUserId, userId).orderByAsc(Goods::getPrice));

        double min = 0.0D;
        for (Goods goods: goodsList){
            if (min<price){
                min += goods.getPrice();
                goodsList.add(goods);
            }else {
                break;
            }
        }

        while (min>price){
            Goods removeGoods = goodsList.remove(0);
            min -= removeGoods.getPrice();
            if (min<price){
                goodsList.add(0, removeGoods);
                break;
            }
        }


        return goodsList;
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {

        goodsMapper.deleteById(id);
        // TODO 从redis中删除
        stringRedisTemplate.opsForValue().set(Constant.REDIS_PRE+id, "null");
        stringRedisTemplate.expire(Constant.REDIS_PRE+ id, Constant.REDIS_EXPIRE, TimeUnit.HOURS);
        // 发送到MQ中
        rabbitTemplate.convertAndSend(RabbitMQConfig.DELETE_GOODS_QUEUE, id);

        return false;
    }


    /**
     * 将good 转变为 DTO
     * @param good
     * @return
     */
    private GoodsDTO goodsToDTO(Goods good){
        // 完整数据
        GoodsDTO goodsDTO = GoodsConvert.INSTANCE.goods2DTO(good);
        LinkedHashMap<String, String> data = (LinkedHashMap<String, String>) codeStateFeign.getById(goodsDTO.getTypeId()).getData();
        goodsDTO.setTypeName(data.get("name"));
        LinkedHashMap<String, String> status = (LinkedHashMap<String, String>) codeStateFeign.getById(goodsDTO.getStatusId()).getData();
        goodsDTO.setStatusName(status.get("name"));

        return goodsDTO;
    }
}
