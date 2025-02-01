package com.yash.flix_tube.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoChunk implements Serializable {
    private String fileId;
    private int chunkIndex;
    private String fileName;
    private String contentType;
    private byte[] chunkData;
    private int chunkSize;
}
