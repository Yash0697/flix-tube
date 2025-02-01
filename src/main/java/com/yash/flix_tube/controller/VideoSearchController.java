package com.yash.flix_tube.controller;

import com.yash.flix_tube.model.VideoMetadata;
import com.yash.flix_tube.service.VideoSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class VideoSearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoSearchController.class);
    @Autowired
    private VideoSearchService videoSearchService;

    @GetMapping("/search/prefix")
    public ResponseEntity<List<VideoMetadata>> prefixSearch(
            @RequestParam String field, @RequestParam String prefix) {
        try {
            List<VideoMetadata> videoMetadata = videoSearchService.prefixSearch(field, prefix);
            return ResponseEntity.ok(videoMetadata);
        } catch (IOException e) {
            LOGGER.error("Error while searching for prefix", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}