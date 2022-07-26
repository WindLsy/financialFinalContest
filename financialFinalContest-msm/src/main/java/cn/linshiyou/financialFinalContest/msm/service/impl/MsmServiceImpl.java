package cn.linshiyou.financialFinalContest.msm.service.impl;

import cn.linshiyou.financialFinalContest.msm.pojo.MsmVo;
import cn.linshiyou.financialFinalContest.msm.service.MsmService;
import cn.linshiyou.financialFinalContest.msm.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, String code) {
        //判断手机号是否为空
        if(StringUtils.isEmpty(phone)) {
            return false;
        }
        System.out.println(code);
        System.out.println(phone);
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "2eb4025135c84338bd9be9a521502237";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**code**:" + code + ",**minute**:2");
        querys.put("smsSignId", "ec5d968b66eb4a86ad136f0fc8e1d9f4");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取发送结果
            System.out.println(response.getStatusLine().getStatusCode() == 200);
            return response.getStatusLine().getStatusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    //mq发送短信封装
    @Override
    public boolean send(MsmVo msmVo) {
        if(!StringUtils.isEmpty(msmVo.getPhone())) {
            boolean isSend = this.send(msmVo.getPhone(), msmVo.getParam());
            return isSend;
        }
        return false;
    }

    private boolean send(String phone, Map<String,Object> param) {
        //判断手机号是否为空
        if(StringUtils.isEmpty(phone)) {
            return false;
        }

        String name = (String) param.get("name");
        String description = (String) param.get("description");

        String templateId;
        if (param.get("status").equals(3)) {
            // 交易者发起交易，短信提醒被交易者
            templateId = "4293caf6812646168481f03a6fb938f8";
        } else if (param.get("status").equals(6)) {
            // 被交易者拒绝 ok
            templateId = "84ccc82133ad430bb69839c5297ee65e";
        } else if (param.get("status").equals(4)) {
            // 被交易者同意，需要被交易者确认
            templateId = "6076cb97ffaf46288f5987f64b0d5049";
        } else if (param.get("status").equals(7)) {
            // 交易者取消交换 ok
            templateId = "1e1290cbc82f4e3680bb9650fabfde3b";
        } else {
            // 交易者确认交换，交换成功 ok
            templateId = "aab1299eb8774da5bbcb0c837e8c4213";
        }

        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "2eb4025135c84338bd9be9a521502237";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**name**:" + name + ",**description**:" + description);
        querys.put("smsSignId", "ec5d968b66eb4a86ad136f0fc8e1d9f4");
        querys.put("templateId", templateId);
        Map<String, String> bodys = new HashMap<String, String>();
        System.out.println((String) param.get("description"));
        System.out.println((String) param.get("name"));
        System.out.println(phone);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取发送结果
            System.out.println(response.getStatusLine().getStatusCode() == 200);
            return response.getStatusLine().getStatusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
