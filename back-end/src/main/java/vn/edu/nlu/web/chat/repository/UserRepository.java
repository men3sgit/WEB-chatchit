package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.search.UserSearchRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , UserSearchRepository {

    Optional<User> findByEmail(String email);


}
