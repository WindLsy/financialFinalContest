package cn.linshiyou.financialFinalContest.common.controller;

import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.common.pojo.StatusCode;
import cn.linshiyou.financialFinalContest.common.util.fastdfs.FastDFSClient;
import cn.linshiyou.financialFinalContest.common.util.fastdfs.FastDFSFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: LJ
 * @Description: 文件上传
 */

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {


    /**
     * 文件上传
     * @param file
     * @return 文件url:String
     */
    @PostMapping(value = "/upload")
    public Result fileUpload(@RequestPart(value = "file") MultipartFile file){
        try{
            //1.判断文件是否存在
            if (file == null){
                throw new RuntimeException("文件不存在");
            }
            //2.获取文件的完整名称
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isEmpty(originalFilename)){
                throw new RuntimeException("文件不存在");
            }
            //3.获取文件的扩展名称  abc.jpg   jpg
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //4.获取文件内容,二进制
            byte[] content = file.getBytes();
            //5.创建文件上传的封装实体类
            FastDFSFile fastDFSFile = new FastDFSFile(originalFilename,content,extName);
            //6.基于工具类进行文件上传,并接受返回参数  String[]

            String[] uploadResult = FastDFSClient.upload(fastDFSFile);
            //7.封装返回结果
            String url = FastDFSClient.getTrackerUrl()+uploadResult[0]+"/"+uploadResult[1];
            //FastDFSClient.getTrackerUrl() 此时为txyun.linshiyou.cn:8080/
//            String url = uploadResult[0]+"/"+uploadResult[1];


            return new Result(true, StatusCode.OK,"文件上传成功",url);
        } catch (IOException e) {
            log.error("文件读取失败");
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false, StatusCode.ERROR,"文件上传失败,请稍后再试");

    }
}
