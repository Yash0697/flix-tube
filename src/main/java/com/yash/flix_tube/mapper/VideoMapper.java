package com.yash.flix_tube.mapper;

import com.yash.flix_tube.model.entity.Channel;
import com.yash.flix_tube.model.entity.Video;
import com.yash.flix_tube.model.request.UploadVideoRequest;
import com.yash.flix_tube.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class VideoMapper {

    @Autowired
    private ChannelRepository channelRepository;

    public Video mapRequestToEntity(UploadVideoRequest request) {
        Video video = new Video();
        video.setDescription(request.getDescription());
        video.setTitle(request.getTitle());
        Optional<Channel> channelOp = channelRepository.findById(request.getChannelId());
        video.setChannel(channelOp.orElse(null)); // channel must already exist when a video is being uploaded
        video.setUploadDate(LocalDate.now());
        // TODO: extract extension from multipart file that will be uploaded
        return video;
    }

    private Channel getDefaultChannel() {
        return new Channel(1L, "WWE");
    }
}
