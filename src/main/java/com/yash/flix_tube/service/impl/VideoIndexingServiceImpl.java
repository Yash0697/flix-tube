package com.yash.flix_tube.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.flix_tube.model.VideoMetadata;
import com.yash.flix_tube.repository.VideoMetadataRepository;
import com.yash.flix_tube.model.request.UploadVideoRequest;
import com.yash.flix_tube.service.VideoIndexingService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class VideoIndexingServiceImpl implements VideoIndexingService {

    @Autowired
    private VideoMetadataRepository repository;

    @Autowired
    @Qualifier("elasticClient")
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${video.metadata.index.name}")
    private String indexName;

    @Override
    public String indexVideoMetadata(UploadVideoRequest request, Long videoId) throws IOException {
        VideoMetadata index = VideoMetadata.builder()
                .title(request.getTitle())
                .videoId(videoId)
                .creator(request.getCreator())
                .description(request.getDescription())
                .uploadDate(LocalDate.now())
                .channelId(String.valueOf(request.getChannelId())).build();
        String stringIndex = objectMapper.writeValueAsString(index);

        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.source(stringIndex, XContentType.JSON);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.getId();
    }
}
