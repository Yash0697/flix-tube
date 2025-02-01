package com.yash.flix_tube.service;

import com.yash.flix_tube.model.response.UploadVideoResponse;
import com.yash.flix_tube.model.request.UploadVideoRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoService {
    UploadVideoResponse uploadVideo(UploadVideoRequest request, MultipartFile file) throws IOException;
}
