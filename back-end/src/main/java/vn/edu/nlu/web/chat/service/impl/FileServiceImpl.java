package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.file.request.FileUpdateRequest;
import vn.edu.nlu.web.chat.dto.file.request.FileUploadRequest;
import vn.edu.nlu.web.chat.dto.file.response.FileDetailsResponse;
import vn.edu.nlu.web.chat.repository.FileRepository;
import vn.edu.nlu.web.chat.service.FileService;

import java.io.FileNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    @Override
    public void upload(FileUploadRequest request) {

    }

    @Override
    public byte[] download(Long id) {
        return null;
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
        return fileRepository.searchFilesWithPageable(pageable,fileName);
    }
}
