package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.dto.chat.request.ChatUpdateRequest;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.dto.theme.request.ThemeUpdateRequest;
import vn.edu.nlu.web.chat.service.ChatService;
import vn.edu.nlu.web.chat.service.ThemeService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/themes")
public class ThemeController {
    private final ThemeService themeService;

    @Operation(summary = "Update Themes", description = "API to update an existing User Themes.")
    @PutMapping(path = "/{id}")
    public ApiResponse<?> updateChat(@PathVariable("id") Long id, @RequestBody @Valid ThemeUpdateRequest request) {
        log.info("Request to update Theme with ID {}: {}", id, request);
        themeService.update(id, request);
        log.info("Theme with ID {} updated successfully", id);
        return new ApiResponse<>(HttpStatus.OK, "Theme updated successfully");
    }
}
