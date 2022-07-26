package cn.linshiyou.financialFinalContest.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: LJ
 * @Description:
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "cn.linshiyou.financialFinalContest")
@MapperScan(basePackages={"cn.linshiyou.financialFinalContest.goods.dao.mapper"})
public class GoodsBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(GoodsBootstrap.class, args);
    }

}
