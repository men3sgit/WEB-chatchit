package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.dto.message.request.MessageCreateRequest;
import vn.edu.nlu.web.chat.dto.message.request.MessageUpdateRequest;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.message.response.MessageCreateResponse;
import vn.edu.nlu.web.chat.dto.message.response.MessageDetailsResponse;
import vn.edu.nlu.web.chat.dto.message.response.MessageUpdateResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.enums.MessageStatus;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.Message;
import vn.edu.nlu.web.chat.repository.MessageRepository;
import vn.edu.nlu.web.chat.service.ChatService;
import vn.edu.nlu.web.chat.service.MessageService;
import vn.edu.nlu.web.chat.utils.DataUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;

    @Override
    public MessageCreateResponse create(Long chatId, MessageCreateRequest request) {
        if (!chatService.exist(chatId)) {
            throw new ResourceNotFoundException("Chat does not exist");
        }
        Message newMessage = new Message();
        newMessage.setChatId(chatId);
        newMessage.setMessageStatus(MessageStatus.SENT);
        newMessage.setContent(request.getContent());
        newMessage.setSenderId(request.getSenderId());
        newMessage.setTimestamp(request.getTimestamp());

        messageRepository.save(newMessage);
        return DataUtils.copyProperties(newMessage, MessageCreateResponse.class);
    }

    @Override
    public MessageUpdateResponse update(Long id, MessageUpdateRequest request) {
        Message storedMessage = getMessageById(id);
        storedMessage.setContent(request.getContent());
        messageRepository.save(storedMessage);
        return DataUtils.copyProperties(storedMessage, MessageUpdateResponse.class);
    }

    @Override
    public void delete(Long id) {
        Message storedMessage = getMessageById(id);
        storedMessage.setEntityStatus(EntityStatus.DELETED);
        messageRepository.save(storedMessage);

    }

    @Override
    public MessageDetailsResponse getDetails(Long id) {
        Message storedMessage = getMessageById(id);
        return DataUtils.copyProperties(storedMessage, MessageDetailsResponse.class);
    }

    @Override
    public PageResponse<?> searchMessagesWithPaginationAndSorting(long chatId, int pageNo, int pageSize, String query, String sortBy) {
        if (!chatService.exist(chatId)) {
            throw new ResourceNotFoundException("Chat does not exist");
        }
        return messageRepository.searchMessagesWithPaginationAndSorting(chatId, pageNo, pageSize, query, sortBy);
    }

    @Override
    public void seen(Long chatId) throws Exception {
        Long DUMMY_USER_ID = -1L;
        try {
            // Lấy danh sách các tin nhắn chưa đọc và có message_status là DELIVERED
            List<Message> unreadMessages = messageRepository.findMessagesByChatIdAndCreatedByNotAndStatusDelivered(chatId, DUMMY_USER_ID);
            // Đánh dấu các tin nhắn đã đọc
            for (Message message : unreadMessages) {
                message.setMessageStatus(MessageStatus.READ);
            }
            // Lưu các thay đổi vào cơ sở dữ liệu
            messageRepository.saveAll(unreadMessages);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            // ném một ngoại lệ tùy chỉnh để thông báo về lỗi
            throw new ApiRequestException("Error occurred while marking messages as read");
        }
    }

    private Message getMessageById(Long id) {
        return messageRepository
                .findByIdAndEntityStatusNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
    }
}


