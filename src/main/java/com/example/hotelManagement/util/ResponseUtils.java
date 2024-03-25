package com.example.hotelManagement.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    public static Map<String, String> buildFailureResponse(String message){
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}
