package com.zenbyte.service;

import com.zenbyte.model.Contact;
import com.zenbyte.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public List<Contact> getContactsByStatus(String status) {
        return contactRepository.findByStatus(status);
    }

    public Contact updateContact(Long id, Contact contact) {
        Optional<Contact> existing = contactRepository.findById(id);
        if (existing.isPresent()) {
            Contact c = existing.get();
            if (contact.getFullName() != null) c.setFullName(contact.getFullName());
            if (contact.getEmail() != null) c.setEmail(contact.getEmail());
            if (contact.getPhone() != null) c.setPhone(contact.getPhone());
            if (contact.getSubject() != null) c.setSubject(contact.getSubject());
            if (contact.getService() != null) c.setService(contact.getService());
            if (contact.getMessage() != null) c.setMessage(contact.getMessage());
            if (contact.getStatus() != null) c.setStatus(contact.getStatus());
            return contactRepository.save(c);
        }
        return null;
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
