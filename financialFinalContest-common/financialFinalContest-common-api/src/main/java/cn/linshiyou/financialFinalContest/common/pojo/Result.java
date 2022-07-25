package cn.linshiyou.financialFinalContest.common.pojo;

import lombok.Builder;

/**
 * 返回结果实体类
 */
@Builder
public class Result<T> {
    //是否成功
    private boolean flag;
    //返回码
    private Integer code;
    //返回消息
    private String message;

    private T data;//返回数据

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = (T)data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = "执行成功";
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 新增
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }
    public static <T> Result<T> build(T body, Integer code) {
        Result<T> result = build(body);
        result.setCode(code);
        return result;
    }
    public static<T> Result<T> ok(T data){
        Result<T> result = build(data);
        return build(data, 20000);
    }
}
