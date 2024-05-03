package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.dto.requests.chat.ChatCreateRequest;
import vn.edu.nlu.web.chat.dto.requests.chat.ChatUpdateRequest;
import vn.edu.nlu.web.chat.dto.responses.common.ApiResponse;
import vn.edu.nlu.web.chat.service.ChatService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/chats")
@Tag(name = "Chat Controller", description = "Endpoints for managing chats")
public class ChatController {

    private final ChatService chatService;


    @Operation(summary = "Create Chat", description = "API to create a new chat.")
    @PostMapping
    public ApiResponse<?> create(@RequestBody @Valid ChatCreateRequest request) {
        log.info("Request to create a new chat: {}", request);
        var chat = chatService.create(request);
        log.info("Chat created successfully with ID: {}", chat.getId());
        return new ApiResponse<>(HttpStatus.CREATED, "Chat created successfully", chat);
    }

    @Operation(summary = "Get Chat by ID", description = "API to get a chat by ID.")
    @GetMapping("/{id}")
    public ApiResponse<?> getDetails(@PathVariable("id") Long id) {
        log.info("Request to get chat by ID: {}", id);
        var chat = chatService.getChatDetailsById(id);
        log.info("Chat found: {}", chat);
        return new ApiResponse<>(HttpStatus.OK, "Chat retrieved successfully", chat);
    }

    @Operation(summary = "Update Chat", description = "API to update an existing chat.")
    @PutMapping("/{id}")
    public ApiResponse<?> updateChat(@PathVariable("id") Long id, @RequestBody @Valid ChatUpdateRequest request) {
        log.info("Request to update chat with ID {}: {}", id, request);
        chatService.update(id, request);
        log.info("Chat with ID {} updated successfully", id);
        return new ApiResponse<>(HttpStatus.OK, "Chat updated successfully");
    }

    @Operation(summary = "Delete Chat", description = "API to delete a chat by ID.")
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteChat(@PathVariable("id") Long id) {
        log.info("Request to delete chat with ID: {}", id);
        chatService.delete(id);
        log.info("Chat with ID {} deleted successfully", id);
        return new ApiResponse<>(HttpStatus.OK, "Chat deleted successfully");
    }
}
