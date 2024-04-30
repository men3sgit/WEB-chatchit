package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.requests.chat.ChatCreateRequest;
import vn.edu.nlu.web.chat.dto.responses.common.ApiResponse;
import vn.edu.nlu.web.chat.model.Chat;
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

    
    @Operation(summary = "Create Chat", description = "API to create a new chat")
    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody ChatCreateRequest request) {
        log.info("Request to create a new chat: {}", request);
        chatService.create(request);
        log.info("Chat successfully created with ID: {}", createdChat.getId());
        return new ApiResponse<>(HttpStatus.CREATED, Translator.toLocale("chat.create.success"));
    }


    @GetMapping
    public ResponseEntity<List<Chat>> getAllChats() {
        List<Chat> chats = chatService.getAllChats();
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> getChatById(@PathVariable Long chatId) {
        Chat chat = chatService.getChatById(chatId);
        return ResponseEntity.ok(chat);
    }

    @PutMapping("/{chatId}")
    public ResponseEntity<Chat> updateChat(@PathVariable Long chatId, @Valid @RequestBody ChatUpdateRequest chatUpdateRequest) {
        Chat updatedChat = chatService.updateChat(chatId, chatUpdateRequest);
        return ResponseEntity.ok(updatedChat);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.noContent().build();
    }
}
