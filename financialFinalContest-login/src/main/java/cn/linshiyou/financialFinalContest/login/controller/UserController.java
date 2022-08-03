package cn.linshiyou.financialFinalContest.login.controller;

import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.common.pojo.StatusCode;
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
    public Result getPhone(@PathVariable(value = "userId") Long userId) {
        String phone = userInfoService.getPhone(userId);
        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(phone)
                .message("查询成功")
                .build();

    }

    @GetMapping("/getName/{userId}")
    public Result getName(@PathVariable(value = "userId") Long userId) {
        String name = userInfoService.getName(userId);
        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(name)
                .message("查询成功")
                .build();
    }

    @GetMapping("/getUserId/{name}")
    public Result getUserId(@PathVariable(value = "name") String name) {
        Long userId = userInfoService.getUserId(name);
        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(userId)
                .message("查询成功")
                .build();
    }
}
