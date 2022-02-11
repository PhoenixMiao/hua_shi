package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.dto.collection.BriefCollection;

import java.util.Optional;

public interface LikeService {
    void like(Long recruitProjectId, String userChuangNum);

    void cancelLike(Long id);

    Long getLikeNumber(Long projectId);
}
