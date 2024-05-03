package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.requests.chat.ChatCreateRequest;
import vn.edu.nlu.web.chat.dto.requests.chat.ChatUpdateRequest;
import vn.edu.nlu.web.chat.dto.responses.chat.ChatCreateResponse;
import vn.edu.nlu.web.chat.dto.responses.chat.ChatDetailsResponse;

import java.util.Optional;

public interface ChatService {
    Optional<Long> getChatId(Long senderId, Long recipientId, boolean createNewRoomIfNotExists);

    ChatCreateResponse create(ChatCreateRequest request);

    ChatDetailsResponse getChatDetailsById(Long id);

    void update(Long id, ChatUpdateRequest request);

    void delete(Long id);
}
