package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.instagramrestproject.entity.Post;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds ORDER BY p.createdAt DESC")
    List<Post> getFeed(List<Long> userIds);

    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
