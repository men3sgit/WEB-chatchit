package vn.edu.nlu.web.chat.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.model.ChatDetails;
import vn.edu.nlu.web.chat.repository.ChatRepository;
import vn.edu.nlu.web.chat.repository.custom.ChatRepositoryCustom;

import java.util.Optional;

@Repository
public class ChatRepositoryCustomImpl implements ChatRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


}
