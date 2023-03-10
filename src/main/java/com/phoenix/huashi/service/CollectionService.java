package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.dto.collection.BriefCollection;

public interface CollectionService {
    void addToCollection(Long recruitProjectId, String userChuangNum);

    void cancelCollection(Long projectId,String userChuangNum);

    Page<BriefCollection> getBriefCollectionList(GetListRequest getListRequest, String userChuangNum);

    Long getCollectionNumber(Long projectId);
}
