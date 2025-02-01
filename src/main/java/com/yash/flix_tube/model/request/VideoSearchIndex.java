package com.yash.flix_tube.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoSearchIndex {
    private String title;
    private String creator;
    private String description;
    private String channel;
    private String uploadDate;
}
