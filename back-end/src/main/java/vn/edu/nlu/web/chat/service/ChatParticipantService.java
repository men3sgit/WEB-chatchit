package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.model.ChatParticipant;

public interface ChatParticipantService {
    ChatParticipant createChatParticipant(Long chatId, Long userId);

}
