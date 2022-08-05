package cn.linshiyou.financialFinalContest.swap.config;

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
     * 交易第一阶段，A确认交换物品,发起交换
     */
    public static final String SWAP_ONE_QUEUE="swap_one_queue";

    /**
     * 交易第二阶段，B同意/拒绝交换物品,等待A最后确认
     */
    public static final String SWAP_TWO_QUEUE="swap_two_queue";

    /**
     * 交易第三阶段，B确认交换物品，物品交换完成
     */
    public static final String SWAP_THREE_QUEUE="swap_three_queue";


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

    /**
     * 声明队列
     */
    @Bean(SWAP_ONE_QUEUE)
    public Queue SWAP_ONE_QUEUE(){
        return new Queue(SWAP_ONE_QUEUE);
    }

    /**
     * 声明队列
     */
    @Bean(SWAP_TWO_QUEUE)
    public Queue SWAP_TWO_QUEUE(){
        return new Queue(SWAP_TWO_QUEUE);
    }

    /**
     * 声明队列
     */
    @Bean(SWAP_THREE_QUEUE)
    public Queue SWAP_THREE_QUEUE(){
        return new Queue(SWAP_THREE_QUEUE);
    }


}
