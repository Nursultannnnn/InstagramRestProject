package peaksoft.instagramrestproject.service;

import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.comment.CommentRequest;
import peaksoft.instagramrestproject.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    SimpleResponse saveComment(Long postId, CommentRequest request);
    List<CommentResponse> findAllByPostId(Long postId);
    SimpleResponse deleteComment(Long commentId);
    SimpleResponse updateComment(Long commentId, CommentRequest request);
}