package cn.linshiyou.financialFinalContest.login.service;

import cn.linshiyou.financialFinalContest.login.dao.entity.LoginVo;
import cn.linshiyou.financialFinalContest.login.dao.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


public interface UserInfoService extends IService<UserInfo> {

    //用户手机号登录接口
    Map<String, Object> loginUser(LoginVo loginVo);

    //根据openid判断
    UserInfo selectWxInfoOpenId(String openid);

    //获取手机号
    String getPhone(Long userId);

    //获取姓名
    String getName(Long userId);

    Long getUserId(String name);
}
