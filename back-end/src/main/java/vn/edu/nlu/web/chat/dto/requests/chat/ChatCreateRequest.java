package vn.edu.nlu.web.chat.dto.requests.chat;

import lombok.Getter;
import lombok.Setter;
import vn.edu.nlu.web.chat.enums.ChatType;

import java.util.List;

@Getter
@Setter
public class ChatCreateRequest {

    private List<Long> participantIds;

    private ChatType chatType;

}
