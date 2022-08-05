package cn.linshiyou.financialFinalContest.msm.receiver;

import cn.linshiyou.financialFinalContest.msm.config.RabbitMQConfig;
import cn.linshiyou.financialFinalContest.msm.pojo.MsmVo;
import cn.linshiyou.financialFinalContest.msm.pojo.SwapMq;
import cn.linshiyou.financialFinalContest.msm.service.MsmService;
import cn.linshiyou.financialFinalContest.msm.utils.MqConst;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsmReceiver {

    @Autowired
    private MsmService smsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
            key = {MqConst.ROUTING_MSM_ITEM}
    ))
    public void send(MsmVo msmVo, Message message, Channel channel) {
        smsService.send(msmVo);
    }

    /**
     * 交易第一阶段
     */
    @RabbitListener(queues = RabbitMQConfig.SWAP_ONE_QUEUE)
    public void receiveDeleteMessage(String  id){

        SwapMq swapMq = JSON.parseObject(id, SwapMq.class);

    }






}
