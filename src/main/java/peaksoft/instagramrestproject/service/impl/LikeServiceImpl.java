package peaksoft.instagramrestproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.entity.Comment;
import peaksoft.instagramrestproject.entity.Like;
import peaksoft.instagramrestproject.entity.Post;
import peaksoft.instagramrestproject.entity.User;
import peaksoft.instagramrestproject.repo.CommentRepo;
import peaksoft.instagramrestproject.repo.LikeRepo;
import peaksoft.instagramrestproject.repo.PostRepo;
import peaksoft.instagramrestproject.repo.UserRepo;
import peaksoft.instagramrestproject.service.LikeService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService {

    private final LikeRepo likeRepo;
    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;

    @Override
    public SimpleResponse toggleLikePost(Long postId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getUserByEmail(email).orElseThrow();
        Post post = postRepo.findById(postId).orElseThrow();

        if (likeRepo.existsByUserIdAndPostId(user.getId(), postId)) {
            likeRepo.deleteByUserIdAndPostId(user.getId(), postId);
            return new SimpleResponse("Лайк убран", HttpStatus.OK);
        } else {
            Like like = Like.builder()
                    .userIds(new ArrayList<>(List.of(user.getId()))) // Используем список ID
                    .post(post)
                    .build();
            likeRepo.save(like);
            return new SimpleResponse("Лайк поставлен", HttpStatus.OK);
        }
    }

    @Override
    public SimpleResponse toggleLikeComment(Long commentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getUserByEmail(email).orElseThrow();
        Comment comment = commentRepo.findById(commentId).orElseThrow();

        if (likeRepo.existsByUserIdAndCommentId(user.getId(), commentId)) {
            likeRepo.deleteByUserIdAndCommentId(user.getId(), commentId);
            return new SimpleResponse("Лайк с комментария убран", HttpStatus.OK);
        } else {
            Like like = Like.builder()
                    .userIds(new ArrayList<>(List.of(user.getId()))) // Используем список ID
                    .comment(comment)
                    .build();
            likeRepo.save(like);
            return new SimpleResponse("Комментарий лайкнут", HttpStatus.OK);
        }
    }
}