package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.instagramrestproject.entity.Comment;

import java.util.List;
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
}
