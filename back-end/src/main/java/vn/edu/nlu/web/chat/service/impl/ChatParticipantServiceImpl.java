package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.model.ChatParticipant;
import vn.edu.nlu.web.chat.service.ChatParticipantService;

@RequiredArgsConstructor
@Service
public class ChatParticipantServiceImpl implements ChatParticipantService {

    @Override
    public ChatParticipant createChatParticipant(Long chatId, Long userId) {
        return null;
    }
}
