package cn.linshiyou.financialFinalContest.search.listener;

import cn.linshiyou.financialFinalContest.search.config.RabbitMQConfig;
import cn.linshiyou.financialFinalContest.search.pojo.GoodsDTO;
import cn.linshiyou.financialFinalContest.search.service.SearchService;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: LJ
 * @Description: 监听MQ, 如果有数据就插入到Es中
 */
@Component
public class GoodsEsListener {

    @Autowired
    private SearchService esGoodsService;

    /**
     * 添加数据
     * @param goodsDTOString
     */
    @RabbitListener(queues = RabbitMQConfig.AD_GOODS_QUEUE)
    public void receiveAddMessage(String goodsDTOString){

        GoodsDTO goodsDTO = JSON.parseObject(goodsDTOString, GoodsDTO.class);
        esGoodsService.addOrUpdateEsGoodsDTO(goodsDTO);
    }
    /**
     * 修改数据
     */
    @RabbitListener(queues = RabbitMQConfig.UPDATE_GOODS_QUEUE)
    public void receiveUpdateMessage(String goodsDTOString){

        GoodsDTO goodsDTO = JSON.parseObject(goodsDTOString, GoodsDTO.class);
//        esGoodsService.UpdateEsGoodsDTO(goodsDTO);
        esGoodsService.addOrUpdateEsGoodsDTO(goodsDTO);
    }
    /**
     * 删除数据
     */
    @RabbitListener(queues = RabbitMQConfig.DELETE_GOODS_QUEUE)
    public void receiveDeleteMessage(Long id){

        esGoodsService.deleteDoc(id);
    }

}
