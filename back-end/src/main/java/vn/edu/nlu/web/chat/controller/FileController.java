package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.dto.file.request.FileUploadRequest;
import vn.edu.nlu.web.chat.service.FileService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/files")
@Slf4j
@Tag(name = "File Controller", description = "Endpoints for managing files")
public class FileController {
    private final FileService fileService;


    @Operation(summary = "Upload File", description = "API to upload a file.")
    @PostMapping
    public ApiResponse<?> upload(FileUploadRequest request) {
        log.info("Request to upload file: {}", request);
        fileService.upload(request);
        log.info("Upload file success: {}", request.getFile().getName());
        return new ApiResponse<>(HttpStatus.CREATED, Translator.toLocale("file.upload.success"));
    }

    @Operation(summary = "Download File", description = "API to download a file by ID.")
    @GetMapping(path = "/{id}/download")
    public ApiResponse<?> download(@PathVariable Long id) {
        log.info("Request to download file with ID: {}", id);
        var response = fileService.download(id);
        log.info("Download file success for ID: {}", id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", response.getFileName());

        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("file.download.success"), headers, response.getData());
    }


}
