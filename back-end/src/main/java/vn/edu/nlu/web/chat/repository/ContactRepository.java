package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.nlu.web.chat.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
