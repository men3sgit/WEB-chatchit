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
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.Message;
import vn.edu.nlu.web.chat.repository.MessageRepository;
import vn.edu.nlu.web.chat.service.ChatService;
import vn.edu.nlu.web.chat.service.MessageService;
import vn.edu.nlu.web.chat.utils.DataUtils;

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
    public MessageUpdateResponse update(Long chatId, Long id, MessageUpdateRequest request) {
        if (!chatService.exist(chatId)) {
            throw new ResourceNotFoundException("Chat does not exist");
        }
        Message storedMessage = getMessageById(id);
        storedMessage.setContent(request.getContent());
        messageRepository.save(storedMessage);
        return DataUtils.copyProperties(storedMessage, MessageUpdateResponse.class);
    }

    @Override
    public void delete(Long chatId, Long id) {
        if (chatService.exist(chatId)) {
            throw new ResourceNotFoundException("Chat does not exist");
        }
        Message storedMessage = getMessageById(id);
        storedMessage.setEntityStatus(EntityStatus.DELETED);
        messageRepository.save(storedMessage);

    }

    @Override
    public MessageDetailsResponse getDetailsById(Long id) {
        return null;
    }

    @Override
    public PageResponse<?> searchMessagesWithPaginationAndSorting(long chatId, int pageNo, int pageSize, String query, String sortBy) {
        if (!chatService.exist(chatId)) {
            throw new ResourceNotFoundException("Chat does not exist");
        }
        return messageRepository.searchMessagesWithPaginationAndSorting(chatId, pageNo, pageSize, query, sortBy);
    }

    private Message getMessageById(Long id) {
        return messageRepository
                .findByIdAndEntityStatusNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
    }
}


