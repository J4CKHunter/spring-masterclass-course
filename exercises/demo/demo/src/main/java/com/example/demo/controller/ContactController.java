package com.example.demo.controller;

import com.example.demo.model.Contact;
import com.example.demo.model.Response;
import com.example.demo.proxy.ContactProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    ContactProxy contactProxy;

    @GetMapping("/getMessages")
    public List<Contact> getMessages(@RequestParam("status") String status){
        return contactProxy.getMessagesByStatus(status);
    }



//    @Autowired
//    RestTemplate restTemplate;
//
//    @PostMapping("/saveMessage")
//    public ResponseEntity<Response> saveMessage(@RequestBody Contact contact){
//
//        String uri = "http://localhost:8080/api/contact/saveMessage";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("invocationFrom", "RestTemplate");
//
//        HttpEntity<Contact> httpEntity = new HttpEntity<>(contact,headers);
//        ResponseEntity<Response> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Response.class);
//
//        return responseEntity;
//    }

    @Autowired
    WebClient webClient;

    @PostMapping("/saveMessage")
    public Mono<Response> saveMessage(@RequestBody Contact contact){

        String uri = "http://localhost:8080/api/contact/saveMessage";
        return webClient.post().uri(uri)
                .header("invocationFrom", "WebClient")
                .body(Mono.just(contact), Contact.class)
                .retrieve()
                .bodyToMono(Response.class);

    }

}
