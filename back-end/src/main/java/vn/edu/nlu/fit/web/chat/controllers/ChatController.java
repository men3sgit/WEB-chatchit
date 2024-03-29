package vn.edu.nlu.fit.web.chat.controllers;

import vn.edu.nlu.fit.web.chat.documents.ChatMessage;
import vn.edu.nlu.fit.web.chat.documents.ChatNotification;
import vn.edu.nlu.fit.web.chat.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/messages/{sender}/{recipient}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(
            @PathVariable(name = "sender") Long senderId,
            @PathVariable(name = "recipient") Long recipientId) {

        var chatMessages = chatMessageService.getChatMessages(senderId, recipientId);
        return ResponseEntity.ok(chatMessages);
    }

    @MessageMapping("/chat")
    public void processChatMessage(@Payload ChatMessage msg) {
        ChatMessage savedMsg = chatMessageService.save(msg);
        if (msg.getChatId() != null) { // Group chat message
            messagingTemplate.convertAndSend("/topic/chat/group/" + msg.getChatId(),
                    ChatNotification.builder()
                            .content(savedMsg.getContent())
                            .senderId(savedMsg.getSenderId())
                            .recipientId(savedMsg.getRecipientId()) // Consider omitting for group messages
                            .build());
        }
    }
}