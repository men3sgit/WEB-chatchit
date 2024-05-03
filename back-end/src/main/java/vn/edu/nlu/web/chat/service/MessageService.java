package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.requests.message.MessageCreateRequest;
import vn.edu.nlu.web.chat.dto.requests.message.MessageUpdateRequest;
import vn.edu.nlu.web.chat.dto.responses.common.PageResponse;
import vn.edu.nlu.web.chat.dto.responses.message.MessageCreateResponse;

public interface MessageService {
    MessageCreateResponse create(MessageCreateRequest request);

    Object update(Long id, MessageUpdateRequest request);

    PageResponse<?> search(String query);

    void delete(Long id);

    Object getById(Long id);
}
