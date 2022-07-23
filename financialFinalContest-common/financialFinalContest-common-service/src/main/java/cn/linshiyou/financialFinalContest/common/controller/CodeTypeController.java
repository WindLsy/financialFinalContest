package cn.linshiyou.financialFinalContest.common.controller;


import cn.linshiyou.financialFinalContest.common.dao.entity.CodeType;
import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.common.pojo.StatusCode;
import cn.linshiyou.financialFinalContest.common.service.CodeTypeService;
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
@RequestMapping("/codeType")
public class CodeTypeController {

    @Autowired
    private CodeTypeService codeTypeService;

    /**
     * 添加类型
     * @param name 类型名
     * @return
     */
    @PostMapping("/{name}")
    public Result add(@PathVariable("name") String name){
        CodeType codeType = new CodeType();
        codeType.setName(name);
        codeTypeService.save(codeType);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .message("添加成功")
                .build();
    }

    /**
     * 查询类型
     * @param id 类型id
     * @return CodeType
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") int id){
        CodeType codeType = codeTypeService.getById(id);

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(codeType)
                .message("查询成功")
                .build();
    }


    /**
     * 获取所有类型
     * @return List<CodeType>
     */
    @GetMapping
    public Result getAll(){
        List<CodeType> list = codeTypeService.list();

        return Result.builder()
                .flag(true)
                .code(StatusCode.OK)
                .data(list)
                .message("查询成功")
                .build();
    }


}

