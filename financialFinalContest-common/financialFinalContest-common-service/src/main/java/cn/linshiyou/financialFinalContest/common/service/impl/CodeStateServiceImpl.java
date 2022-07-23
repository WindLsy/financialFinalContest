package cn.linshiyou.financialFinalContest.common.service.impl;

import cn.linshiyou.financialFinalContest.common.dao.dto.CodeStateDTO;
import cn.linshiyou.financialFinalContest.common.dao.entity.CodeState;
import cn.linshiyou.financialFinalContest.common.dao.mapper.CodeStateMapper;
import cn.linshiyou.financialFinalContest.common.service.CodeStateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LJ
 * @since 2022-07-23
 */
@Service
public class CodeStateServiceImpl extends ServiceImpl<CodeStateMapper, CodeState> implements CodeStateService {

    @Autowired
    private CodeStateMapper codeStateMapper;

    @Override
    public List<CodeStateDTO> getByTypeId(int type_id) {
        return codeStateMapper.getByTypeId(type_id);
    }
}
