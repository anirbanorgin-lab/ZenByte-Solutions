package com.zenbyte.repository;

import com.zenbyte.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByEmail(String email);
    List<Contact> findByStatus(String status);
}
