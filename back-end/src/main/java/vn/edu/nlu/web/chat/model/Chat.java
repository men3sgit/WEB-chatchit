package vn.edu.nlu.web.chat.model;

import jakarta.persistence.*;
import vn.edu.nlu.web.chat.enums.ChatType;

@Entity
@Table(name = "chats")
public class Chat extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ChatType type; // This could be "ONE_ON_ONE" or "GROUP"
}
