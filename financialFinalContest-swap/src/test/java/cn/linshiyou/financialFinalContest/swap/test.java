package cn.linshiyou.financialFinalContest.swap;

import cn.linshiyou.financialFinalContest.swap.dao.entity.Swap;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LJ
 * @Description:
 */
public class test {



    @Test
    public void test(){
        List<Swap> list = new ArrayList<>();


        System.out.println(list.toString());
        System.out.println(JSON.toJSONString(list));

    }

}
