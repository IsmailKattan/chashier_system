package com._32bit.project.cashier_system.resource;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldResource {

    @GetMapping("/manager")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public String helloManager(){
        return "Hello manager";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String helloAdmin(){
        return "Hello admin";
    }
    @GetMapping("/cashier")
    @PreAuthorize("hasAuthority('ROLE_CASHIER')")
    public String helloCashier(){
        return "Hello cashier";
    }

    @GetMapping("/erisebilir")
    public String helloFriend() {return "hello friend";}

    @GetMapping("/erisilmez")
    public String helloFriend2() {return "sen buralara nerden girdin, yaradana kurban olayım, yaradana kurban olayım, yaradana";}
}
