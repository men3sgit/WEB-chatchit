package vn.edu.nlu.web.chat.dto.details;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttachedFileDTO {
    private String fileName;
    private String fileType;
    private Long fileSize;

}
