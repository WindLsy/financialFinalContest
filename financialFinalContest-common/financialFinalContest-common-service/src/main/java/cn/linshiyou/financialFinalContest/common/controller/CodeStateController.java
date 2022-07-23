package cn.linshiyou.financialFinalContest.common.controller;


import cn.linshiyou.financialFinalContest.common.dao.dto.CodeStateDTO;
import cn.linshiyou.financialFinalContest.common.dao.entity.CodeState;
import cn.linshiyou.financialFinalContest.common.dao.entity.CodeType;
import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.common.pojo.StatusCode;
import cn.linshiyou.financialFinalContest.common.service.CodeStateService;
import cn.linshiyou.financialFinalContest.common.service.CodeTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LJ
 * @since 2022-07-23
 */
@RestController
@RequestMapping("/codeState")
public class CodeStateController {

    @Autowired
    private CodeStateService codeStateService;

    /**
     * 添加类型
     * @param type_id 类型id
     * @param name 状态名
     * @return
     */
    @PostMapping("/{type_id}/{name}")
    public Result add(@PathVariable("type_id") int type_id, @PathVariable("name") String name){

        CodeState codeState = new CodeState();
        codeState.setName(name);
        codeState.setTypeId(type_id);

        codeStateService.save(codeState);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .message("添加成功")
                .build();
    }

    /**
     * 根据id查询状态
     * @param id 状态id
     * @return codeState
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") int id){
        CodeState codeState = codeStateService.getById(id);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(codeState)
                .message("查询成功")
                .build();
    }


    /**
     * 根据类型id查询所有状态
     * @param type_id 类型type_id
     * @return List<CodeStateDTO>
     */
    @GetMapping("/list/{type_id}")
    public Result getByTypeId(@PathVariable("type_id") int type_id){

        List<CodeStateDTO> codeStateDTOS = codeStateService.getByTypeId(type_id);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(codeStateDTOS)
                .message("查询成功")
                .build();
    }

}

