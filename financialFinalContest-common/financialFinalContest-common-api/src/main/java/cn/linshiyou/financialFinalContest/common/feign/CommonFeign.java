package cn.linshiyou.financialFinalContest.common.feign;

import cn.linshiyou.financialFinalContest.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: LJ
 * @Description: Common服务中提供的服务
 */
@FeignClient(name = "common-service")
public interface CommonFeign {

    /**
     * 文件上传
     * @param file
     * @return 文件路径url
     */
    @PostMapping(value = "/file/upload")
    Result upload(@RequestPart(value = "file") MultipartFile file);
}
