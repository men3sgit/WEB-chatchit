package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.model.Theme;
import vn.edu.nlu.web.chat.repository.custom.ChatRepositoryCustom;
import vn.edu.nlu.web.chat.repository.custom.ThemeRepositoryCustom;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long>, ThemeRepositoryCustom {
    Optional<Theme> findThemesById(Long id);
}
