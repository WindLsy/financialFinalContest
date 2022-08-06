package cn.linshiyou.financialFinalContest.swap.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author LJ
 * @since 2022-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SwapBill implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 交换列表id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 用户Aid
     */
    @TableField("user_Aid")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long userAid;

    /**
     * 用户Bid
     */
    @TableField("user_Bid")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long userBid;

    /**
     * 交易描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 交易状态
     */
    private Integer statusId;


}
