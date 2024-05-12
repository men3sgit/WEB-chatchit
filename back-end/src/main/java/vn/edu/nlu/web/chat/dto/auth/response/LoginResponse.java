package vn.edu.nlu.web.chat.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;

@Getter
@AllArgsConstructor
public class LoginResponse extends EntityResponse {
    private String token;
}
