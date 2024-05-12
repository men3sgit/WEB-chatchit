package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.config.cloud.storage.FileStorageProvider;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.file.request.FileUpdateRequest;
import vn.edu.nlu.web.chat.dto.file.request.FileUploadRequest;
import vn.edu.nlu.web.chat.dto.file.response.FileDetailsResponse;
import vn.edu.nlu.web.chat.dto.file.response.FileDownloadResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.model.File;
import vn.edu.nlu.web.chat.repository.FileRepository;
import vn.edu.nlu.web.chat.service.FileService;
import vn.edu.nlu.web.chat.utils.FileUtils;

import java.io.FileNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    @Value("${app.baseURL}")
    private String baseURL;
    private final FileRepository fileRepository;
    private final FileStorageProvider fileStorageProvider;


    @Override
    public void upload(FileUploadRequest request) {
        var fileData = request.getFile();
        if (fileData == null || fileData.isEmpty()) {
            log.error("File data is null or empty");
            throw new ApiRequestException(Translator.toLocale("error.invalid.payload"));
        }

        try {
            log.info("Uploading file");
            String fileName = fileStorageProvider.uploadFile(fileData);
            log.info("File uploaded successfully");

            var newFile = File.builder()
                    .name(fileName)
                    .path(baseURL + "/api/v1/files/")
                    .size(fileData.getSize())
                    .extension(FileUtils.getFileExtension(fileName))
                    .isDirectory(false)
                    .build();

            fileRepository.save(newFile);
            log.info("File saved into database successfully");
        } catch (Exception e) {
            log.error("Failed to upload file: {}", e.getMessage());
            throw new ApiRequestException(Translator.toLocale("file.upload.fail"));
        }
    }

    @Override
    public FileDownloadResponse download(Long id) {
        var storedFile = getFileById(id);
        log.info("Request to download file from file storage with ID {}: {}", id, storedFile.getName());
        var bytesContent = fileStorageProvider.downloadFile(storedFile.getName());
        if (bytesContent != null) {
            log.info("Download file success for ID {}: {}", id, storedFile.getName());
        } else {
            log.error("Failed to download file with ID {}: {}", id, storedFile.getName());
        }

        return FileDownloadResponse.builder()
                .data(bytesContent)
                .fileName(storedFile.getName())
                .build();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void update(Long id, FileUpdateRequest request) throws FileNotFoundException {

    }

    @Override
    public FileDetailsResponse getDetails(Long id) throws FileNotFoundException {
        return null;
    }

    @Override
    public PageResponse<?> search(Pageable pageable, String fileName) {
        return fileRepository.searchFiles(pageable, fileName);
    }

    private File getFileById(Long id) {
        return fileRepository.findByIdAndEntityStatusIsNot(id, EntityStatus.DELETED)
                .orElseThrow(() -> new ApiRequestException(Translator.toLocale("error.not-exist")));
    }
}
