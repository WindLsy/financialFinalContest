package cn.linshiyou.financialFinalContest.swap.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LJ
 * @since 2022-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Swap implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private Long id;

    /**
     * 物品id
     */
    private Long goodId;

    /**
     * 用户Aid
     */
    @TableField("user_Aid")
    private Long userAid;

    /**
     * 用户Bid
     */
    @TableField("user_Bid")
    private Long userBid;

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
