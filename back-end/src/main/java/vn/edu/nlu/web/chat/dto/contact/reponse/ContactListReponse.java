package vn.edu.nlu.web.chat.dto.contact.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;
import vn.edu.nlu.web.chat.dto.contact.ContactDTO;
@Data
@AllArgsConstructor
public class ContactListReponse extends EntityResponse {
    private ContactDTO contactDTO;
}
