package vn.edu.nlu.web.chat.repository.search;

import vn.edu.nlu.web.chat.dto.responses.common.PageResponse;
import vn.edu.nlu.web.chat.model.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    PageResponse<?> searchUsersWithPaginationAndSorting(int pageNo, int pageSize, String search, String sortBy);

    Optional<User> findByIdAndEntityStatusNotDeleted(Long id);
}
