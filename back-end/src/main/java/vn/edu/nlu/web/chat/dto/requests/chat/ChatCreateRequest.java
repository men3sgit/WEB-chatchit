package vn.edu.nlu.web.chat.dto.requests.chat;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatCreateRequest {

    private Long senderId;

    private Long recipientId;

    @NotEmpty(message = "Chat name cannot be empty")
    private String name;

}
