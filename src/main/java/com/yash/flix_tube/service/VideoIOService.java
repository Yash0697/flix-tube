package com.yash.flix_tube.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoIOService {
    void produceFileInChunks(MultipartFile file, String videoId) throws IOException;
}
