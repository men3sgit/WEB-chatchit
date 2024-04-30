package vn.edu.nlu.web.chat.repository.custom;

public interface UserChatRepositoryCustom {
    boolean doesUserChatExist(Long userId, Long chatId);
}
