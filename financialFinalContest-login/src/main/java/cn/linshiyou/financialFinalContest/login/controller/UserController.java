package cn.linshiyou.financialFinalContest.login.controller;

import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.login.dao.entity.LoginVo;
import cn.linshiyou.financialFinalContest.login.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        // 登录信息返回一个Map集合，方便后续操作
        Map<String, Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }

    @GetMapping("/getPhone/{userId}")
    public String getPhone(@PathVariable(value = "userId") Long userId) {
        return userInfoService.getPhone(userId);
    }

    @GetMapping("/getName/{userId}")
    public String getName(@PathVariable(value = "userId") Long userId) {
        return userInfoService.getName(userId);
    }
}
