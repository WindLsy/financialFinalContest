package cn.linshiyou.financialFinalContest.msm.receiver;

import cn.linshiyou.financialFinalContest.common.feign.User_MsmFeign;
import cn.linshiyou.financialFinalContest.msm.config.RabbitMQConfig;
import cn.linshiyou.financialFinalContest.msm.pojo.SwapMq;
import cn.linshiyou.financialFinalContest.msm.service.MsmService;
import cn.linshiyou.financialFinalContest.msm.service.RabbitService;
import cn.linshiyou.financialFinalContest.msm.pojo.MsmVo;
import cn.linshiyou.financialFinalContest.msm.utils.MqConst;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SwapListener {


    @Autowired
    private User_MsmFeign userFeign;

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private MsmService smsService;

    /**
     * 交易第一阶段
     */
    @RabbitListener(queues = RabbitMQConfig.SWAP_ONE_QUEUE)
    public void oneSendMessage(String swapListJson){

        SwapMq swapMq = JSON.parseObject(swapListJson, SwapMq.class);
        Long Bid = swapMq.getUserBid();
        String description = swapMq.getDescription();
        int statusId = swapMq.getStatusId();

        MsmVo msmVo = new MsmVo();

        String phone = (String) userFeign.getPhone(Bid).getData();
        String name = (String) userFeign.getName(Bid).getData();
        msmVo.setPhone(phone);
        System.out.println(phone);
        Map<String,Object> param = new HashMap<String,Object>(){{
            put("name", name);
            put("description", description);
            put("status", statusId);
        }};
        msmVo.setParam(param);
        System.out.println("111111");
        smsService.send(msmVo);
    }


    /**
     * 交易第二阶段
     */
    @RabbitListener(queues = RabbitMQConfig.SWAP_TWO_QUEUE)
    public void twoSendMessage(String swapListJson){

        SwapMq swapMq = JSON.parseObject(swapListJson, SwapMq.class);
        Long Aid = swapMq.getUserAid();
        Long Bid = swapMq.getUserBid();
        String description = swapMq.getDescription();
        int statusId = swapMq.getStatusId();
        MsmVo msmVo = new MsmVo();

        String phone = "";
        String name = "";
        if (statusId == 7) {
            phone = (String) userFeign.getPhone(Bid).getData();
            name = (String) userFeign.getName(Bid).getData();
        } else {
            phone = (String) userFeign.getPhone(Aid).getData();
            name = (String) userFeign.getName(Aid).getData();
        }




        msmVo.setPhone(phone);
        System.out.println(phone);
        System.out.println(statusId);
        String finalName = name;
        Map<String,Object> param = new HashMap<String,Object>(){{
            put("name", finalName);
            put("description", description);
            put("status", statusId);
        }};
        msmVo.setParam(param);
        System.out.println("111111");
        smsService.send(msmVo);
    }


    /**
     * 交易第三阶段
     */
    @RabbitListener(queues = RabbitMQConfig.SWAP_THREE_QUEUE)
    public void threeSendMessage(String swapListJson){

        // 交易成功，给双方都发短信
        SwapMq swapMq = JSON.parseObject(swapListJson, SwapMq.class);
        Long Aid = swapMq.getUserAid();
        String description = swapMq.getDescription();

        MsmVo msmVo1 = new MsmVo();

        String phoneA = (String) userFeign.getPhone(Aid).getData();
        String nameA = (String) userFeign.getName(Aid).getData();
        int statusId = swapMq.getStatusId();
        msmVo1.setPhone(phoneA);
        System.out.println(phoneA);
        System.out.println(statusId);
        Map<String,Object> param = new HashMap<String,Object>(){{
            put("name", nameA);
            put("description", description);
            put("status", statusId);
        }};
        msmVo1.setParam(param);
        System.out.println("111111");
        smsService.send(msmVo1);


        Long Bid = swapMq.getUserBid();

        MsmVo msmVo2 = new MsmVo();

        String phoneB = (String) userFeign.getPhone(Bid).getData();
        String nameB = (String) userFeign.getName(Bid).getData();
        msmVo2.setPhone(phoneB);
        System.out.println(phoneB);
        Map<String,Object> paramB = new HashMap<String,Object>(){{
            put("name", nameB);
            put("description", description);
            put("status", statusId);
        }};
        msmVo2.setParam(paramB);
        System.out.println("111111");
        smsService.send(msmVo2);
    }
}
