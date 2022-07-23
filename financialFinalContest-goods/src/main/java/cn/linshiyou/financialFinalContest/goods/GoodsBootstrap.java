package cn.linshiyou.financialFinalContest.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: LJ
 * @Description:
 */
@SpringBootApplication
@MapperScan(basePackages={"cn.linshiyou.financialFinalContest.goods.dao.mapper"})
public class GoodsBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(GoodsBootstrap.class, args);
    }

}
