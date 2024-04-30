package vn.edu.nlu.web.chat.service;

import java.util.Optional;

public interface ChatService {
    Optional<Long> getChatId(Long senderId, Long recipientId, boolean createNewRoomIfNotExists);
}
