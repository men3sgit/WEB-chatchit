package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.custom.UserRepositoryCustom;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , UserRepositoryCustom {

    Optional<User> findByEmail(String email);
    List<User> findAllByEmailIn(List<String> email);
}
