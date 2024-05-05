package vn.edu.nlu.web.chat.dto.message.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.edu.nlu.web.chat.utils.DateUtils;

import java.util.Date;

@Getter
@Setter
public class MessageCreateRequest {
    @NotNull(message = "Sender ID cannot be null")
    private Long senderId;

    @NotEmpty(message = "Message content cannot be empty")
    private String content;

    @NotNull(message = "Timestamp cannot be null")
    @PastOrPresent(message = "Timestamp must be in the past or present")
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT2)
    private Date timestamp;

}
