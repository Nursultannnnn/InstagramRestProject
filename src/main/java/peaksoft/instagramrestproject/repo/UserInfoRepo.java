package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instagramrestproject.entity.UserInfo;

public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {}
