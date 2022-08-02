package cn.linshiyou.financialFinalContest.msm.pojo;

import lombok.Data;

import java.util.Map;

/**
 * 短信实体
 */

@Data
public class MsmVo {
    // 手机号
    private String phone;

    // 短信模板
    private String templateCode;

    // 短信模板参数
    private Map<String,Object> param;
}