package cn.linshiyou.financialFinalContest.swap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages={"cn.linshiyou.financialFinalContest.swap.dao.mapper"})
public class SwapBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SwapBootstrap.class,args);
    }
}
