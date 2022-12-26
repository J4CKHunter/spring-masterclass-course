package com.eazybytes.eazyschool.rest;

import com.eazybytes.eazyschool.constants.SchoolConstants;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.model.Response;
import com.eazybytes.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
//@Controller
@RestController
@RequestMapping(value = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*")
public class ContactRestController {

    @Autowired
    ContactRepository contactRepository;

    // bu method parametre alır, json döndürür
    @GetMapping("/getMessagesByStatus")
//    @ResponseBody
    public List<Contact> getMessagesByStatus(@RequestParam("status") String status){
        return contactRepository.findByStatus(status);
    }

    // bu method message body olarak json alır ve json döndürür.
    @GetMapping("/getAllMessagesByStatus")
//    @ResponseBody
    public List<Contact> getAllMessagesByStatus(@RequestBody Contact contact){
        if(contact != null && contact.getStatus() != null){
            return contactRepository.findByStatus(contact.getStatus());
        }else {
            return List.of();
        }
    }

    @PostMapping("/saveMessage")
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom,
                                            @Valid @RequestBody Contact contact){

        log.info(String.format("Header invocationFrom = %s", invocationFrom));

        contactRepository.save(contact);

        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMessage("Message saved to database succesfully.");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMessageSaved", "true")
                .body(response);

    }

//    @PostMapping
//    public Response saveMsg(@RequestHeader("invocationFrom") String invocationFrom,
//                                            @Valid @RequestBody Contact contact){
//
//        log.info(String.format("Header invocationFrom = &s", invocationFrom));
//
//        contactRepository.save(contact);
//
//        Response response = new Response();
//        response.setStatusCode("200");
//        response.setStatusMessage("Message saved to database succesfully.");
//
//        return response;
//    }

    @DeleteMapping("/deleteMessage")
    public ResponseEntity<Response> deleteMessage(RequestEntity<Contact> requestEntity){

        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))
            ));
        });

        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());

        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMessage("Message successfully deleted from the database");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeMessage")
    public ResponseEntity<Response> closeMessage(@RequestBody Contact contactRequest){
        Response response = new Response();

        Optional<Contact> contact = contactRepository.findById(contactRequest.getContactId());
        if(contact.isPresent()){
            contact.get().setStatus(SchoolConstants.CLOSE);
            contactRepository.save(contact.get());
        }else{
            response.setStatusCode("400");
            response.setStatusMessage("Invalid contactId received");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        response.setStatusCode("200");
        response.setStatusMessage("Messsage status turned to closed");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);


    }
}
