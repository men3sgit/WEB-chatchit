package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.dto.responses.common.PageResponse;
import vn.edu.nlu.web.chat.model.ChatParticipant;
import vn.edu.nlu.web.chat.repository.ChatParticipantRepository;
import vn.edu.nlu.web.chat.service.ChatParticipantService;

@Service
@RequiredArgsConstructor
public class ChatParticipantServiceImpl implements ChatParticipantService {
    private final ChatParticipantRepository chatParticipantRepository;

    @Override
    public void addParticipantToChat(Long userId, Long chatId) {
// Check if the participant already exists in the chat
        if (!chatParticipantRepository.isParticipantInChat(userId, chatId)) {
            // If not, create a new chat participant entry
            ChatParticipant chatParticipant = new ChatParticipant();
            chatParticipant.setUserId(userId);
            chatParticipant.setChatId(chatId);
            chatParticipantRepository.save(chatParticipant);
        }
    }

    @Override
    public void removeParticipantFromChat(Long userId, Long chatId) {

    }

    @Override
    public PageResponse<?> getParticipantsOfChat(Long chatId) {
        return null;
    }

    @Override
    public PageResponse<?> getParticipantDetailsOfChat(Long chatId) {
        return null;
    }

    @Override
    public boolean isParticipantInChat(Long userId, Long chatId) {
        return false;
    }
}
