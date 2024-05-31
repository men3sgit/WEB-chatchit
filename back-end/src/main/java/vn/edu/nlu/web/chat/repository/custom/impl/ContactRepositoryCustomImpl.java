package vn.edu.nlu.web.chat.repository.custom.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequiredArgsConstructor
@Service
public class ContactRepositoryCustomImpl implements ContactRepositoryCustom {
    private final EntityManager entityManager;
    private static final String LIKE_FORMAT = "%%%s%%";

    private ContactRepository contactRepository;
    private UserRepository userRepository;

    @Override
    public PageResponse<User> list(String email, int pageNo, int pageSize, String sortBy) {
        try {
            log.info("Executing list contacts for email={}", email);

            // Tìm kiếm các contact có email1 hoặc email2 bằng email truyền vào
            List<Contact> contacts = contactRepository.findByEmail1OrEmail2(email);

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
            List<User> users = userRepository.findAllByEmailIn(contactEmails);

            // Phân trang kết quả
            int start = pageNo * pageSize;
            int end = Math.min((start + pageSize), users.size());
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<User> page = new PageImpl<>(users.subList(start, end), pageable, users.size());

            return PageResponse.<User>builder()
                    .page(pageNo)
                    .size(pageSize)
                    .total(page.getTotalPages())
                    .items((User) page.getContent())
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
//        PageResponse<User> pageUser= contactRepositoryCustom.list()
    }
}
