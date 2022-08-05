package cn.linshiyou.financialFinalContest.swap.service;

import cn.linshiyou.financialFinalContest.swap.dao.entity.Swap;
import cn.linshiyou.financialFinalContest.swap.dao.entity.SwapBill;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LJ
 * @since 2022-07-28
 */
public interface SwapService extends IService<Swap> {

    /**
     * 交易第一阶段
     * @param userAId
     * @param userBId
     * @param swaps
     */
    void stageOneSwap(Long userAId, Long userBId, List<Swap> swaps);

    /**
     * 交易第二阶段
     * @param swapBill
     */
    void stageTwoSwap(SwapBill swapBill);

    /**
     * 交易三阶段
     * @param swapBill
     */
    void stageThreeSwap(SwapBill swapBill);

    /**
     * 查询交易记录
     * @param startPage
     * @param sizePage
     * @param userId
     * @return
     */
    Page<SwapBill> selectByUserid(int startPage, int sizePage, Long userId);

}
