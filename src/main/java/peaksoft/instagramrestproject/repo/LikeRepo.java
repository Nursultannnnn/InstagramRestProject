package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.instagramrestproject.entity.Like;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {


    @Query("SELECT COUNT(l) > 0 FROM Like l JOIN l.userIds uid WHERE l.post.id = :postId AND uid = :userId")
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    @Query("SELECT COUNT(l) > 0 FROM Like l JOIN l.userIds uid WHERE l.comment.id = :commentId AND uid = :userId")
    boolean existsByUserIdAndCommentId(Long userId, Long commentId);

    @Modifying
    @Query("DELETE FROM Like l WHERE l.post.id = :postId AND :userId MEMBER OF l.userIds")
    void deleteByUserIdAndPostId(Long userId, Long postId);

    @Modifying
    @Query("DELETE FROM Like l WHERE l.comment.id = :commentId AND :userId MEMBER OF l.userIds")
    void deleteByUserIdAndCommentId(Long userId, Long commentId);
}