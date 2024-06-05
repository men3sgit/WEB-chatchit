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
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ContactRepositoryCustomImpl implements ContactRepositoryCustom {

    private static final String LIKE_FORMAT = "%%%s%%";

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public boolean  exits(String emailUser,String emailContact) {
        try {
            log.info("Check if contact exists={}", emailContact);
            // Tìm kiếm các contact có email1 hoặc email2 bằng email truyền vào (không phân biệt chữ hoa chữ thường)
            String contactJpql = "SELECT c FROM Contact c WHERE LOWER(c.email1) = LOWER(:email) OR LOWER(c.email2) = LOWER(:email)";
            TypedQuery<Contact> contactQuery = entityManager.createQuery(contactJpql, Contact.class);
            contactQuery.setParameter("email", emailUser.toLowerCase());
            List<Contact> contacts = contactQuery.getResultList();
            System.out.println(contacts.toString());
            List<String> contactEmails = new ArrayList<>();
            for (Contact contact : contacts) {
                // Lấy email khác mà không trùng với email truyền vào
                if (emailUser.equalsIgnoreCase(contact.getEmail1())) {
                    contactEmails.add(contact.getEmail2());
                } else {
                    System.out.println("mến ăn lồn");
                    contactEmails.add(contact.getEmail1());
                }
            }
            System.out.println(contactEmails.toString());
            for (String email : contactEmails) {
                if (email.equalsIgnoreCase(emailContact)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while listing contacts: {}", e.getMessage());
            throw new ApiRequestException("Error occurred while listing contacts", e);
        }
        return false;
    }

    @Override
    public PageResponse<?> list(String email, int pageNo, int pageSize, String sortBy) {
        try {
            log.info("Executing list contacts for email={}", email);

            // Tìm kiếm các contact có email1 hoặc email2 bằng email truyền vào (không phân biệt chữ hoa chữ thường)
            String contactJpql = "SELECT c FROM Contact c WHERE LOWER(c.email1) = LOWER(:email) OR LOWER(c.email2) = LOWER(:email)";
            TypedQuery<Contact> contactQuery = entityManager.createQuery(contactJpql, Contact.class);
            contactQuery.setParameter("email", email.toLowerCase());
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

            String userJpql = "SELECT u FROM User u WHERE LOWER(u.email) IN (:emails)";
            TypedQuery<User> userQuery = entityManager.createQuery(userJpql, User.class);
            List<String> lowerCaseEmails = contactEmails.stream().map(String::toLowerCase).collect(Collectors.toList());
            userQuery.setParameter("emails", lowerCaseEmails);
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
                    .items(page.getContent())
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while listing contacts: {}", e.getMessage());
            throw new ApiRequestException("Error occurred while listing contacts", e);
        }
    }


    @Override
    public PageResponse<?> search(String email, int pageNo, int pageSize, String search, String sortBy) {
        try {
            log.info("Executing list contacts for email={}", email);

            // Tìm kiếm các contact có email1 hoặc email2 bằng email truyền vào (không phân biệt chữ hoa chữ thường)
            String contactJpql = "SELECT c FROM Contact c WHERE LOWER(c.email1) = LOWER(:email) OR LOWER(c.email2) = LOWER(:email)";
            TypedQuery<Contact> contactQuery = entityManager.createQuery(contactJpql, Contact.class);
            contactQuery.setParameter("email", email.toLowerCase());
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
            String userJpql = "SELECT u FROM User u WHERE LOWER(u.email) IN (:emails)";
            TypedQuery<User> userQuery = entityManager.createQuery(userJpql, User.class);
            List<String> lowerCaseEmails = contactEmails.stream().map(String::toLowerCase).collect(Collectors.toList());
            userQuery.setParameter("emails", lowerCaseEmails);
            List<User> users = userQuery.getResultList();

            List<User> filteredUsers = new ArrayList<>();
            for (User user : users) {
                if ((user.getAppName() != null && user.getAppName().contains(search)) ||
                        (user.getEmail() != null && user.getEmail().contains(search)) ||
                        (user.getFirstName() != null && user.getFirstName().contains(search)) ||
                        (user.getLastName() != null && user.getLastName().contains(search))) {
                    filteredUsers.add(user);
                }
            }

            // Phân trang kết quả
            int start = pageNo * pageSize;
            int end = Math.min((start + pageSize), filteredUsers.size());
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<User> page = new PageImpl<>(filteredUsers.subList(start, end), pageable, users.size());

            return PageResponse.builder()
                    .page(pageNo)
                    .size(pageSize)
                    .total(page.getTotalPages())
                    .items(page.getContent())
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while listing contacts: {}", e.getMessage());
            throw new ApiRequestException("Error occurred while listing contacts", e);
        }
    }

    public static void main(String[] args) {
//        ContactRepositoryCustom contactRepositoryCustom;

    }
}
