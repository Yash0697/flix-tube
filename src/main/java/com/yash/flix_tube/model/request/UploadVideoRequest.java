package com.yash.flix_tube.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UploadVideoRequest {
    private String title;
    private String creator;
    private String description;
    private Long channelId;
//    private MultipartFile file;
}
