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
     * 添加数据
     */
    public static final String AD_GOODS_QUEUE="ad_goods_queue";
    /**
     * 修改数据
     */
    public static final String UPDATE_GOODS_QUEUE="update_goods_queue";

    /**
     * 删除数据
     */
    public static final String DELETE_GOODS_QUEUE="delete_goods_queue";

    /**
     * 声明队列
     */
    @Bean(AD_GOODS_QUEUE)
    public Queue AD_GOODS_QUEUE(){
        return new Queue(AD_GOODS_QUEUE);
    }

    /**
     * 声明队列
     */
    @Bean(UPDATE_GOODS_QUEUE)
    public Queue UPDATE_GOODS_QUEUE(){
        return new Queue(UPDATE_GOODS_QUEUE);
    }

    /**
     * 声明队列
     */
    @Bean(DELETE_GOODS_QUEUE)
    public Queue DELETE_GOODS_QUEUE(){
        return new Queue(DELETE_GOODS_QUEUE);
    }

}
