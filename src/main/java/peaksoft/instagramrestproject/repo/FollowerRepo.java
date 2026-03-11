package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.instagramrestproject.dto.follow.UserSearchResponse;
import peaksoft.instagramrestproject.dto.follow.UserShortResponse;
import peaksoft.instagramrestproject.entity.Follower;

import java.util.List;
import java.util.Optional;

public interface FollowerRepo extends JpaRepository<Follower, Long> {
    Optional<Follower> findByUserId(Long userId);

    // Указываем правильный путь к DTO: peaksoft.instagramrestproject.dto.follow...
    @Query("SELECT new peaksoft.instagramrestproject.dto.follow.UserSearchResponse(u.id, u.username, ui.image, ui.fullName) " +
            "FROM User u JOIN u.userInfo ui " +
            "WHERE u.username ILIKE %:query% OR ui.fullName ILIKE %:query%")
    List<UserSearchResponse> searchUsers(String query);

    @Query("SELECT new peaksoft.instagramrestproject.dto.follow.UserShortResponse(u.id, u.username, ui.image) " +
            "FROM User u JOIN u.userInfo ui " +
            "WHERE u.id IN (SELECT sub FROM Follower f JOIN f.subscribers sub WHERE f.user.id = :userId)")
    List<UserShortResponse> findSubscribersByUserId(Long userId);

    @Query("SELECT new peaksoft.instagramrestproject.dto.follow.UserShortResponse(u.id, u.username, ui.image) " +
            "FROM User u JOIN u.userInfo ui " +
            "WHERE u.id IN (SELECT sub FROM Follower f JOIN f.subscriptions sub WHERE f.user.id = :userId)")
    List<UserShortResponse> findSubscriptionsByUserId(Long userId);
}