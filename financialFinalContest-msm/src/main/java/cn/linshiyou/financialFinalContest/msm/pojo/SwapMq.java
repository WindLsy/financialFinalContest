package cn.linshiyou.financialFinalContest.msm.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: LJ
 * @Description: 传递消息的javaBean
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SwapMq {

    /**
     * 用户Aid
     */
    private Long userAid;

    /**
     * 用户Bid
     */
    private Long userBid;

    /**
     * 交易描述
     */
    private String description;


    /**
     * 物品列表
     */
    private List<Swap> swaps;

    /**
     * 交易状态
     */
    private int statusId;
}
