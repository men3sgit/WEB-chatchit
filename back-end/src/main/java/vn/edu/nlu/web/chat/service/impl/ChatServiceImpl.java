package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.repository.ChatRepository;
import vn.edu.nlu.web.chat.service.ChatService;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public Optional<Long> getChatId(Long senderId, Long recipientId, boolean createNewRoomIfNotExists) {
        Optional<Chat> existingChatRoom = chatRepository.findByChatIdAndSenderId(, recipientId);

        if (existingChatRoom.isPresent()) {
            return existingChatRoom.map(Chat::getId);
        }

        if (createNewRoomIfNotExists) {
            return createChatId(senderId, recipientId);
        }

        return Optional.empty();
    }
}
