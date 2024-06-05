package vn.edu.nlu.web.chat.dto.profile.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileDTO {
    private BasicDetailsDTO basicDetails;
    private MediaDTO media;
    private AttachedFilesDTO attachedFiles;

    public ProfileDTO createMockProfile() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setBasicDetails(createBasicDetails());
        profileDTO.setMedia(createMedia());
        profileDTO.setAttachedFiles(createAttachedFiles());
        return profileDTO;
    }

    private BasicDetailsDTO createBasicDetails() {
        BasicDetailsDTO basicDetailsDTO = new BasicDetailsDTO();
        basicDetailsDTO.setFirstName("John");
        basicDetailsDTO.setLastName("Doe");
        basicDetailsDTO.setTitle("Software Engineer");
        basicDetailsDTO.setDescription("Passionate about building great software.");
        basicDetailsDTO.setFullName("John Doe");
        basicDetailsDTO.setEmail("john.doe@example.com");
        basicDetailsDTO.setLocation("New York, USA");
        basicDetailsDTO.setAvatar("https://example.com/avatar.jpg");
        basicDetailsDTO.setCoverImage("https://example.com/cover.jpg");
        return basicDetailsDTO;
    }

    private MediaDTO createMedia() {
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setTotal(3);

        List<MediaItemDTO> mediaItems = new ArrayList<>();
        mediaItems.add(new MediaItemDTO(1L, "https://example.com/image1.jpg"));
        mediaItems.add(new MediaItemDTO(2L, "https://example.com/image2.jpg"));
        mediaItems.add(new MediaItemDTO(3L, "https://example.com/image3.jpg"));

        mediaDTO.setList(mediaItems);
        return mediaDTO;
    }

    private AttachedFilesDTO createAttachedFiles() {
        AttachedFilesDTO attachedFilesDTO = new AttachedFilesDTO();
        attachedFilesDTO.setTotal(2);

        List<AttachedFileItemDTO> attachedFileItems = new ArrayList<>();
        attachedFileItems.add(new AttachedFileItemDTO(1L, "document1.pdf", "2 MB", "https://example.com/document1.pdf", "pdf"));
        attachedFileItems.add(new AttachedFileItemDTO(2L, "document2.docx", "1.5 MB", "https://example.com/document2.docx", "docx"));

        attachedFilesDTO.setList(attachedFileItems);
        return attachedFilesDTO;
    }


    @Data
    public class BasicDetailsDTO {
        private String firstName;
        private String lastName;
        private String title;
        private String description;
        private String fullName;
        private String email;
        private String location;
        private String avatar;
        private String coverImage;
    }

    @Data
    public class MediaDTO {
        private int total;
        private List<MediaItemDTO> list;

    }

    @AllArgsConstructor
    @Data
    public class MediaItemDTO {
        private long id;
        private String url;

    }

    @Data
    public class AttachedFilesDTO {
        private int total;
        private List<AttachedFileItemDTO> list;

    }

    @Data
    @AllArgsConstructor
    public class AttachedFileItemDTO {
        private long id;
        private String fileName;
        private String size;
        private String downloadUrl;
        private String icon;

    }


}