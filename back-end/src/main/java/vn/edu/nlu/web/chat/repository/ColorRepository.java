package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.model.Color;
import vn.edu.nlu.web.chat.repository.custom.ColorRepositoryCustom;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, Long>, ColorRepositoryCustom {
    Optional<Color> existsByValueAndEntityStatusIsNot(String value, EntityStatus entityStatus);
}
