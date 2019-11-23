package com.example.demo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(int a){
        if (a == 1){
            return "Hello a = 1";
        }else if(a == 2){
            return "Hello a = 2";
        } else {
            return "I don't know a = ?";
        }
    }
}
