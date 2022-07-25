package cn.linshiyou.financialFinalContest.msm.service.impl;

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


//    private boolean send(String phone, Map<String,Object> param) {
//        //判断手机号是否为空
//        if(StringUtils.isEmpty(phone)) {
//            return false;
//        }
//
//
//        String title = (String) param.get("title");
//        String reserveDate = (String) param.get("reserveDate");
//        String name = (String) param.get("name");
//        String templateId;
//        if (param.get("jiuyitixing") != null) {
//            // 就诊提醒短信
//            templateId = "";
//        } else if (param.get("yuyue") != null) {
//            // 预约成功短信
//            templateId = "";
//        } else {
//            // 取消预约成功短信
//            templateId = "";
//        }
//
//        String host = "https://gyytz.market.alicloudapi.com";
//        String path = "/sms/smsSend";
//        String method = "POST";
//        String appcode = "";
//        Map<String, String> headers = new HashMap<String, String>();
//        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("mobile", phone);
//        querys.put("param", "**name**:" + name + ",**title**:" + title + ",**reserveDate**:" + reserveDate);
//        querys.put("smsSignId", "");
//        querys.put("templateId", templateId);
//        Map<String, String> bodys = new HashMap<String, String>();
//        System.out.println((String) param.get("title"));
//        System.out.println((String) param.get("reserveDate"));
//        System.out.println((String) param.get("name"));
//        System.out.println(phone);
//
//
//        try {
//            /**
//             * 重要提示如下:
//             * HttpUtils请从
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//             * 下载
//             *
//             * 相应的依赖请参照
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//             */
//            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//            System.out.println(response.toString());
//            //获取发送结果
//            System.out.println(response.getStatusLine().getStatusCode() == 200);
//            return response.getStatusLine().getStatusCode() == 200;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
