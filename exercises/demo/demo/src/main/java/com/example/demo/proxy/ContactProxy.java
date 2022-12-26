package com.example.demo.proxy;


import com.example.demo.config.ProjectConfiguration;
import com.example.demo.model.Contact;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "contact", url = "http://localhost:8080/api/contact",
            configuration = ProjectConfiguration.class)
public interface ContactProxy {

    @GetMapping("/getMessagesByStatus")
    @Headers(value = "Context-Type: application/json")
    public List<Contact> getMessagesByStatus(@RequestParam("status") String status);
}
