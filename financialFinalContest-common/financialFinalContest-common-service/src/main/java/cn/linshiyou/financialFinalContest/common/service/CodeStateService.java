package cn.linshiyou.financialFinalContest.common.service;

import cn.linshiyou.financialFinalContest.common.dao.dto.CodeStateDTO;
import cn.linshiyou.financialFinalContest.common.dao.entity.CodeState;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LJ
 * @since 2022-07-23
 */
public interface CodeStateService extends IService<CodeState> {

    /**
     * 根据类型ID获取COdeStateDTO
     * @param type_id
     * @return
     */
    List<CodeStateDTO> getByTypeId(int type_id);


}
