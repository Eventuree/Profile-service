package com.eventure.profile_service.Service.ServiceImpl;


import com.eventure.profile_service.DTO.UserProfileSummaryDto;
import com.eventure.profile_service.Entity.UserProfile;
import com.eventure.profile_service.Repository.UserProfileRepository;
import com.eventure.profile_service.Service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfileSummaryDto getUserProfileSummary(Long id) {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        String fullName = (user.getFirstName() != null ? user.getFirstName() : "")
                + " " +
                (user.getLastName() != null ? user.getLastName() : "");

        return new UserProfileSummaryDto(
                fullName.trim(),
                user.getUsername(),
                user.getPhotoUrl()
        );
    }
}