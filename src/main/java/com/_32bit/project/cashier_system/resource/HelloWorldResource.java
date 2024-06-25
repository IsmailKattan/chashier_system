package com._32bit.project.cashier_system.resource;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldResource {

    @GetMapping("/hi-user")
    public String helloUser(){
        return "Hello user";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess(){
        return "see you later";
    }
    @GetMapping("/manager")
    public String helloManager(){
        return "Hello manager";
    }
    @GetMapping("/admin")
    public String helloAdmin(){
        return "Hello admin";
    }
    @GetMapping("/cashier")
    public String helloCashier(){
        return "Hello cashier";
    }
}
