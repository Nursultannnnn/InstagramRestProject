package peaksoft.instagramrestproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.follow.UserSearchResponse;
import peaksoft.instagramrestproject.dto.user.UserResponse;
import peaksoft.instagramrestproject.entity.Follower;
import peaksoft.instagramrestproject.entity.User;
import peaksoft.instagramrestproject.repo.FollowerRepo;
import peaksoft.instagramrestproject.repo.UserRepo;
import peaksoft.instagramrestproject.service.FollowerService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowerServiceImpl implements FollowerService {

    private final FollowerRepo followerRepo;
    private final UserRepo userRepo;

    @Override
    public SimpleResponse followUser(Long targetUserId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.getUserByEmail(email).orElseThrow();

        if (currentUser.getId().equals(targetUserId)) {
            throw new RuntimeException("Нельзя подписаться на самого себя!");
        }

        User targetUser = userRepo.findById(targetUserId).orElseThrow();

        Follower myFollowerInfo = currentUser.getFollower();
        Follower targetFollowerInfo = targetUser.getFollower();

        if (myFollowerInfo.getSubscriptions().contains(targetUserId)) {
            myFollowerInfo.getSubscriptions().remove(targetUserId);
            targetFollowerInfo.getSubscribers().remove(currentUser.getId());
            return new SimpleResponse("Отписка выполнена", HttpStatus.OK);
        } else {
            myFollowerInfo.getSubscriptions().add(targetUserId);
            targetFollowerInfo.getSubscribers().add(currentUser.getId());
            return new SimpleResponse("Подписка выполнена", HttpStatus.OK);
        }
    }

    @Override
    public List<UserResponse> getAllSubscribers(Long userId) {
        Follower follower = followerRepo.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Follower info not found"));

        return userRepo.findAllById(follower.getSubscribers()).stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getUserInfo() != null ? u.getUserInfo().getImage() : null,
                        u.getFollower() != null ? u.getFollower().getSubscribers().size() : 0,
                        u.getFollower() != null ? u.getFollower().getSubscriptions().size() : 0,
                        u.getUserInfo() != null ? u.getUserInfo().getFullName() : null
                ))
                .toList();
    }

    @Override
    public List<UserResponse> getAllSubscriptions(Long userId) {
        Follower follower = followerRepo.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Follower info not found"));

        return userRepo.findAllById(follower.getSubscriptions()).stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getUserInfo() != null ? u.getUserInfo().getImage() : null,
                        u.getFollower() != null ? u.getFollower().getSubscribers().size() : 0,
                        u.getFollower() != null ? u.getFollower().getSubscriptions().size() : 0,
                        u.getUserInfo() != null ? u.getUserInfo().getFullName() : null
                ))
                .toList();
    }

    @Override
    public List<UserSearchResponse> searchUsers(String query) {
        return followerRepo.searchUsers(query);
    }
}