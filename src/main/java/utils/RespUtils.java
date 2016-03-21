package main.java.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanxiaocan on 2016/3/19.
 */
public class RespUtils {
    public static Map<String,Object> success(Object data){
        Map<String,Object> resp = new HashMap<String, Object>();
        resp.put("code","0");
        resp.put("message","success");
        resp.put("data",data);
        return resp;
    }

    public static Map<String,Object> fail(String code,String message){
        Map<String,Object> resp = new HashMap<String, Object>();
        resp.put("code",code);
        resp.put("message",message);
        return resp;
    }
}
