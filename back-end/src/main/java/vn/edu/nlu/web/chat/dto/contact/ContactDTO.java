package vn.edu.nlu.web.chat.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Data
public class ContactDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String profileImage;
    private String about;
    private String email;
    private String location;
    private String status;
    private List<ChannelDTO> channels;
    private MediaDTO media;
    private AttachedFilesDTO attachedFiles;

    public ContactDTO() {

    }

    public ContactDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        // Các trường còn lại sẽ được đặt giá trị mặc định
        this.profileImage = "avatar1_url";
        this.about = "If several languages coalesce, the grammar of the resulting.";
        this.location = "California, USA";
        this.status = "ACTIVE";
        this.channels = List.of(
                new ChannelDTO("61665bcb9a456823e282afa7", "Landing Design")
        );
        this.media = new MediaDTO(
                17,
                Arrays.asList(
                        new MediaItemDTO(1, "img1_url"),
                        new MediaItemDTO(2, "img2_url")
                )
        );
        this.attachedFiles = new AttachedFilesDTO(
                4,
                Arrays.asList(
                        new AttachedFileDTO(1, "design-phase-1-approved.pdf", "12.5 MB", "#", "bx bx-file"),
                        new AttachedFileDTO(4, "Landing-A.zip", "6.7 MB", "#", "bx bx-file")
                )
        )
        ;
    }


    // Constructors
}

