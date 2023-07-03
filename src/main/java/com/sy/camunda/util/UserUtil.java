package com.sy.camunda.util;

public final class UserUtil {

    private UserUtil() {
        throw new AssertionError();
    }


    // TODO: 2023/6/30 集成业务系统自己的鉴权
    public static Long getUserId(){
        return 1L;
    }
}
