package vn.edu.nlu.web.chat.dto.user.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.edu.nlu.web.chat.dto.validator.annotations.GenderSubset;
import vn.edu.nlu.web.chat.enums.Gender;
import vn.edu.nlu.web.chat.utils.DateUtils;

import java.util.Date;

import static vn.edu.nlu.web.chat.enums.Gender.*;

@Getter
public class UserCreateRequest {

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotEmpty(message = "Name cannot be empty")
    private String firstName;

    private String lastName;

    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{8,11}+$", message = "Phone number must contain only digits")
    private String phone;

    @DateTimeFormat(pattern = DateUtils.DATE_FORMAT_2)
    private Date dob;

    @NotNull(message = "type must be not null")
    @GenderSubset(anyOf = {MALE,FEMALE,OTHER})
    private Gender gender;

}
