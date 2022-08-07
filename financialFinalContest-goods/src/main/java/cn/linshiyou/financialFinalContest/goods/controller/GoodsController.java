package cn.linshiyou.financialFinalContest.goods.controller;


import cn.linshiyou.financialFinalContest.common.feign.CommonFeign;
import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.common.pojo.StatusCode;
import cn.linshiyou.financialFinalContest.goods.dao.dto.GoodsDTO;
import cn.linshiyou.financialFinalContest.goods.dao.entity.Goods;
import cn.linshiyou.financialFinalContest.goods.service.GoodsService;
import com.github.pagehelper.Page;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LJ
 * @since 2022-07-23
 */
@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 物品上传
     * @param good 物品
     * @param file 事务图
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Goods good, HttpServletRequest request){

        goodsService.add(good, request);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .message("添加成功")
                .build();
    }

    /**
     * 根据id获取商品
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable(value = "id") Long id){

        GoodsDTO goodsDTO = goodsService.getByIdSelf(id);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(goodsDTO)
                .message("查询成功")
                .build();
    }

    /**
     * 修改物品
     * @return
     */
    @PutMapping("/update/{id}")
    public Result updateById(@RequestBody Goods good){

        goodsService.updateById(good);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .message("修改成功")
                .build();
    }

    /**
     * 根据id删除商品
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable(value = "id") Long id){

        goodsService.removeById(id);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .message("删除成功")
                .build();
    }

    /**
     * 根据用户id和欲交换的物品价值生成最小物品集合
     * @param userId
     * @param price
     * @return
     */
    @GetMapping("/lcontainer/{userId}/{price}")
    public Result getLcontainer(@PathVariable(value = "userId") Long userId,
                                @PathVariable(value = "price") Double price){

        List<Goods> leastContainer = goodsService.getLeastContainer(userId, price);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(leastContainer)
                .message("最小物品集合")
                .build();
    }

    @GetMapping("/getGoods")
    public List<GoodsDTO> getGoods(@RequestParam List<Long> listId){

        List<GoodsDTO> goodsDTOS = goodsService.listGoodDTOByIds(listId);

        return goodsDTOS;
    }

}

