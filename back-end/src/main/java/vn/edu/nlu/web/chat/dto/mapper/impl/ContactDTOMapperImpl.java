package vn.edu.nlu.web.chat.dto.mapper.impl;

import org.springframework.stereotype.Component;
import vn.edu.nlu.web.chat.dto.contact.*;
import vn.edu.nlu.web.chat.model.*;
import vn.edu.nlu.web.chat.dto.mapper.ContactDTOMapper;

import java.util.Arrays;

/*
@Component: Đây là annotation chung nhất và thường được sử dụng để đánh dấu
một class là một Spring Bean. Nó chỉ ra rằng class được chú
 thích là một thành phần của ứng dụng Spring.

 */
@Component
public class ContactDTOMapperImpl implements ContactDTOMapper {

    public ContactDTO toContactDTO(User user) {
        if (user == null) {
            return null;
        }
        ContactDTO contactDTO = new ContactDTO(
                user.getId(),  // id
                user.getFirstName(),           // firstName
                user.getLastName(),            // lastName
                user.getEmail()                // email
        );
        // Gán sẵn dữ liệu cho các trường khác
        contactDTO.setProfileImage("avatar1_url");
        contactDTO.setAbout("If several languages coalesce, the grammar of the resulting.");
        contactDTO.setLocation("California, USA");
        contactDTO.setStatus("ACTIVE");
        contactDTO.setChannels(Arrays.asList(
                new ChannelDTO("61665bcb9a456823e282afa7", "Landing Design")
        ));
        contactDTO.setMedia(new MediaDTO(
                17,
                Arrays.asList(
                        new MediaItemDTO(1, "img1_url"),
                        new MediaItemDTO(2, "img2_url")
                )
        ));
        contactDTO.setAttachedFiles(new AttachedFilesDTO(
                4,
                Arrays.asList(
                        new AttachedFileDTO(1, "design-phase-1-approved.pdf", "12.5 MB", "#", "bx bx-file"),
                        new AttachedFileDTO(4, "Landing-A.zip", "6.7 MB", "#", "bx bx-file")
                )
        ));

        return contactDTO;
    }


    public User toUser(ContactDTO contactDTO) {
        if (contactDTO == null) {
            return null;
        }
        User user = new User();
        user.setEmail(contactDTO.getEmail());
        user.setFirstName(contactDTO.getFirstName());
        user.setLastName(contactDTO.getLastName());
        // Bổ sung các trường thông tin khác tùy ý
        return user;
    }

    @Override
    public ContactDTO apply(User user) {
        if (user == null) {
            return null;
        }
        ContactDTO contactDTO = new ContactDTO(
                user.getId(),  // id
                user.getFirstName(),           // firstName
                user.getLastName(),            // lastName
                user.getEmail()                // email
        );
        System.out.println(contactDTO);
        // Gán sẵn dữ liệu cho các trường khác
        contactDTO.setProfileImage("http://localhost:3000/static/media/avatar-4.474927d6a33a7b8cde52.jpg");
        contactDTO.setAbout("If several languages coalesce, the grammar of the resulting.");
        contactDTO.setLocation("California, USA");
        contactDTO.setStatus("ACTIVE");
        contactDTO.setChannels(Arrays.asList(
                new ChannelDTO("61665bcb9a456823e282afa7", "Landing Design")
        ));
        contactDTO.setMedia(new MediaDTO(
                17,
                Arrays.asList(
                        new MediaItemDTO(1, "img1_url"),
                        new MediaItemDTO(2, "img2_url")
                )
        ));
        contactDTO.setAttachedFiles(new AttachedFilesDTO(
                4,
                Arrays.asList(
                        new AttachedFileDTO(1, "design-phase-1-approved.pdf", "12.5 MB", "#", "bx bx-file"),
                        new AttachedFileDTO(4, "Landing-A.zip", "6.7 MB", "#", "bx bx-file")
                )
        ));

        return contactDTO;
    }
}