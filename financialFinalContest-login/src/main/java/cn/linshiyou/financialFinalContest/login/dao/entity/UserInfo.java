package cn.linshiyou.financialFinalContest.login.dao.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * UserInfo
 * </p>
 *
 * @author xzh
 */
@Data
@TableName("user_info")
public class UserInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("openid")
    private String openid;

    @TableField("nick_name")
    private String nickName;

    @TableField("phone")
    private String phone;

    @TableField("name")
    private String name;

    @TableField("status")
    private Integer status;

}

