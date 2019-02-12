package com.gateway.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ui")
public class DemoRestController{

    @GetMapping
    public String get_hello_message(){
        return "Index Page";
    }
}