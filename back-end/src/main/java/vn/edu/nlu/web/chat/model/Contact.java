package vn.edu.nlu.web.chat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import vn.edu.nlu.web.chat.enums.ContactStatus;

@Data
@Entity
@Table(name = "contact")
public class Contact extends AbstractEntity {
    @Column(name = "email1", unique = true, nullable = false)
    private String email1;

    @Column(name = "email2", nullable = false)
    private String email2;

    @Column(name = "status", nullable = false)
    private String status;

}
