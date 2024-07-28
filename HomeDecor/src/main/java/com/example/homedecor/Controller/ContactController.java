package com.example.homedecor.Controller;



import com.example.homedecor.Entity.Contact;
import com.example.homedecor.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        Contact savedContact = contactService.saveContact(contact);
        return ResponseEntity.ok(savedContact);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/count")

    public ResponseEntity<Long> getContactCount() {
        Long count = contactService.contactCount();
        return ResponseEntity.ok(count);
    }
}
