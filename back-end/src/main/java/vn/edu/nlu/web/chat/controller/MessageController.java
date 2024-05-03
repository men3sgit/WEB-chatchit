package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.requests.message.MessageCreateRequest;
import vn.edu.nlu.web.chat.dto.requests.message.MessageUpdateRequest;
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

    @Operation(summary = "Update Message", description = "API to update an existing message.")
    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable("id") Long id, @RequestBody @Valid MessageUpdateRequest request) {
        log.info("Request to update Message with ID {}: {}", id, request);
        var res = messageService.update(id, request);
        log.info("Message with ID {} successfully updated", id);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("message.update.success"), res);
    }

    @Operation(summary = "Search Messages", description = "API to search messages.")
    @GetMapping
    public ApiResponse<?> search(@RequestParam(value = "query", required = false) String query) {
        log.info("Searching for messages with query: {}", query);
        var messages = messageService.search(query);
        log.info("Found {} messages matching the query", messages);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("message.search.success"), messages);
    }

    @Operation(summary = "Delete Message", description = "API to delete a message by ID.")
    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable("id") Long id) {
        log.info("Request to delete Message with ID: {}", id);
        messageService.delete(id);
        log.info("Message with ID {} successfully deleted", id);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("message.delete.success"));
    }

    @Operation(summary = "Get Message by ID", description = "API to get a message by ID.")
    @GetMapping("/{id}")
    public ApiResponse<?> getById(@PathVariable("id") Long id) {
        log.info("Request to get Message with ID: {}", id);
        var message = messageService.getById(id);
        log.info("Message with ID {} found: {}", id, message);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("message.get.success"), message);
    }
}