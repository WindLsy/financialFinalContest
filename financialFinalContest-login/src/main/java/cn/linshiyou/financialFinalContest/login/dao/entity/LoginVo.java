package cn.linshiyou.financialFinalContest.login.dao.entity;

import lombok.Data;

@Data
public class LoginVo {

    private String openid;

    private String phone;

    private String code;

    private String ip;

    private String nickname;
}
