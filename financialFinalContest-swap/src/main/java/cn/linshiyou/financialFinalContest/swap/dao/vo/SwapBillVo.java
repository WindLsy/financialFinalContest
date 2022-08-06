package cn.linshiyou.financialFinalContest.swap.dao.vo;

import cn.linshiyou.financialFinalContest.swap.dao.entity.SwapBill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class SwapBillVo  extends SwapBill implements Serializable {


    /**
     * 用户Aid电话
     */
    private Long userAPhone;


    /**
     * 用户B电话
     */
    private Long userBPhone;


}
