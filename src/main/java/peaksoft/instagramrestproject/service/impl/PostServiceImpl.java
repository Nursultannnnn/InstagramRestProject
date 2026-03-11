package peaksoft.instagramrestproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.post.PostRequest;
import peaksoft.instagramrestproject.dto.post.PostResponse;
import peaksoft.instagramrestproject.entity.Image;
import peaksoft.instagramrestproject.entity.Post;
import peaksoft.instagramrestproject.entity.User;
import peaksoft.instagramrestproject.repo.ImageRepo;
import peaksoft.instagramrestproject.repo.PostRepo;
import peaksoft.instagramrestproject.repo.UserRepo;
import peaksoft.instagramrestproject.service.PostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;

    @Override
    public SimpleResponse savePost(PostRequest request) {
        // 1. Находим того, кто сейчас в системе
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getUserByEmail(email).orElseThrow();

        // 2. Создаем пост
        Post post = new Post();
        post.setTitle(request.title());
        post.setDescription(request.description());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        // 3. Создаем картинку (по ТЗ пост без картинки не катит)
        if (request.imageUrl() == null || request.imageUrl().isBlank()) {
            throw new RuntimeException("У поста должна быть картинка!");
        }

        Image image = new Image();
        image.setImageURL(request.imageUrl());
        image.setPost(post);

        // Привязываем картинку к посту
        post.setImages(List.of(image));

        postRepo.save(post);
        return SimpleResponse.builder()
                .message("Пост успешно опубликован")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse updatePost(Long postId, PostRequest request) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Пост не найден"));

        // Проверка: Ты ли автор?
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!post.getUser().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Вы не можете редактировать чужой пост!");
        }

        post.setTitle(request.title());
        post.setDescription(request.description());

        // Обновляем картинку (берем первую)
        if (!post.getImages().isEmpty()) {
            post.getImages().get(0).setImageURL(request.imageUrl());
        }

        postRepo.save(post);
        return SimpleResponse.builder()
                .message("Пост обновлен")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse deletePost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Пост не найден"));

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!post.getUser().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Вы не можете удалить чужой пост!");
        }

        postRepo.delete(post);
        return SimpleResponse.builder()
                .message("Пост удален")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public PostResponse getPostById(Long postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Пост не найден"));

        // В методе getPostById
        return new PostResponse(
                post.getId(),
                post.getImages().isEmpty() ? null : post.getImages().get(0).getImageURL(),
                post.getDescription(),
                post.getLikes() != null ? post.getLikes().size() : 0
        );
    }

    @Override
    public List<PostResponse> getAllPosts() {
        // Достаем все посты из репозитория
        return postRepo.findAll().stream()
                // Сортируем по дате (от новых к старым)
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getImages().isEmpty() ? null : post.getImages().get(0).getImageURL(),
                        post.getDescription(),
                        post.getLikes() != null ? post.getLikes().size() : 0 // И здесь!
                ))
                .toList();
    }

}