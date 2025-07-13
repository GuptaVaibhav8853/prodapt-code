package com.example.demo.controller;

import com.example.demo.APIResponse;
import com.example.demo.service.StringProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    StringProcessorService stringProcessorService;
    @GetMapping("/remove")
    public ResponseEntity<APIResponse> remove(@RequestParam String original){
        return stringProcessorService.processString(original);
    }
}
