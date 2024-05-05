package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.mapper.UserDetailsMapper;
import vn.edu.nlu.web.chat.dto.requests.user.UserCreateRequest;
import vn.edu.nlu.web.chat.dto.requests.user.UserUpdateRequest;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.user.response.UserCreateResponse;
import vn.edu.nlu.web.chat.dto.user.response.UserDetailsResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.UserRepository;
import vn.edu.nlu.web.chat.service.UserService;
import vn.edu.nlu.web.chat.utils.DataUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;


    @Override
    public UserCreateResponse create(UserCreateRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.error("Email already exists: {}", request.getEmail());
            throw new ApiRequestException("Email taken!");
        }
        try {
            User newUser = DataUtils.copyProperties(request, User.class);
            newUser.setAppName(newUser.getEmail().split("@")[0]);

            userRepository.save(newUser);
            return DataUtils.copyProperties(newUser, UserCreateResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.add.fail"));
        }

    }

    @Override
    public PageResponse<?> getAllUsersWithPagingAndSorting(int pageNo, int pageSize, String sortBy) {
        return getAllUsersAndSearchWithPagingAndSorting(pageNo, pageSize, "", sortBy);
    }

    @Override
    public void delete(long userId) {
        try {
            User storedUser = getUserById(userId);
            storedUser.setEntityStatus(EntityStatus.DELETED);
            userRepository.save(storedUser);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.delete.fail"));
        }
    }

    @Override
    public void update(long userId, UserUpdateRequest request) {
        try {
            User storedUser = getUserById(userId);

            userRepository.save(storedUser);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.update.fail"));
        }

    }

    @Override
    public UserDetailsResponse getUserDetailsById(long userId) {
        try {
            User storedUser = getUserById(userId);
            return DataUtils.copyProperties(storedUser, UserDetailsResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.get.fail"));
        }
    }

    @Override
    public PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy) {
        try {
            pageNo = pageNo > 1 ? pageNo - 1 : 0;
            return userRepository.searchUsersWithPaginationAndSorting(pageNo, pageSize, search, sortBy);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.list.fail"));
        }
    }

    private User getUserById(long id) {
        return userRepository.findByIdAndEntityStatusNotDeleted(id)
                .orElseThrow(() -> {
                    String msg = "User with id " + id + " not found";
                    log.error(msg);
                    return new ResourceNotFoundException(msg);
                });
    }
}
