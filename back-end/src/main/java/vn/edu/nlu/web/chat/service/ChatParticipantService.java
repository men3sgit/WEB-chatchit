package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.responses.common.PageResponse;

public interface ChatParticipantService {
    void addParticipantToChat(Long userId, Long chatId);

    void removeParticipantFromChat(Long userId, Long chatId);

    PageResponse<?> getParticipantsOfChat(Long chatId);

    PageResponse<?> getParticipantDetailsOfChat(Long chatId);

    boolean isParticipantInChat(Long userId, Long chatId);
}
