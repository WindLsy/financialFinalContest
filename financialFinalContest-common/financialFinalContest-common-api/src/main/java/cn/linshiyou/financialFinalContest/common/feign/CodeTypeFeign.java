package cn.linshiyou.financialFinalContest.common.feign;

import cn.linshiyou.financialFinalContest.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: LJ
 * @Description: CodeType状态feign
 */
@FeignClient(name = "common-service", path = "/codeType")
public interface CodeTypeFeign {

    /**
     * 添加类型
     * @param name 类型名
     * @return
     */
    @PostMapping("/{name}")
    Result add(@PathVariable("name") String name);

    /**
     * 查询类型
     * @param id 类型id
     * @return CodeType
     */
    @GetMapping("/{id}")
    Result getById(@PathVariable("id") int id);

}
