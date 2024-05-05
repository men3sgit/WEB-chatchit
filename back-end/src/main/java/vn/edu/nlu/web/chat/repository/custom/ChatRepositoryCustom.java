package vn.edu.nlu.web.chat.repository.custom;

import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.model.ChatDetails;

import java.util.Optional;

public interface ChatRepositoryCustom {

    Optional<Chat> findChatBySenderId(
            Long senderId,
            boolean createNewChatIfNotExists
    );

    void addUserToChatRoom(Long chatId, Long userId);

    Optional<ChatDetails> findChatDetailsByChatId(Long chatId);

    Optional<Chat> findByIdAndEntityStatusNotDeleted(Long id);
}
