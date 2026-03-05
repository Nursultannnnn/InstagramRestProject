package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instagramrestproject.entity.Follower;

import java.util.Optional;

public interface FollowerRepo extends JpaRepository<Follower, Long> {
    Optional<Follower> findByUserId(Long userId);
}
