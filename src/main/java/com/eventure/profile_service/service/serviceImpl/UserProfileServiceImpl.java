package com.eventure.profile_service.service.serviceImpl;

import com.eventure.profile_service.DTO.ProfileRequestDTO;
import com.eventure.profile_service.DTO.ProfileResponseDTO;
import com.eventure.profile_service.DTO.UserProfileSummaryDto;
import com.eventure.profile_service.exception.ProfileAlreadyExistsException;
import com.eventure.profile_service.exception.ProfileNotFoundException;
import com.eventure.profile_service.model.entity.UserProfile;
import com.eventure.profile_service.repository.UserProfileRepository;
import com.eventure.profile_service.service.FileStorageService;
import com.eventure.profile_service.service.UserProfileService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final FileStorageService fileStorageService;

    @Lazy @Autowired private UserProfileServiceImpl self;

    @Override
    public UserProfileSummaryDto getUserProfileSummary(Long userId) {
        UserProfile user = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        String fullName = user.getFullName() != null ? user.getFullName().trim() : "";
        return new UserProfileSummaryDto(fullName, user.getPhotoUrl(), user.getEmail());
    }

    @Override
    public ProfileResponseDTO createProfile(ProfileRequestDTO request, MultipartFile file) {
        Long userId = request.getUserId();
        String email = request.getEmail();

        if (userProfileRepository.existsByUserId(userId)) {
            throw new ProfileAlreadyExistsException("Profile already exists for user " + userId);
        }

        String photoUrl = uploadFileSafe(file);
        return self.createProfileInDb(userId, email, request, photoUrl);
    }

    @Transactional
    public ProfileResponseDTO createProfileInDb(Long userId, String email, ProfileRequestDTO request, String photoUrl) {
        UserProfile profile = UserProfile.builder()
                .userId(userId)
                .email(email)
                .isProfileActive(true)
                .build();

        updateEntityFromDto(profile, request);

        if (photoUrl != null) profile.setPhotoUrl(photoUrl);
        profile.setLastProfileEdit(LocalDateTime.now());

        return mapToResponse(userProfileRepository.save(profile));
    }

    @Override
    public ProfileResponseDTO updateProfile(Long userId, ProfileRequestDTO request, MultipartFile file) {
        String photoUrl = uploadFileSafe(file);
        return self.updateProfileInDb(userId, request, photoUrl);
    }

    @Transactional
    public ProfileResponseDTO updateProfileInDb(Long userId, ProfileRequestDTO request, String photoUrl) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user id: " + userId));

        updateEntityFromDto(profile, request);

        if (photoUrl != null) profile.setPhotoUrl(photoUrl);
        profile.setLastProfileEdit(LocalDateTime.now());

        return mapToResponse(userProfileRepository.save(profile));
    }

    private String uploadFileSafe(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        try {
            return fileStorageService.uploadImage(file);
        } catch (Exception e) {
            log.error("File upload failed", e);
            throw new RuntimeException("Image upload failed", e);
        }
    }

    private void updateEntityFromDto(UserProfile profile, ProfileRequestDTO request) {
        if (request.getFirstName() != null) profile.setFirstName(request.getFirstName());
        if (request.getLastName() != null) profile.setLastName(request.getLastName());
        if (request.getGender() != null) profile.setGender(request.getGender());
        if (request.getAge() != null) profile.setAge(request.getAge());
        if (request.getBio() != null) profile.setBio(request.getBio());
        if (request.getLocation() != null) profile.setLocation(request.getLocation());
        if (request.getFavoriteCategoryIds() != null) {
            profile.setFavoriteCategoryIds(request.getFavoriteCategoryIds());
        }
        if (request.getSocialMediaLinks() != null) {
            profile.setSocialMediaLinks(request.getSocialMediaLinks());
        }
    }

    @Override
    public ProfileResponseDTO getProfileByUserId(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user id: " + userId));
        return mapToResponse(profile);
    }

    private ProfileResponseDTO mapToResponse(UserProfile p) {
        return ProfileResponseDTO.builder()
                .id(p.getId())
                .userId(p.getUserId())
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .email(p.getEmail())
                .photoUrl(p.getPhotoUrl())
                .gender(p.getGender())
                .age(p.getAge())
                .bio(p.getBio())
                .location(p.getLocation())
                .favoriteCategoryIds(p.getFavoriteCategoryIds())
                .socialMediaLinks(p.getSocialMediaLinks())
                .build();
    }

    @Override
    @Transactional
    public void deleteProfile(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        userProfileRepository.delete(profile);
    }
}