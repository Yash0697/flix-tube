package com.yash.flix_tube.service;

import com.yash.flix_tube.model.VideoMetadata;

import java.io.IOException;
import java.util.List;

public interface VideoSearchService {
    List<VideoMetadata> prefixSearch(String field, String prefix) throws IOException;
}
