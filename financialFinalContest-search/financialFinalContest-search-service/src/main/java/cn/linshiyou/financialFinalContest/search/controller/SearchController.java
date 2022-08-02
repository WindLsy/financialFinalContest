package cn.linshiyou.financialFinalContest.search.controller;

import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.common.pojo.StatusCode;
import cn.linshiyou.financialFinalContest.search.pojo.GoodsInfo;
import cn.linshiyou.financialFinalContest.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: LJ
 * @Description: 控制层
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Autowired
    private SearchService searchService;


    /**
     * 创建索引
     * @return
     */
    @GetMapping("/createMapping")
    public Result createMapping(){
        searchService.createIndexMappingIfNot();

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .message("创建成功")
                .build();
    }


    @GetMapping("/searchByCondition")
    public Result searchByCondition(@RequestParam(value = "startPage", defaultValue = "0") int startPage,
                                    @RequestParam(value = "sizePage", defaultValue = "10") int sizePage,
                                    String searchString,
                                    Double price,
                                    boolean isUp,
                                    Long userId,
                                    Long typeId,
                                    Long statusId){

        Page<GoodsInfo> goodsInfoPage = searchService.selectByCondition(startPage, sizePage, searchString, price, isUp, userId, typeId, statusId);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(goodsInfoPage)
                .message("查询成功")
                .build();

    }


}
