package vn.edu.nlu.web.chat.dto.profile.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ProfileDTO {
    private BasicDetailsDTO basicDetails;
    private MediaDTO media;
    private AttachedFilesDTO attachedFiles;

}
@Getter
@Setter
class BasicDetailsDTO {
    private String firstName;
    private String lastName;
    private String title;
    private String description;
    private String fullName;
    private String email;
    private String location;
    private String avatar;
    private String coverImage;

    // Getters and Setters
}
@Getter
@Setter
class MediaDTO {
    private int total;
    private List<MediaItemDTO> list;

}
@Getter
@Setter
class MediaItemDTO {
    private long id;
    private String url;

}
@Getter
@Setter
class AttachedFilesDTO {
    private int total;
    private List<AttachedFileItemDTO> list;

}
@Getter
@Setter
class AttachedFileItemDTO {
    private long id;
    private String fileName;
    private String size;
    private String downloadUrl;
    private String icon;

}