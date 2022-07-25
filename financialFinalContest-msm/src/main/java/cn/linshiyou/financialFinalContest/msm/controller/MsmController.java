package cn.linshiyou.financialFinalContest.msm.controller;


import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.common.pojo.StatusCode;
import cn.linshiyou.financialFinalContest.msm.service.MsmService;
import cn.linshiyou.financialFinalContest.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送手机验证码
    @GetMapping("/send/{phone}")
    public Result sendCode(@PathVariable String phone) {

        // 从redis获取验证码，如果获取获取到，返回ok
        // key 手机号  value 验证码
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {
            return new Result(true, 20000, "验证码已发送");
        }
        // 如果从redis获取不到，
        // 生成验证码，
        code = RandomUtil.getFourBitRandom();
        String codes = "code:" + code + ",expire_at:1";
        // 调用service方法，通过整合短信服务进行发送
        boolean isSend = msmService.send(phone,code);
        // 生成验证码放到redis里面，设置有效时间
        if(isSend) {
            redisTemplate.opsForValue().set(phone,code,60, TimeUnit.SECONDS);
            return new Result(true, 20000, "发送短信成功");
        } else {
            return new Result(false, StatusCode.ERROR, "发送短信失败");
        }
    }
}
