package vn.edu.nlu.web.chat.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.repository.custom.ChatParticipantRepositoryCustom;

@Slf4j
@Repository
public class ChatParticipantRepositoryCustomImpl implements ChatParticipantRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public boolean isParticipantInChat(Long userId, Long chatId) {
        try {
            Long count = entityManager.createQuery(
                            "SELECT COUNT(cp) FROM ChatParticipant cp WHERE cp.userId = :userId " +
                                    "AND cp.chatId = :chatId AND cp.entityStatus != vn.edu.nlu.web.chat.enums.EntityStatus.DELETED",
                            Long.class)
                    .setParameter("userId", userId)
                    .setParameter("chatId", chatId)
                    .getSingleResult();

            return count != null && count > 0;
        } catch (Exception e) {
            log.error("Error checking if participant exists in chat: {}", e.getMessage());
            return false;
        }
    }
}
