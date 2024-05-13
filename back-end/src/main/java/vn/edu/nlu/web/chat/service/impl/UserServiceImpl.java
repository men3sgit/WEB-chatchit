package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.config.locale.Translator;

import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.mapper.UserDetailsMapper;
import vn.edu.nlu.web.chat.dto.user.request.UserCreateRequest;
import vn.edu.nlu.web.chat.dto.user.request.UserUpdateRequest;
import vn.edu.nlu.web.chat.dto.user.response.UserCreateResponse;
import vn.edu.nlu.web.chat.dto.user.response.UserDetailsResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.enums.SocialStatus;
import vn.edu.nlu.web.chat.enums.TokenType;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.Token;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.UserRepository;
import vn.edu.nlu.web.chat.service.EmailService;
import vn.edu.nlu.web.chat.service.TokenService;
import vn.edu.nlu.web.chat.service.UserService;
import vn.edu.nlu.web.chat.utils.DataUtils;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public boolean exists(Long id) {
        return userRepository.findByIdAndEntityStatusNotDeleted(id).isPresent();
    }

    @Override
    public boolean exists(String username) {
        User storedUser = userRepository.findByEmail(username).orElseThrow(() -> new ApiRequestException("User not found"));
        return exists(storedUser.getId());
    }

    @Override
    public UserCreateResponse create(UserCreateRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.error("Email already exists: {}", request.getEmail());
            throw new ApiRequestException("Email taken!");
        }
        try {
            User newUser = DataUtils.copyProperties(request, User.class);
            newUser.setSocialStatus(SocialStatus.OFFLINE);
            newUser.setAppName(request.getEmail().split("@")[0]);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);

            Token verificationToken = Token.builder()
                    .userId(newUser.getId())
                    .type(TokenType.VERIFICATION)
                    .expiredAt(DateUtils.addDays(new Date(), 1))
                    .value(UUID.randomUUID().toString())
                    .build();

            tokenService.save(verificationToken);
            emailService.sendVerificationNewUser(newUser.getEmail(), verificationToken.getValue());
            return DataUtils.copyProperties(newUser, UserCreateResponse.class);

        } catch (ResourceNotFoundException e) {
            throw new ApiRequestException(e.getMessage());
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
