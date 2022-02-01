package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.controller.response.SearchResponse;

public interface SearchService {
    Page<SearchResponse> search(SearchRequest searchRequest);
}
