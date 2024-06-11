package vn.edu.nlu.web.chat.dto.mapper;


import vn.edu.nlu.web.chat.dto.contact.ContactDTO;
import vn.edu.nlu.web.chat.model.User;

import java.util.function.Function;


public interface ContactDTOMapper extends Function<User, ContactDTO> {
    //Tạo một mapper để ánh xạ giữa thực thể User và ContactDTO.

}
