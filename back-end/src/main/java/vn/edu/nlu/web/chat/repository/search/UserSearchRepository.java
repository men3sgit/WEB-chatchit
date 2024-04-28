package vn.edu.nlu.web.chat.repository.search;

import vn.edu.nlu.web.chat.dto.responses.common.PageResponse;
import vn.edu.nlu.web.chat.dto.responses.user.UserDetailsResponse;

public interface UserSearchRepository {
    PageResponse<?> searchUsersWithPaginationAndSorting(int pageNo, int pageSize, String search, String sortBy);
}
