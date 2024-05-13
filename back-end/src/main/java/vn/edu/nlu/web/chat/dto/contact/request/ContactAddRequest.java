package vn.edu.nlu.web.chat.dto.contact.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactAddRequest {
    @NotNull(message = "email cannot be null")
    private String emailContact;
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "InvitetionMessage cannot be null")
    private String InvitetionMessage;
}
