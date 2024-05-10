package vn.edu.nlu.web.chat.dto.file.response;

import lombok.Getter;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;


@Getter
public class FileDetailsResponse extends EntityResponse {
    private String name;

    private String path;

    private long size;

    private String extension;

    private boolean isDirectory;

}
