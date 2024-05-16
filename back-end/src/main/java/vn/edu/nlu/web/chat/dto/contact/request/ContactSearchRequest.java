package vn.edu.nlu.web.chat.dto.contact.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactSearchRequest {
    @NotNull(message = "email cannot be null")
    private String emailContact;
}
