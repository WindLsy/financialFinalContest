package cn.linshiyou.financialFinalContest.swap.controller;


import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.common.pojo.StatusCode;
import cn.linshiyou.financialFinalContest.swap.dao.entity.Swap;
import cn.linshiyou.financialFinalContest.swap.dao.entity.SwapBill;
import cn.linshiyou.financialFinalContest.swap.dao.vo.GoodsDTO;
import cn.linshiyou.financialFinalContest.swap.dao.vo.SwapBillVo;
import cn.linshiyou.financialFinalContest.swap.service.SwapBillService;
import cn.linshiyou.financialFinalContest.swap.service.SwapService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LJ
 * @since 2022-07-28
 */
@RestController
@RequestMapping("/swap")
public class SwapController {


    @Autowired
    private SwapService swapService;

    @Autowired
    private SwapBillService swapBillService;

    /**
     * 交换第一阶段
     * 必需参数
     * goodId：物品id集合
     * userAid：用户Aid
     * user_Bid：用户Bid
     *
     * swaps:user_final_id:如果交换成功拥有者id
     *
     * @param swapList
     * @return
     */
    @PostMapping("/inone")
    public Result swapOne ( @RequestParam Long userAId,
                            @RequestParam Long userBId,
                            @RequestBody List<Swap> swaps){

        swapService.stageOneSwap(userAId, userBId, swaps);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .message("交易一阶段成功")
                .build();
    }

    /**
     * 交换第二阶段
     * 必需参数
     * id：交易id
     * statusId：交易状态id
     *
     * @return
     */
    @PutMapping("/intwo")
    public Result swapTwo(@RequestBody SwapBill swapBill){

        swapService.stageTwoSwap(swapBill);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .message("交易二阶段成功")
                .build();
    }

    /**
     * 交换第三阶段
     * 必需参数
     * id：交易id
     *
     * @return
     */
    @PutMapping("/inthree")
    public Result swapThree(@RequestBody SwapBill swapBill){

        swapService.stageThreeSwap(swapBill);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .message("交易三阶段成功")
                .build();
    }

    /**
     * 查询交易记录(分页)
     * 根据当前登录用户
     */
    @GetMapping("/get")
    public Result getByuserId(@RequestParam(value = "startPage", defaultValue = "0") int startPage,
                              @RequestParam(value = "sizePage", defaultValue = "10") int sizePage,
                              @RequestParam Long userId){

        Page<SwapBillVo> swapBills = swapService.selectByUserid(startPage, sizePage, userId);

        return  Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(swapBills.toPageInfo())
                .message("查询成功")
                .build();
    }

    @GetMapping("/getSwapList")
    public Result getSwapList(@RequestParam Long swapBillId){

//        Page<SwapBillVo> swapBills = swapService.selectByUserid(startPage, sizePage, userId);

        List<GoodsDTO> goodsDTOS = swapService.selectSwapLit(swapBillId);

        return  Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(goodsDTOS)
                .message("查询成功")
                .build();
    }


}