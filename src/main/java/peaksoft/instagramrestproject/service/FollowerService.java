package peaksoft.instagramrestproject.service;


import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.follow.UserSearchResponse;
import peaksoft.instagramrestproject.dto.user.UserResponse;

import java.util.List;

public interface FollowerService {
    SimpleResponse followUser(Long targetUserId);
    List<UserResponse> getAllSubscribers(Long userId);
    List<UserResponse> getAllSubscriptions(Long userId);
    List<UserSearchResponse> searchUsers(String query);

}

