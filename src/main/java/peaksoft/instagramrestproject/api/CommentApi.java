package peaksoft.instagramrestproject.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.comment.CommentRequest;
import peaksoft.instagramrestproject.dto.comment.CommentResponse;
import peaksoft.instagramrestproject.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentApi {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public SimpleResponse saveComment(@PathVariable Long postId, @RequestBody CommentRequest request) {
        return commentService.saveComment(postId, request);
    }

    @GetMapping("/post/{postId}")
    public List<CommentResponse> findAllByPostId(@PathVariable Long postId) {
        return commentService.findAllByPostId(postId);
    }

    @DeleteMapping("/{commentId}")
    public SimpleResponse deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }

    @PutMapping("/{commentId}")
    public SimpleResponse updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        return commentService.updateComment(commentId, request);
    }
}
