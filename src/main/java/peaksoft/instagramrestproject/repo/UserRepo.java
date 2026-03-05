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

    // Метод для глобального поиска по username или fullName
    @Query("SELECT u FROM User u LEFT JOIN u.userInfo ui WHERE u.username ILIKE %:word% OR ui.fullName ILIKE %:word%")
    List<User> searchUsers(String word);
}
