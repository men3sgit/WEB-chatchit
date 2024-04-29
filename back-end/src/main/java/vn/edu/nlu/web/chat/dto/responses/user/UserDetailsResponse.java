package vn.edu.nlu.web.chat.dto.responses.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import vn.edu.nlu.web.chat.dto.responses.common.EntityResponse;
import vn.edu.nlu.web.chat.utils.DateUtils;

import java.util.Date;


@Getter
@Setter
public class UserDetailsResponse extends EntityResponse {

    private String appName;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String avatarUrl;

    @JsonFormat(pattern = DateUtils.DATE_FORMAT_2)
    private Date dob;

    private String roles;

}
