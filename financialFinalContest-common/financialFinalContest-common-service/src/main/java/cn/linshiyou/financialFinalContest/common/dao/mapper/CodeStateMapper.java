package cn.linshiyou.financialFinalContest.common.dao.mapper;

import cn.linshiyou.financialFinalContest.common.dao.dto.CodeStateDTO;
import cn.linshiyou.financialFinalContest.common.dao.entity.CodeState;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LJ
 * @since 2022-07-23
 */
public interface CodeStateMapper extends BaseMapper<CodeState> {

    /**
     * 根据类型ID获取COdeStateDTO
     * @param type_id
     * @return
     */
    List<CodeStateDTO> getByTypeId(int type_id);

}
