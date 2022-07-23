package cn.linshiyou.financialFinalContest.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: LJ
 * @Description:
 */
@SpringBootApplication
@MapperScan(basePackages={"cn.linshiyou.financialFinalContest.common.dao.mapper"})
public class CommonBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(CommonBootstrap.class, args);
    }


}
