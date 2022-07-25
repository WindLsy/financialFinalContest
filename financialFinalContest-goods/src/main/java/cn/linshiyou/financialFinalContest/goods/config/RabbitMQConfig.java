package cn.linshiyou.financialFinalContest.goods.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: LJ
 * @Description: RabbitMQ配置
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 定义队列名称
     */
    public static final String AD_GOODS_QUEUE="ad_goods_queue";

    /**
     * 声明队列
     */
    @Bean(AD_GOODS_QUEUE)
    public Queue AD_GOODS_QUEUE(){
        return new Queue(AD_GOODS_QUEUE);
    }

}
