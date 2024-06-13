package vn.edu.nlu.web.chat.dto.contact.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;
import vn.edu.nlu.web.chat.dto.details.AttachedFilesDTO;
import vn.edu.nlu.web.chat.enums.SocialStatus;

@Getter
public class ContactListResponse extends EntityResponse {

    private String firstName;

    private String lastName;

    @JsonProperty(namespace = "profileImage")
    private String avatarUrl;

    @JsonProperty(value = "about")
    private String description;

    private String email;

    private String location;

    private AttachedFilesDTO attachedFiles;

    @JsonProperty(namespace = "status")
    private SocialStatus socialStatus;

//   TODO:  private Boolean isFavourite;
//
//    private Boolean isArchived;
}
