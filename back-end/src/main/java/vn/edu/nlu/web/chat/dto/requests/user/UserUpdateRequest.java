package vn.edu.nlu.web.chat.dto.requests.user;

import lombok.Getter;
import vn.edu.nlu.web.chat.enums.Gender;
import vn.edu.nlu.web.chat.enums.SocialStatus;

import java.util.Date;

@Getter
public class UserUpdateRequest {

    private String appName;

    private String firstName;

    private String lastName;

    private String phone;

    private Date dateOfBirth;

    private Gender gender;


}
