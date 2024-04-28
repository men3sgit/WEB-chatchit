package vn.edu.nlu.web.chat.dto.responses.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import vn.edu.nlu.web.chat.dto.responses.common.EntityResponse;
import vn.edu.nlu.web.chat.utils.DateUtils;

import java.util.Date;

@Getter
@Builder
public final class UserDetailsResponse extends EntityResponse {
    private static final long serialVersionUID = 1L;

    private final String appName;

    private final String firstName;

    private final String lastName;

    private final String email;

    private final String phone;

    private final String avatarUrl;

    @JsonFormat(pattern = DateUtils.DATE_FORMAT_2)
    private final Date dob;

}
