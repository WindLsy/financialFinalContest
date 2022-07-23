package cn.linshiyou.financialFinalContest.common.dao.dto;

import cn.linshiyou.financialFinalContest.common.dao.entity.CodeState;
import lombok.Data;

/**
 * @Author: LJ
 * @Description: codeState扩展类
 */
@Data
public class CodeStateDTO extends CodeState {

    /**
     * 状态类型名
     */
    private String type_name;
}
