package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.instagramrestproject.entity.Post;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    // Лента: посты самого юзера и его подписок, отсортированные по времени
    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds ORDER BY p.createdAt DESC")
    List<Post> getFeed(List<Long> userIds);

    // Посты конкретного юзера для его профиля
    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
