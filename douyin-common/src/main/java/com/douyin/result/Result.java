package com.douyin.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 */
@Data
public class Result implements Serializable {

    private Integer statusCode; //编码：0成功，其它数字为失败

    private String statusMsg; //错误信息

    public static Result success() {
        Result result = new Result();
        result.statusCode = 0;
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.statusMsg = msg;
        result.statusCode = 1;
        return result;
    }
}
