package vn.edu.nlu.web.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.contact.reponse.ContactAddResponse;
import vn.edu.nlu.web.chat.dto.contact.reponse.ContactUnResponse;
import vn.edu.nlu.web.chat.dto.contact.request.ContactAddRequest;
import vn.edu.nlu.web.chat.dto.contact.request.ContactUnRequest;
import vn.edu.nlu.web.chat.dto.message.response.MessageCreateResponse;
import vn.edu.nlu.web.chat.enums.ContactStatus;
import vn.edu.nlu.web.chat.model.Contact;
import vn.edu.nlu.web.chat.repository.ContactRepository;
import vn.edu.nlu.web.chat.service.ContactService;
import vn.edu.nlu.web.chat.utils.DataUtils;

public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Override
    public ContactAddResponse addContact(ContactAddRequest request) {
        Contact contact = new Contact();
        String emailUser = "men@gmail.com"; //email của người gửi request
        contact.setEmail1(emailUser);
        contact.setEmail2(request.getEmailContact());
        contact.setStatus(ContactStatus.PENDING.name()); // Set default status to PENDING
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
        return null;
    }

    @Override
    public PageResponse<?> search(int pageNo, int pageSize, String search, String sortBy) {
        return null;
    }

    public static void main(String[] args) {
//        ContactAddRequest contactAddRequest=new ContactAddRequest("men1@gmail.com","menabc","mến óc chó");
//        ContactService contactService ;
//        ContactAddResponse contactAddResponse= contactService.addContact(contactAddRequest);
    }
}
