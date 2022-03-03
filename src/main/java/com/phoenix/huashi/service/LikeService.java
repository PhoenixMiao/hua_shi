package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.dto.collection.BriefCollection;
import com.phoenix.huashi.entity.Likes;

import java.util.List;
import java.util.Optional;

public interface LikeService {
    void like(Long recruitProjectId, String userChuangNum);

    void cancelLike(Long projectId,String userChuangNum);

    Long getLikeNumber(Long projectId);
}
