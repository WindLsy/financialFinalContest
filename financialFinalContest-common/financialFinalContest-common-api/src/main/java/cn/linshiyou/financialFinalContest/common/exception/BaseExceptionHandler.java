package cn.linshiyou.financialFinalContest.common.exception;

import cn.linshiyou.financialFinalContest.common.pojo.Result;
import cn.linshiyou.financialFinalContest.common.pojo.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;

/**
 * @Author: LJ
 * @Description: 统一异常处理
 */
@ControllerAdvice
public class BaseExceptionHandler {


    /**
     * 文件读取失败
     * @param e
     * @return
     */
    @ExceptionHandler(value = IOException.class)
    @ResponseBody
    public Result error(IOException e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "文件读取失败");
    }

    /**
     * 上传文件不能超过2MB
     * @param e
     * @return
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public Result error(MaxUploadSizeExceededException e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "文件大小不能超过5MB");
    }

    /**
     * 所有异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "执行出错");
    }
}
