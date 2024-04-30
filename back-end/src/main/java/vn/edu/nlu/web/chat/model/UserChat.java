package vn.edu.nlu.web.chat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_chats")
public class UserChat extends AbstractEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chat_id")
    private Long chatId;
}
