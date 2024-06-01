package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.model.Contact;
import vn.edu.nlu.web.chat.repository.custom.ContactRepositoryCustom;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> , ContactRepositoryCustom {
    @Query("SELECT c FROM Contact  c WHERE c.email1 = :email OR c.email2 =:email")
    List<Contact> findByEmail1OrEmail2(String email);
}
