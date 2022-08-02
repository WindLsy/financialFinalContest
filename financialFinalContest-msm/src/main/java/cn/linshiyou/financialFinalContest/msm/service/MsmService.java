package cn.linshiyou.financialFinalContest.msm.service;


import cn.linshiyou.financialFinalContest.msm.pojo.MsmVo;

public interface MsmService {

    //发送手机验证码
    boolean send(String phone, String code);

    //mq使用发送短信
    boolean send(MsmVo msmVo);
}
