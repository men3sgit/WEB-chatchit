package vn.edu.nlu.web.chat.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.model.Contact;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.ContactRepository;
import vn.edu.nlu.web.chat.repository.UserRepository;
import vn.edu.nlu.web.chat.repository.custom.ContactRepositoryCustom;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ContactRepositoryCustomImpl implements ContactRepositoryCustom {

    private static final String LIKE_FORMAT = "%%%s%%";

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public PageResponse<?> list(String email, int pageNo, int pageSize, String sortBy) {
        try {
            log.info("Executing list contacts for email={}", email);

            // Tìm kiếm các contact có email1 hoặc email2 bằng email truyền vào
            String contactJpql = "SELECT c FROM Contact c WHERE c.email1 = :email OR c.email2 = :email";
            TypedQuery<Contact> contactQuery = entityManager.createQuery(contactJpql, Contact.class);
            contactQuery.setParameter("email", email);
            List<Contact> contacts = contactQuery.getResultList();

            List<String> contactEmails = new ArrayList<>();
            for (Contact contact : contacts) {
                // Lấy email khác mà không trùng với email truyền vào
                if (contact.getEmail1().equals(email)) {
                    contactEmails.add(contact.getEmail2());
                } else {
                    contactEmails.add(contact.getEmail1());
                }
            }

            // Tìm kiếm thông tin của các user contact dựa trên các email thu được
            String userJpql = "SELECT u FROM User u WHERE u.email IN :emails";
            TypedQuery<User> userQuery = entityManager.createQuery(userJpql, User.class);
            userQuery.setParameter("emails", contactEmails);
            List<User> users = userQuery.getResultList();

            // Phân trang kết quả
            int start = pageNo * pageSize;
            int end = Math.min((start + pageSize), users.size());
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<User> page = new PageImpl<>(users.subList(start, end), pageable, users.size());

            return PageResponse.builder()
                    .page(pageNo)
                    .size(pageSize)
                    .total(page.getTotalPages())
                    .items(users)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while listing contacts: {}", e.getMessage());
            throw new ApiRequestException("Error occurred while listing contacts", e);
        }
    }


    @Override
    public PageResponse<?> search(String email, int pageNo, int pageSize, String search, String sortBy) {
        return null;
    }

    public static void main(String[] args) {
//        ContactRepositoryCustom contactRepositoryCustom;

    }
}
