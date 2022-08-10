package cn.linshiyou.financialFinalContest.common.feign;

import cn.linshiyou.financialFinalContest.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: LJ
 * @Description: 状态类型feign
 */
@FeignClient(name = "common-service", path = "/codeState")
public interface CodeStateFeign {

    /**
     * 添加类型
     * @param type_id 类型id
     * @param name 状态名
     * @return
     */
    @PostMapping("/{type_id}/{name}")
    Result add(@PathVariable("type_id") int type_id, @PathVariable("name") String name);

    /**
     * 根据id查询状态为了feign
     * @param id 状态id
     * @return codeState
     */
    @GetMapping("/name/{id}")
    String getNameById(@PathVariable("id") int id);
    /**
     * 根据类型id查询所有状态
     * @param type_id 类型type_id
     * @return List<CodeStateDTO>
     */
    @GetMapping("/list/{type_id}")
    Result getByTypeId(int type_id);

}
