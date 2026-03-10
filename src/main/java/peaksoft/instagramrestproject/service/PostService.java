package peaksoft.instagramrestproject.service;

import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.post.PostRequest;
import peaksoft.instagramrestproject.dto.post.PostResponse;

import java.util.List;

public interface PostService {
    SimpleResponse savePost(PostRequest postRequest);
    SimpleResponse updatePost(Long postId, PostRequest postRequest); // Обновить
    SimpleResponse deletePost(Long postId); // Удалить
    PostResponse getPostById(Long postId); // Найти один пост
    List<PostResponse> getAllPosts(); // Добавь эту строку
}