package vn.edu.nlu.web.chat.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.custom.UserChatRepositoryCustom;

@Slf4j
@Repository
public class UserChatRepositoryCustomImpl implements UserChatRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public boolean doesUserChatExist(Long userId, Long chatId) {
        boolean exists = true;
        try {
            log.info("Checking if User with ID={} exists", userId);
            User user = entityManager.find(User.class, userId);
            if (user == null) {
                exists = false;
                log.error("User with ID={} does not exist", userId);
            } else {
                log.info("User with ID={} exists", userId);
            }

            log.info("Checking if Chat with ID={} exists", chatId);
            Chat chat = entityManager.find(Chat.class, chatId);
            if (chat == null) {
                exists = false;
                log.error("Chat with ID={} does not exist", chatId);
            } else {
                log.info("Chat with ID={} exists", chatId);
            }

            // Both user and chat exist
            return exists;
        } catch (Exception e) {
            log.error("Error occurred while checking UserChat existence", e);
            throw new ApiRequestException(Translator.toLocale("message.create.fail"));
        }
    }
}
