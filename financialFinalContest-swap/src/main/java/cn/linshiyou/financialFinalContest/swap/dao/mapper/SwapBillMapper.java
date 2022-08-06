package cn.linshiyou.financialFinalContest.swap.dao.mapper;

import cn.linshiyou.financialFinalContest.swap.dao.entity.SwapBill;
import cn.linshiyou.financialFinalContest.swap.dao.vo.SwapBillVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LJ
 * @since 2022-07-29
 */
public interface SwapBillMapper extends BaseMapper<SwapBill> {


    /**
     * 根据用户查询交易记录
     * @param userId
     * @return
     */
    List<SwapBillVo> selectByUserid(Long userId);

}
