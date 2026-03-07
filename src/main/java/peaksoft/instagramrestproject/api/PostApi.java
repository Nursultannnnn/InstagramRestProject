package peaksoft.instagramrestproject.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.post.PostRequest;
import peaksoft.instagramrestproject.dto.post.PostResponse;
import peaksoft.instagramrestproject.service.PostService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApi {

    private final PostService postService;

    @PostMapping
    public SimpleResponse save(@RequestBody PostRequest request) {
        return postService.savePost(request);
    }

    @GetMapping("/{postId}")
    public PostResponse getById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PutMapping("/{postId}")
    public SimpleResponse update(@PathVariable Long postId, @RequestBody PostRequest request) {
        return postService.updatePost(postId, request);
    }

    @DeleteMapping("/{postId}")
    public SimpleResponse delete(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }
}