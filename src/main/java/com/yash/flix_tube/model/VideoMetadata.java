package com.yash.flix_tube.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;

@Builder
@Document(indexName = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoMetadata {
    @Id
    private Long id;
    private Long videoId; // from mysql
    private String title;
    private String creator;
    private String description;
    private LocalDate uploadDate;
    private String channelId;
}