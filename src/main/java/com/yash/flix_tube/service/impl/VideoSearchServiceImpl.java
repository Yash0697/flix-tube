package com.yash.flix_tube.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.flix_tube.model.VideoMetadata;
import com.yash.flix_tube.service.VideoSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VideoSearchServiceImpl implements VideoSearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoSearchServiceImpl.class);

    @Autowired
    @Qualifier("elasticClient")
    private RestHighLevelClient client;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${video.metadata.index.name}")
    private String indexName;
    @Override
    public List<VideoMetadata> prefixSearch(String field, String prefix) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.prefixQuery(field  + ".keyword", prefix));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        LOGGER.info("Search response: {}", Arrays.toString(searchResponse.getHits().getHits()));
        List<VideoMetadata> results = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            VideoMetadata metadata = objectMapper.readValue(hit.getSourceAsString(), VideoMetadata.class);
            // TODO: Get actual data from mysql based on the video id retrieved from elasticsearch
            results.add(metadata);
        }

        return results;
    }
}
