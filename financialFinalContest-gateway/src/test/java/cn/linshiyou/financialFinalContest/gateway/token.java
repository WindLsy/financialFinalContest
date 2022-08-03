package cn.linshiyou.financialFinalContest.gateway;

import cn.linshiyou.financialFinalContest.gateway.util.JwtHelper;
import org.junit.Test;

/**
 * @Author: LJ
 * @Description:
 */
public class token {

    @Test
    public void test(){

        String token = JwtHelper.createToken("949615145", "勇者");

        System.out.println(token);
    }
}
