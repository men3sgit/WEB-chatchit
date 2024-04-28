package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.requests.user.UserCreateRequest;
import vn.edu.nlu.web.chat.dto.responses.common.ApiResponse;
import vn.edu.nlu.web.chat.service.UserService;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Add new User", description = "API create a new user.")
    @PostMapping
    public ApiResponse<?> addNewUser(@RequestBody @Valid UserCreateRequest request) {
        log.info("Request add new User: {}", request);
        var res = userService.createUser(request);
        log.info("User successfully created with ID: {}", res.getId());
        return new ApiResponse<>(HttpStatus.CREATED, Translator.toLocale("user.add.success"), res);

    }

    @Operation(summary = "Get list of users per pageNo", description = "Send a request via this API to get user list by pageNo and pageSize")
    @GetMapping
    public ApiResponse<?> getAllUsers(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                      @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                      @RequestParam(required = false) String sortBy) {
        log.info("Request get all of users");
        var res = userService.getAllUsersWithSortBy(pageNo, pageSize, sortBy);
        log.info("Users successfully retrieved with total: {} ", res.getTotal());
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("user.list.success"), res);
    }


}
