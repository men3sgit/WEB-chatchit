package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.contact.reponse.ContactAddResponse;
import vn.edu.nlu.web.chat.dto.contact.request.ContactAddRequest;
import vn.edu.nlu.web.chat.dto.contact.request.ContactUnRequest;
import vn.edu.nlu.web.chat.enums.ContactStatus;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.Contact;
import vn.edu.nlu.web.chat.repository.ContactRepository;
import vn.edu.nlu.web.chat.service.ContactService;
import vn.edu.nlu.web.chat.service.UserService;
import vn.edu.nlu.web.chat.utils.DataUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final UserService userService;

    @Override
    public boolean exits(String emailUser,String emailContact) {
//        log.info("exits {}",contactRepository.exits(email));
        return contactRepository.exits(emailUser,emailContact);
    }

    @Override
    public ContactAddResponse addContact(ContactAddRequest request) {
        Contact contact = new Contact();
        String emailUser = "men@gmail.com"; //email của người gửi request
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
        return DataUtils.copyProperties(contact, ContactAddResponse.class);
    }

    @Override
    public void unContact(ContactUnRequest request) {
        Long contactId = request.getIdContact();
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + contactId));

        // Xóa contact khỏi cơ sở dữ liệu
        contactRepository.delete(contact);
    }

    @Override
    public PageResponse<?> list(int pageNo, int pageSize, String sortBy) {
        String emailUser = "men@gmail.com";
        return contactRepository.list(emailUser, pageNo, pageSize, sortBy);
    }

    @Override
    public PageResponse<?> search(int pageNo, int pageSize, String search, String sortBy) {
        String emailUser = "men@gmail.com";

        return contactRepository.search(emailUser, pageNo, pageSize, search, sortBy);
    }


}
