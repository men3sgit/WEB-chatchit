package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.nlu.web.chat.model.Contact;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact  c WHERE c.email1 = :email OR c.email2 =:email")
    List<Contact> findByEmail1OrEmail2(String email);
}
