package com.yash.flix_tube.service.impl;

import com.yash.flix_tube.mapper.VideoMapper;
import com.yash.flix_tube.model.entity.Video;
import com.yash.flix_tube.model.request.UploadVideoRequest;
import com.yash.flix_tube.model.response.UploadVideoResponse;
import com.yash.flix_tube.repository.VideoRepository;
import com.yash.flix_tube.service.VideoIOService;
import com.yash.flix_tube.service.VideoIndexingService;
import com.yash.flix_tube.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class VideoServiceImpl implements VideoService {
    private final static Logger LOGGER = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Autowired
    private VideoIndexingService videoIndexingService;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoIOService videoIOService;
    @Override
    public UploadVideoResponse uploadVideo(UploadVideoRequest request, MultipartFile file) {

        Video video = videoMapper.mapRequestToEntity(request);
        // TODO: upload video to s3 and get the path and set in video
        try {
            Video videoSaved = videoRepository.save(video);
            // Producer will produce file in chunks to kafka, consumer will consume the chunks
            // then assemble and upload them in S3, generate the videoSource, update it in mysql
            // and notify the channel
            videoIOService.produceFileInChunks(file, String.valueOf(videoSaved.getId()));
            videoIndexingService.indexVideoMetadata(request, videoSaved.getId());
            UploadVideoResponse uploadVideoResponse = new UploadVideoResponse();
            uploadVideoResponse.setVideoTitle(videoSaved.getTitle());
            return uploadVideoResponse;
        } catch (IOException e) {
            LOGGER.error("Error while uploading video ", e);
            throw new RuntimeException("Error While Uploading Video " + e.getMessage() );
        }
    }
}
