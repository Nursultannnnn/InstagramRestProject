package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.instagramrestproject.entity.UserInfo;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {

    @Query("SELECT ui FROM UserInfo ui WHERE ui.user.id = :userId")
    Optional<UserInfo> findByUserId(Long userId);

}