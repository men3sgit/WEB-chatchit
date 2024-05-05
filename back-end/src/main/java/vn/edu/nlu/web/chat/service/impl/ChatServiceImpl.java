package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.dto.chat.request.ChatCreateRequest;
import vn.edu.nlu.web.chat.dto.chat.request.ChatUpdateRequest;
import vn.edu.nlu.web.chat.dto.chat.response.ChatCreateResponse;
import vn.edu.nlu.web.chat.dto.chat.response.ChatDetailsResponse;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.model.ChatParticipant;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.ChatParticipantRepository;
import vn.edu.nlu.web.chat.repository.ChatRepository;
import vn.edu.nlu.web.chat.repository.UserRepository;
import vn.edu.nlu.web.chat.service.ChatService;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatParticipantRepository chatParticipantRepository;


    @Override
    public ChatCreateResponse create(ChatCreateRequest request) {
        checkUsersExist(request.getParticipantIds());
        Chat newChat = new Chat();
        chatRepository.save(newChat);
    // TODO

        // Create ChatParticipants for each participant ID
        for (Long participantId : request.getParticipantIds()) {
            ChatParticipant participant = new ChatParticipant();
            participant.setChatId(newChat.getId());
            participant.setParticipantId(participantId);
            // Set default role or any other fields as needed
            chatParticipantRepository.save(participant);
        }


        return null;
    }

    private void checkUsersExist(List<Long> participantIds) {
        for (Long userId : participantIds) {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new ApiRequestException("User with ID " + userId + " does not exist");
            }
        }
    }

    @Override
    public ChatDetailsResponse getChatDetailsById(Long id) {
        return null;
    }

    @Override
    public void update(Long id, ChatUpdateRequest request) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean exist(Long id) {
        return chatRepository.findByIdAndEntityStatusNotDeleted(id).isPresent();
    }
}
