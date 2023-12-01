package com.murphy.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PatchMapping("/test")
    public String test(){
        return "Hello";
    }
}
