package cn.linshiyou.financialFinalContest.swap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan(basePackages={"cn.linshiyou.financialFinalContest.swap.dao.mapper"})
@EnableFeignClients(basePackages = "cn.linshiyou.financialFinalContest.common")
public class SwapBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SwapBootstrap.class,args);
    }
}
