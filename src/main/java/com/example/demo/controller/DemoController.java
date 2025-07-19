package com.example.demo.controller;

import com.example.demo.APIResponse;
import com.example.demo.service.StringProcessorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class DemoController {
    @Autowired
    StringProcessorService stringProcessorService;
    @GetMapping("/remove")
    public ResponseEntity<APIResponse> remove(HttpServletRequest request)  {
        String rawQuery = request.getQueryString();
        if (rawQuery != null) {
            String original = rawQuery.replaceFirst("original=", "");
            return stringProcessorService.processString(original);
        }
        APIResponse badResponse = new APIResponse(
                400,
                "error",
                "Invalid request",
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badResponse);
    }
}
