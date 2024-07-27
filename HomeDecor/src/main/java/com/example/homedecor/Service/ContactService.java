package com.example.homedecor.Service;


import com.example.homedecor.Entity.Contact;

import java.util.List;

public interface ContactService {
    Contact saveContact(Contact contact);
    List<Contact> getAllContacts();
    Long contactCount();
}
