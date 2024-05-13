package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.nlu.web.chat.dto.chat.request.ChatCreateRequest;
import vn.edu.nlu.web.chat.dto.color.request.ColorCreateRequest;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.service.ColorService;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/colors")
public class ColorController {
    private final ColorService colorService;

    @Operation(summary = "Create Color", description = "API to create a new Color.")
    @PostMapping
    public ApiResponse<?> create(@RequestBody @Valid ColorCreateRequest request) {
        log.info("Request to create a new Color: {}", request);
        var color = colorService.create(request);
        log.info("Color created successfully with ID: {}", color.getId());
        return new ApiResponse<>(HttpStatus.CREATED, "Chat created successfully", color);
    }
}
