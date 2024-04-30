package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.model.UserChat;
import vn.edu.nlu.web.chat.repository.custom.UserChatRepositoryCustom;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long>, UserChatRepositoryCustom {

}
