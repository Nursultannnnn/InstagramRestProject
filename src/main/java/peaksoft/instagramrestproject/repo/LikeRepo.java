package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instagramrestproject.entity.Like;

import java.util.Optional;

public interface LikeRepo extends JpaRepository<Like, Long> {
    // Добавили Contains, чтобы Spring искал один ID в списке всех лайкнувших этот пост
    Optional<Like> findByPostIdAndUserIdsContains(Long postId, Long userId);

    // Здесь уже было правильно
    Optional<Like> findByCommentIdAndUserIdsContains(Long commentId, Long userId);
}