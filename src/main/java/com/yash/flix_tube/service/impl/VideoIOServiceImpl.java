package com.yash.flix_tube.service.impl;

import com.google.gson.Gson;
import com.yash.flix_tube.model.Notification;
import com.yash.flix_tube.model.VideoChunk;
import com.yash.flix_tube.model.request.UploadVideoRequest;
import com.yash.flix_tube.model.response.UploadVideoResponse;
import com.yash.flix_tube.service.VideoIOService;
import com.yash.flix_tube.service.VideoService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Service
public class VideoIOServiceImpl implements VideoIOService {

    private final Logger LOGGER = LoggerFactory.getLogger(VideoIOServiceImpl.class);

    @Value("${flix-tube.video.upload.path}")
    private String uploadPath;

    @Value("${flix-tube.video.topic}")
    private String topic;
    @Value("${flic-tube.notification.topic}")
    private String notificationTopic;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /*
    *   Produce file in chunks to kafka, consumer will consume the chunks, assemble them
    *  and upload to S3, then it will update video in mysql adding video url/s3_path
    *  and extension then it will send email to channel owner.
    *  If producer fails to produce chunks, it will also send message to kafka
    */
    @Async
    @Override
    public void produceFileInChunks(MultipartFile file, String videoId) {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        try {
            InputStream inputStream = file.getInputStream();
            Gson gson = new Gson();
            Notification notification = new Notification("Uploading your video", "test@gmail.com");
            kafkaTemplate.send(notificationTopic, notification);
            assert fileName != null;
            fileName = StringUtils.cleanPath(fileName);
            Path path = Paths.get(uploadPath, fileName);
            int chunkSize = 1024 * 1024;
            byte[] buffer = new byte[chunkSize];

            int chunkIndex = 0;
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] chunkData = Arrays.copyOf(buffer, bytesRead);
                VideoChunk videoChunk = new VideoChunk(videoId, chunkIndex, fileName, contentType, chunkData, bytesRead);
                kafkaTemplate.send(topic, videoChunk.getFileId(), videoChunk);
                chunkIndex++;
            }
        } catch (Exception e) {
            Notification notification = new Notification("Failed to Upload your video", "test@gmail.com");
            // produce to kafka for email
            kafkaTemplate.send(notificationTopic, notification);
        }
    }
}
