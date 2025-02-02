package com.yash.flix_tube.controller;

import com.yash.flix_tube.model.request.UploadVideoRequest;
import com.yash.flix_tube.model.response.UploadVideoResponse;
import com.yash.flix_tube.service.VideoIndexingService;
import com.yash.flix_tube.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/videos")
public class VideoUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoSearchController.class);

    @Autowired
    private VideoService videoService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadVideoResponse> uploadVideo(
            @RequestPart("metadata") UploadVideoRequest request,
          @RequestPart("file") MultipartFile file) {
        UploadVideoResponse uploadVideoResponse = null;
        try {
            uploadVideoResponse = videoService.uploadVideo(request, file);
        } catch (IOException e) {
            LOGGER.error("Error while uploading video ", e);
            throw new RuntimeException(e);
        }
//
//            response.put("fileName", fileName);
        return new ResponseEntity<>(uploadVideoResponse, HttpStatus.OK);
    }
}
