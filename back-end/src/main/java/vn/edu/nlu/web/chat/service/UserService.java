package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.requests.user.UserCreateRequest;
import vn.edu.nlu.web.chat.dto.requests.user.UserUpdateRequest;
import vn.edu.nlu.web.chat.dto.responses.common.PageResponse;
import vn.edu.nlu.web.chat.dto.responses.user.UserCreateResponse;
import vn.edu.nlu.web.chat.dto.responses.user.UserDetailsResponse;


public interface UserService {

    UserCreateResponse createUser(UserCreateRequest request);


    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);

    void deleteUser(long userId);

    void updateUser(long userId, UserUpdateRequest request);


    UserDetailsResponse getUserDetailsById(long userId);

    PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy);
}
