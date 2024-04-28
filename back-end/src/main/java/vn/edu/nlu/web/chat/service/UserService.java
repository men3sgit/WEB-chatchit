package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.requests.user.UserCreateRequest;
import vn.edu.nlu.web.chat.dto.responses.common.PageResponse;
import vn.edu.nlu.web.chat.dto.responses.user.UserCreateResponse;
import vn.edu.nlu.web.chat.dto.responses.user.UserDetailsResponse;

import java.util.Collection;

public interface UserService {

    UserCreateResponse createUser(UserCreateRequest request);


    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);
}
