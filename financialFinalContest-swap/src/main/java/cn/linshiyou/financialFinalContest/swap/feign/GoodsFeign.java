package cn.linshiyou.financialFinalContest.swap.feign;

import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.swap.dao.entity.Goods;
import cn.linshiyou.financialFinalContest.swap.dao.vo.GoodsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LJ
 * @Description: 物品服务Feign
 */
@FeignClient(name = "goods-service", path = "/goods")
public interface GoodsFeign {


    /**
     * 根据物品id获取GoodsDTO
     * @param listId
     * @return
     */
    @GetMapping("/getGoods")
    List<GoodsDTO> getGoods(@RequestParam List<Long> listId);

    /**
     * 修改物品
     * @return
     */
    @PutMapping("/update/{id}")
    Result updateById(@RequestBody Goods good);

    /**
     * 根据id获取商品
     * @return
     */
    @GetMapping("/{id}")
    Result getById(@PathVariable(value = "id") Long id);

    /**
     * 修改物品(list)
     * @return
     */
    @PutMapping("/updateByList")
    Result updateByList(@RequestBody List<Goods> goodsList);



}
