package com.murphy.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: R
 * @Description: 返回的数据
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /*
     * private Object data;
     *
     * public Object getData() { return data; }
     *
     * public void setData(Object data) { this.data = data; }
     */
    public static final int NEED_BIND = 466;

    /**
     * 执行中
     */
    private static final int WS_RUNNING = 0;

    /**
     * 失败
     */
    private static final int WS_ERROR = -1;

    /**
     * 执行完成
     */
    private static final int WS_END = 1;

    public Result() {
        put("code", 0);
        put("timestamp", System.currentTimeMillis());
    }

    public static Result error() {
        Result r = new Result();
        r.put("code", 500);
        r.put("success", false);
        return r;
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String errors) {
        Result r = new Result();
        r.put("code", code);
        r.put("success", false);
        r.put("errors", errors);
        return r;
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.put("code", 0);
        r.put("success", true);
        r.put("msg", msg);

        return r;
    }

    public static Result okData(Object data) {
        Result r = new Result();
        r.put("code", 0);
        r.put("success", true);
        r.put("data", data);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        r.put("code", 0);
        r.put("success", true);
        return r;
    }

    public static Result ok() {
        Result r = new Result();
        r.put("code", 0);
        r.put("success", true);
        return r;
    }

    public static Result BAD_REQUEST(String message) {
        Result r = new Result();
        r.put("code", HttpStatus.BAD_REQUEST.value());
        r.put("success", false);
        r.put("errors", message);
        return r;
    }

    public static Result WS_RUNNING() {
        Result r = new Result();
        r.put("code", 0);
        r.put("operateState", WS_RUNNING);
        return r;
    }

    public static Result WS_END() {
        Result r = new Result();
        r.put("code", 0);
        r.put("operateState", WS_END);
        return r;
    }

    public static Result WS_ERROR() {
        Result r = new Result();
        r.put("code", 0);
        r.put("operateState", WS_ERROR);
        return r;
    }

    public static Map<String, Object> INTERNAL_SERVER_ERROR(String message) {
        Result r = new Result();
        r.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        r.put("errors", message);
        return r;
    }
}