package cn.linshiyou.financialFinalContest.swap.feign;

import cn.linshiyou.financialFinalContest.swap.dao.vo.GoodsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: LJ
 * @Description: 物品服务Feign
 */
@FeignClient(name = "goods-service", path = "/goods")
public interface GoodsFeign {


    @GetMapping("/getGoods")
    List<GoodsDTO> getGoods(@RequestParam List<Long> listId);

}
