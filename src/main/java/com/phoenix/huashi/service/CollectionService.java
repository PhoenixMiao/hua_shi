package com.phoenix.huashi.service;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.dto.collection.BriefCollection;
import com.phoenix.huashi.controller.request.GetCollectionRequest;

public interface CollectionService {
    void addToCollection(Long recruitProjectId, Long userId);
    void cancelCollection(Long id);
    Page<BriefCollection> getBriefCollectionList(GetCollectionRequest getCollectionRequest, Long userId);
}
