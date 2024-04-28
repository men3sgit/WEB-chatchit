package vn.edu.nlu.web.chat.dto.requests.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import vn.edu.nlu.web.chat.dto.validator.annotations.GenderSubset;
import vn.edu.nlu.web.chat.enums.Gender;

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
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    private String phone;


    private Date dob;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})
    private Gender gender;

}
