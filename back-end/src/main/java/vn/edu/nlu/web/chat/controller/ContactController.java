package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.contact.request.ContactAddRequest;
import vn.edu.nlu.web.chat.dto.contact.request.ContactListRequest;
import vn.edu.nlu.web.chat.dto.contact.request.ContactUnRequest;
import vn.edu.nlu.web.chat.dto.user.request.UserCreateRequest;
import vn.edu.nlu.web.chat.service.ContactService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/contacts")
@Tag(name = "Contact Controller", description = "Endpoints for Contact")
public class ContactController {
    private final ContactService contactService;

    @Operation(summary = "Add new Contact", description = "API add a new Contact.")
    @PostMapping
    public ApiResponse<?> add(@RequestBody @Valid ContactAddRequest request) {
        log.info("Request add new Contact: {}", request);
        var res = contactService.addContact(request);
        log.info("Contact with id: {} successfully, wait for acceptance", res.getId());
        return new ApiResponse<>(HttpStatus.CREATED, Translator.toLocale("Contact.add.success"), res);
    }

    @Operation(summary = "cancel Contact", description = "API cancel a Contact.")
    @DeleteMapping
    public ApiResponse<?> unAdd(@RequestBody @Valid ContactUnRequest request) {
        log.info("Request cancel Contact: {}", request);
        contactService.unContact(request);
        log.info("Cancel Contact successfully");
        return new ApiResponse<>(HttpStatus.NO_CONTENT, Translator.toLocale("Cancel.Contact.success"));
    }

    @Operation(summary = "Get list Contact", description = "API Get list  Contact.")
    @GetMapping
    public ApiResponse<?> list(@RequestParam(defaultValue = "0", required = false) int pageNo,
                               @Min(1) @RequestParam(defaultValue = "20", required = false) int pageSize,
                               @RequestParam(required = false) String sortBy) {
        log.info("Request get all of contact");
        var res = contactService.list(pageNo, pageSize, sortBy);
        log.info("Contact successfully retrieved with total: {} ", res.getTotal());
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("contact.list.success"), res);
    }

    @Operation(summary = "search Contact", description = "API search Contact.")
    @GetMapping(path = "/search/")
    public ApiResponse<?> search(@Min(0) @RequestParam(defaultValue = "0", required = false) int pageNo,
                                 @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                 @RequestParam(required = false) String sortBy,
                                 @RequestParam(required = true) String query) {

        log.info("Request search with paging and sorting");
        PageResponse<?> response = contactService.search(pageNo, pageSize, query, sortBy);
        log.info("Contact matching the query '{}' successfully retrieved", query, pageNo, pageSize, sortBy);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("Contact.list.success"), response);
    }
}
