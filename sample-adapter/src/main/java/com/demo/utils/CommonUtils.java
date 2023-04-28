package com.demo.utils;

import com.demo.constant.AllFunction;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public String getQueueRequest(AllFunction function) {
        return function.getFunctionName() + ".REQUEST";
    }
    public String getQueueResponse(AllFunction function) {
        return function.getFunctionName() + ".RESPONSE";
    }

}
