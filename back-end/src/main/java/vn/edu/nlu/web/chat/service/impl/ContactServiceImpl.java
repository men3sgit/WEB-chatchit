package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.dto.chat.request.ChatCreateRequest;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.contact.request.ContactAddRequest;
import vn.edu.nlu.web.chat.dto.contact.request.ContactUnRequest;

import vn.edu.nlu.web.chat.dto.message.request.MessageCreateRequest;
import vn.edu.nlu.web.chat.enums.ChatType;
import vn.edu.nlu.web.chat.dto.contact.response.ContactAddResponse;

import vn.edu.nlu.web.chat.enums.ContactStatus;
import vn.edu.nlu.web.chat.enums.MessageStatus;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.Contact;

import vn.edu.nlu.web.chat.model.Message;
import vn.edu.nlu.web.chat.repository.ContactRepository;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.ContactRepository;
import vn.edu.nlu.web.chat.repository.UserRepository;
import vn.edu.nlu.web.chat.service.AuthenticationService;
import vn.edu.nlu.web.chat.service.ContactService;
import vn.edu.nlu.web.chat.service.UserService;
import vn.edu.nlu.web.chat.utils.DataUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final UserService userService;
    private final ChatService chatService;
    private final AuthenticationService authenticationService;
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Override
    public boolean exits(String emailUser, String emailContact) {
//        log.info("exits {}",contactRepository.exits(email));
        return contactRepository.exits(emailUser, emailContact);
    }

    @Override
    public ContactAddResponse addContact(ContactAddRequest request) {
        Contact contact = new Contact();
        String emailUser; //email của người gửi request
        Long currentUserId = authenticationService.getCurrentUserId();
        emailUser = userRepository.findEmailById(currentUserId).orElseThrow(() -> new RuntimeException("User id not found with id: " + currentUserId));
        if (!userService.exists(request.getEmail())) {
            log.error("User not found: {}", request.getEmail());
            throw new ResourceNotFoundException("user not found");
        }
        if (exits(emailUser, request.getEmail())) {
            log.error("This contact already exists: {}", request.getEmail());
            throw new ResourceNotFoundException("This contact already exists");
        }
        contact.setEmail1(emailUser);
        contact.setEmail2(request.getEmail());
        contact.setStatus(ContactStatus.PENDING); // Set default status to PENDING
        contactRepository.save(contact);

        ChatCreateRequest createNewChatRequest = new ChatCreateRequest();
        createNewChatRequest.setParticipantIds(List.of(
                userService.getIdByUsername(contact.getEmail1()),
                userService.getIdByUsername(contact.getEmail2())
        ));
        createNewChatRequest.setChatType(ChatType.ONE_ON_ONE);
        var newChatResponse = chatService.create(createNewChatRequest);

        MessageCreateRequest createNewMessageRequest = new MessageCreateRequest();
        createNewMessageRequest.setContent(request.getMessage());
        createNewMessageRequest.setTimestamp(new Date());
        createNewMessageRequest.setSenderId(authenticationService.getCurrentUserId());
        messageService.create(newChatResponse.getId(), createNewMessageRequest);

        return DataUtils.copyProperties(contact, ContactAddResponse.class);
    }

    @Override
    public void unContact(ContactUnRequest request) {
        String emailUser;
        Long currentUserId = authenticationService.getCurrentUserId();
        emailUser = userRepository.findEmailById(currentUserId).orElseThrow(() -> new RuntimeException("User id not found with id: " + currentUserId));
        String emailContact = userRepository.findEmailById(request.getIdContact()).orElseThrow(() -> new RuntimeException("User contact id not found with id: " + currentUserId));
        Contact contact = contactRepository.findByEmail1AndEmail2(emailUser, emailContact).orElseThrow(() -> new RuntimeException("Contact id not found with emailUser: {}" + emailUser + "emailContact: {}" + emailContact));
        contactRepository.delete(contact);
    }

    @Override
    public PageResponse<?> list(int pageNo, int pageSize, String sortBy) {
        String emailUser;
        Long currentUserId = authenticationService.getCurrentUserId();
        emailUser = userRepository.findEmailById(currentUserId).orElseThrow(() -> new RuntimeException("User id not found with id: " + currentUserId));
        return contactRepository.list(emailUser, pageNo, pageSize, sortBy);
    }

    @Override
    public PageResponse<?> search(int pageNo, int pageSize, String search, String sortBy) {
        String emailUser;
        Long currentUserId = authenticationService.getCurrentUserId();
        emailUser = userRepository.findEmailById(currentUserId).orElseThrow(() -> new RuntimeException("User id not found with id: " + currentUserId));
        return contactRepository.search(emailUser, pageNo, pageSize, search, sortBy);
    }

    @Override
    public PageResponse<?> getConversationByContactId(Long contactId) { // TODO
        var storedContact = getContactById(contactId);
        var email = storedContact.getEmail1();


        return null;
    }

    private Contact getContactById(Long id) {
        return contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
    }


}
