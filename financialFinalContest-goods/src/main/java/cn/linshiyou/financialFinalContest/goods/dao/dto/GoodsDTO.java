package cn.linshiyou.financialFinalContest.goods.dao.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author LJ
 * @since 2022-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 类型id
     */
    private Integer typeId;


    /**
     * 类型名称
     */
    private String typeName;


    /**
     * 物品价值
     */
    private Double price;

    /**
     * 物品图片
     */
    private String image;

    /**
     * 物品描述
     */
    private String description;
    /**
     * 物品状态id
     */
    private Integer statusId;

    /**
     * 物品状态
     */
    private String statusName;


}
