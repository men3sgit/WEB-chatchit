package vn.edu.nlu.web.chat.repository.custom.impl;
import vn.edu.nlu.web.chat.repository.custom.ColorRepositoryCustom;

import java.util.Optional;

public class ColorRepositoryCustomImpl implements ColorRepositoryCustom {
    @Override
    public Optional<Object> findByIdAndEntityStatusNotDeleted(Long id) {
        return Optional.empty();
    }
}
