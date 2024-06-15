package vn.edu.nlu.web.chat.dto.message.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;
import vn.edu.nlu.web.chat.enums.MessageStatus;

import java.util.Date;

import static vn.edu.nlu.web.chat.utils.DateUtils.DATE_TIME_FORMAT2;

@Getter
@Setter
public class MessageDetailsResponse extends EntityResponse {

    @JsonProperty("text")
    private String content;
    private MessageStatus messageStatus;

    @JsonFormat(pattern = DATE_TIME_FORMAT2)
    public Date getTime() {
        return super.getCreatedAt();
    }

    // TODO write META

}
