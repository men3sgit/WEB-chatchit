package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.requests.message.MessageCreateRequest;
import vn.edu.nlu.web.chat.dto.responses.message.MessageCreateResponse;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.Message;
import vn.edu.nlu.web.chat.repository.MessageRepository;
import vn.edu.nlu.web.chat.repository.UserChatRepository;
import vn.edu.nlu.web.chat.service.MessageService;
import vn.edu.nlu.web.chat.utils.DataUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserChatRepository userChatRepository;

    @Override
    public MessageCreateResponse create(MessageCreateRequest request) {
        // Check if chatId exists
        if (!userChatRepository.doesUserChatExist(request.getSenderId(), request.getChatId())) {
            throw new ResourceNotFoundException("chat does not exist");
        }
        try {
            // Map MessageCreateRequest to Message entity
            Message newMessage = DataUtils.copyProperties(request, Message.class);

            // Save the Message entity to the database
            messageRepository.save(newMessage);


            return DataUtils.copyProperties(newMessage, MessageCreateResponse.class);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error creating message: {}", e.getMessage());
            throw new ApiRequestException(Translator.toLocale("message.create.fail"));
        }
    }
}


