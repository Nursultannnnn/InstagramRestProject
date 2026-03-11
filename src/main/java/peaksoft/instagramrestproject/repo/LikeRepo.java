package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.instagramrestproject.entity.Like;

import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {


    @Query("SELECT l FROM Like l WHERE l.post.id = :postId AND :userId MEMBER OF l.userIds")
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);

    @Query("SELECT l FROM Like l WHERE l.comment.id = :commentId AND :userId MEMBER OF l.userIds")
    Optional<Like> findByCommentIdAndUserId(Long commentId, Long userId);

    @Modifying
    @Query("DELETE FROM Like l WHERE l.post.id = :postId AND :userId MEMBER OF l.userIds")
    void deleteByUserIdAndPostId(Long userId, Long postId);

    @Modifying
    @Query("DELETE FROM Like l WHERE l.comment.id = :commentId AND :userId MEMBER OF l.userIds")
    void deleteByUserIdAndCommentId(Long userId, Long commentId);
}