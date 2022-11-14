package com.phoenix.huashi.service;

public interface VoteService {
    void updateVote();

    String vote(Long projectId, String userChuangNum);
}
