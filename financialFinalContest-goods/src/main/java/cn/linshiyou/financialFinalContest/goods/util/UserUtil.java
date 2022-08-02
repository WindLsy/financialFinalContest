package cn.linshiyou.financialFinalContest.goods.util;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {
    public static Long getUserId(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        //jwt从token获取userid
        Long userId = JwtHelper.getUserId(token);
        return userId;
    }
}
