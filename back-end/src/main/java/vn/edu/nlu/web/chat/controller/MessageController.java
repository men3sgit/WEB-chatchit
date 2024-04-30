package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.requests.message.MessageCreateRequest;
import vn.edu.nlu.web.chat.dto.responses.common.ApiResponse;
import vn.edu.nlu.web.chat.service.MessageService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/messages")
@Tag(name = "Message Controller")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Add new Message", description = "API to add a new message.")
    @PostMapping
    public ApiResponse<?> create(@RequestBody @Valid MessageCreateRequest request) {
        log.info("Request to add a new Message: {}", request);
        var res = messageService.create(request);
        log.info("Message successfully created with ID: {}", res.getId());
        return new ApiResponse<>(HttpStatus.CREATED, Translator.toLocale("message.add.success"), res);
    }
}