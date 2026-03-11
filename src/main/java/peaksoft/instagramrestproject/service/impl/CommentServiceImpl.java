package peaksoft.instagramrestproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.comment.CommentRequest;
import peaksoft.instagramrestproject.dto.comment.CommentResponse;
import peaksoft.instagramrestproject.entity.Comment;
import peaksoft.instagramrestproject.entity.Post;
import peaksoft.instagramrestproject.entity.User;
import peaksoft.instagramrestproject.repo.CommentRepo;
import peaksoft.instagramrestproject.repo.PostRepo;
import peaksoft.instagramrestproject.repo.UserRepo;
import peaksoft.instagramrestproject.service.CommentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    @Override
    public SimpleResponse saveComment(Long postId, CommentRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getUserByEmail(email).orElseThrow();
        Post post = postRepo.findById(postId).orElseThrow(() -> new NoSuchElementException("Пост не найден"));

        Comment comment = new Comment();
        comment.setComment(request.comment());
        comment.setCreatedAt(LocalDate.from(LocalDateTime.now()));
        comment.setUser(user);
        comment.setPost(post);

        commentRepo.save(comment);

        return SimpleResponse.builder()
                .message("Комментарий успешно добавлен")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public List<CommentResponse> findAllByPostId(Long postId) {
        List<Comment> comments = commentRepo.findAllByPostId(postId);

        return comments.stream()
                .map(c -> new CommentResponse(
                        c.getId(),
                        c.getComment(),
                        c.getCreatedAt().atStartOfDay(),
                        c.getUser().getUsername(),
                        c.getLikes() != null ? c.getLikes().size() : 0
                ))
                .sorted((c1, c2) -> c2.createdAt().compareTo(c1.createdAt()))
                .toList();
    }

    @Override
    public SimpleResponse deleteComment(Long commentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Комментарий не найден"));

        if (!comment.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Вы не можете удалить чужой комментарий!");
        }

        commentRepo.delete(comment);

        return SimpleResponse.builder()
                .message("Комментарий удален")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse updateComment(Long commentId, CommentRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Комментарий не найден"));

        if (!comment.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Вы не можете редактировать чужой комментарий!");
        }

        comment.setComment(request.comment());

        commentRepo.save(comment);

        return SimpleResponse.builder()
                .message("Комментарий успешно обновлен")
                .status(HttpStatus.OK)
                .build();
    }
}
