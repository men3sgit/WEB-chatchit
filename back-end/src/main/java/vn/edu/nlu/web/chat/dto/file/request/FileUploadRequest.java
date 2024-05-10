package vn.edu.nlu.web.chat.dto.file.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileUploadRequest {
    private MultipartFile file;
}
