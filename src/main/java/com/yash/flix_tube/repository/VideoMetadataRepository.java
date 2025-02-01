package com.yash.flix_tube.repository;

import com.yash.flix_tube.model.VideoMetadata;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoMetadataRepository extends ElasticsearchRepository<VideoMetadata, String> {
}
