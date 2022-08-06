package cn.linshiyou.financialFinalContest.common.feign;

import cn.linshiyou.financialFinalContest.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("login-service")
public interface User_MsmFeign {
    @GetMapping("/user/getPhone/{userId}")
    public String getPhone(@PathVariable("userId") Long userId);

    @GetMapping("/user/getName/{userId}")
    public String getName(@PathVariable("userId") Long userId);

}
