package cn.linshiyou.financialFinalContest.search.listener;

import cn.linshiyou.financialFinalContest.search.config.RabbitMQConfig;
import cn.linshiyou.financialFinalContest.search.pojo.GoodsDTO;
import cn.linshiyou.financialFinalContest.search.service.EsGoodsService;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: LJ
 * @Description: 监听MQ, 如果有数据就插入到Es中
 */
@Component
public class GoodsAddEsListener {

    @Autowired
    private EsGoodsService esGoodsService;

    @RabbitListener(queues = RabbitMQConfig.AD_GOODS_QUEUE)
    public void receiveMessage(String goodsDTOString){
        GoodsDTO goodsDTO = JSON.parseObject(goodsDTOString, GoodsDTO.class);
        esGoodsService.addOrUpdateEsGoodsDTO(goodsDTO);
    }

}
