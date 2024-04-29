package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.mapper.UserDetailsMapper;
import vn.edu.nlu.web.chat.dto.requests.user.UserCreateRequest;
import vn.edu.nlu.web.chat.dto.requests.user.UserUpdateRequest;
import vn.edu.nlu.web.chat.dto.responses.common.PageResponse;
import vn.edu.nlu.web.chat.dto.responses.user.UserCreateResponse;
import vn.edu.nlu.web.chat.dto.responses.user.UserDetailsResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.UserRepository;
import vn.edu.nlu.web.chat.service.UserService;
import vn.edu.nlu.web.chat.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static vn.edu.nlu.web.chat.utils.AppConst.SORT_BY;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;


    @Override
    public UserCreateResponse createUser(UserCreateRequest request) {
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
    public PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy) {
        int page = 0;
        if (pageNo > 0) {
            page = pageNo - 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();

        if (StringUtils.hasLength(sortBy)) {
            // firstName:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));

        Page<User> users = userRepository.findAll(pageable);
        List<UserDetailsResponse> response = users.stream()
                .map(userDetailsMapper)
                .toList();

        return PageResponse.builder()
                .page(pageNo)
                .size(pageSize)
                .total(users.getTotalPages())
                .items(response)
                .build();

    }

    @Override
    public void deleteUser(long userId) {
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
    public void updateUser(long userId, UserUpdateRequest request) {
//        try{
//            User storedUser = getUserById(userId);
//        }

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
            return userRepository.searchUsersWithPaginationAndSorting(pageNo, pageSize, search, sortBy);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.list.fail"));
        }
    }

    private User getUserById(long id) {
        return userRepository.findByIdAndEntityStatusNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }
}
