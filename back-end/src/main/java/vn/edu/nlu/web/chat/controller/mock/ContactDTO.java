package vn.edu.nlu.web.chat.controller.mock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class ContactDTO {

    private String id;
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


    // Constructors
}
@Data
@AllArgsConstructor
class ChannelDTO {

    private String id;
    private String name;


}
@Data
@AllArgsConstructor

class MediaDTO {

    private int total;
    private List<MediaItemDTO> list;


}
@AllArgsConstructor

@Data
class MediaItemDTO {

    private int id;
    private String url;


}
@AllArgsConstructor

@Data
class AttachedFilesDTO {

    private int total;
    private List<AttachedFileDTO> list;


}
@AllArgsConstructor

@Data
class AttachedFileDTO {

    private int id;
    private String fileName;
    private String size;
    private String downloadUrl;
    private String icon;


}
