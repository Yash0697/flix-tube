package com.yash.flix_tube.service;

import com.yash.flix_tube.model.request.UploadVideoRequest;

import java.io.IOException;

public interface VideoIndexingService {
    String indexVideoMetadata(UploadVideoRequest request, Long videoId) throws IOException;
}
