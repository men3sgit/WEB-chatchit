package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.requests.message.MessageCreateRequest;
import vn.edu.nlu.web.chat.dto.responses.message.MessageCreateResponse;

public interface MessageService {
    MessageCreateResponse create(MessageCreateRequest request);
}
