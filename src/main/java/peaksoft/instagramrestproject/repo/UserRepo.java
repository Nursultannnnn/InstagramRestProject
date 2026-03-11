package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.instagramrestproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User>  getUserByEmail(String email);


    @Query("SELECT u FROM User u JOIN u.userInfo ui WHERE u.username ILIKE %:keyword% OR ui.fullName ILIKE %:keyword%")
    List<User> searchUsers(String keyword);
}
