package cn.liujson.comm4j.random;

import java.util.UUID;

/**
 * description
 *
 * @author Liujs
 * @date 2019/9/23
 */
public class UUIDUtils {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
