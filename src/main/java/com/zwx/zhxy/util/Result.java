package com.zwx.zhxy.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * JSON响应结果格式封装类
 * @author zwx
 * @param <T>
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result(){}

    /**
     * 返回数据
     * @param data
     * @param <T>
     * @return
     */
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    /**
     * 返回数据，状态码，相应信息
     * @param body
     * @param resultCodeEnum
     * @param <T>
     * @return
     */
    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    /**
     * 响应成功
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(){
        return Result.ok(null);
    }

    /**
     * 操作成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    /**
     * 响应失败
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    /**
     * 返回响应信息
     * @param msg
     * @return
     */
    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    /**
     * 返回响应状态码
     * @param code
     * @return
     */
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * 是否成功
     * @return
     */
    public boolean isOk() {
        if(this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}
