package peaksoft.instagramrestproject.service;

import peaksoft.instagramrestproject.dto.SimpleResponse;

public interface LikeService {

        SimpleResponse toggleLikePost(Long postId);
        SimpleResponse toggleLikeComment(Long commentId);
    }

