package peaksoft.instagramrestproject.service;

import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.post.PostRequest;
import peaksoft.instagramrestproject.dto.post.PostResponse;

import java.util.List;

public interface PostService {
    SimpleResponse savePost(PostRequest postRequest);
    SimpleResponse updatePost(Long postId, PostRequest postRequest);
    SimpleResponse deletePost(Long postId);
    PostResponse getPostById(Long postId);
    List<PostResponse> getAllPosts(Long userId);}