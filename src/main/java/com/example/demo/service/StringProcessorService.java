package com.example.demo.service;

import com.example.demo.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StringProcessorService {
    public ResponseEntity<APIResponse> processString(String original)  {
        if(original.length()==2){
            APIResponse successResponse = new APIResponse(
                    200,
                    "success",
                    "data processed successfully",
                    ""
            );
            return ResponseEntity.ok(successResponse);
        }
        if(original.length()<2){
            APIResponse badResponse = new APIResponse(
                    400,
                    "error",
                    "Invalid request",
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badResponse);
        }
        String result;
        if(original.matches(".*[!@#$%~^&*()].*")){
            String temp = original.replaceAll("([!@#$%~^&*()])", "_$1");
            result = temp.substring(0,temp.length()-1);
        }
        else{
            result = original.substring(1,original.length()-1);
        }
        APIResponse successResponse = new APIResponse(
                200,
                "success",
                "data processed successfully",
                result
        );
        return ResponseEntity.ok(successResponse);
    }
}
