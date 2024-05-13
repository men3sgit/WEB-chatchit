package vn.edu.nlu.web.chat.service;


import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.contact.reponse.ContactAddResponse;
import vn.edu.nlu.web.chat.dto.contact.reponse.ContactUnResponse;
import vn.edu.nlu.web.chat.dto.contact.request.ContactAddRequest;
import vn.edu.nlu.web.chat.dto.contact.request.ContactListRequest;
import vn.edu.nlu.web.chat.dto.contact.request.ContactUnRequest;

public interface ContactService {
    ContactAddResponse addContact(ContactAddRequest request);

    void unContact( ContactUnRequest request);
    PageResponse<?> list(int pageNo, int pageSize, String sortBy);
    PageResponse<?> search(int pageNo, int pageSize, String search, String sortBy);

}
