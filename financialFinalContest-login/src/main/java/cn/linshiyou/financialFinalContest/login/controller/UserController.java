package cn.linshiyou.financialFinalContest.login.controller;

import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.login.dao.entity.LoginVo;
import cn.linshiyou.financialFinalContest.login.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//
//    //用户列表（条件查询带分页）
//    @GetMapping("{page}/{limit}")
//    public Result list(@PathVariable Long page,
//                       @PathVariable Long limit,
//                       UserInfoQueryVo userInfoQueryVo) {
//        Page<UserInfo> pageParam = new Page<>(page,limit);
//        IPage<UserInfo> pageModel =
//                userInfoService.selectPage(pageParam,userInfoQueryVo);
//        return Result.ok(pageModel);
//    }
//
//    //用户锁定
//    @GetMapping("lock/{userId}/{status}")
//    public Result lock(@PathVariable Long userId,@PathVariable Integer status) {
//        userInfoService.lock(userId,status);
//        return Result.ok();
//    }
//
//    //用户详情
//    @GetMapping("show/{userId}")
//    public Result show(@PathVariable Long userId) {
//        Map<String,Object> map = userInfoService.show(userId);
//        return Result.ok(map);
//    }
//
//    //认证审批
//    @GetMapping("approval/{userId}/{authStatus}")
//    public Result approval(@PathVariable Long userId,@PathVariable Integer authStatus) {
//        userInfoService.approval(userId,authStatus);
//        return Result.ok();
//    }
}
