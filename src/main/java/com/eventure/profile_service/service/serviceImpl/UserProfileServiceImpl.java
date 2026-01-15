package com.eventure.profile_service.service.serviceImpl;


import com.eventure.profile_service.DTO.ProfileRequestDTO;
import com.eventure.profile_service.DTO.ProfileResponseDTO;
import com.eventure.profile_service.DTO.UserProfileSummaryDto;
import com.eventure.profile_service.exeption.ProfileNotFoundException;
import com.eventure.profile_service.model.entity.InterestCategory;
import com.eventure.profile_service.model.entity.UserProfile;
import com.eventure.profile_service.repository.InterestCategoryRepository;
import com.eventure.profile_service.repository.UserProfileRepository;
import com.eventure.profile_service.service.FileStorageService;
import com.eventure.profile_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final InterestCategoryRepository interestCategoryRepository;
    private final FileStorageService fileStorageService;

    @Override
    public UserProfileSummaryDto getUserProfileSummary(Long id) {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        String fullName = (user.getFirstName() != null ? user.getFirstName() : "")
                + " " +
                (user.getLastName() != null ? user.getLastName() : "");

        return new UserProfileSummaryDto(
                fullName.trim(),
                user.getPhotoUrl()
        );
    }

    @Override
    public ProfileResponseDTO createOrUpdateProfile(Long userId, String email, ProfileRequestDTO request, MultipartFile file) {

        String photoUrl = null;

        if (file != null && !file.isEmpty()) {
            try {
                photoUrl = fileStorageService.uploadFile(file);
            } catch (Exception e) {
                log.error("Failed to upload file for user {}", userId, e);
                throw new RuntimeException("Image upload failed", e);
            }
        }

        return saveProfileToDb(userId, email, request, photoUrl);
    }

    @Transactional
    protected ProfileResponseDTO saveProfileToDb(Long userId, String email, ProfileRequestDTO request, String newPhotoUrl) {

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElse(UserProfile.builder()
                        .userId(userId)
                        .email(email)
                        .isProfileActive(true)
                        .build());

        if (request.getFirstName() != null) profile.setFirstName(request.getFirstName());
        if (request.getLastName() != null) profile.setLastName(request.getLastName());
        if (request.getGender() != null) profile.setGender(request.getGender());
        if (request.getAge() != null) profile.setAge(request.getAge());
        if (request.getBio() != null) profile.setBio(request.getBio());
        if (request.getLocation() != null) profile.setLocation(request.getLocation());

        profile.setLastProfileEdit(LocalDateTime.now());

        if (request.getInterests() != null) {
            Set<InterestCategory> categories = new HashSet<>();
            for (String interestName : request.getInterests()) {
                interestCategoryRepository.findByName(interestName)
                        .ifPresent(categories::add);
            }
            profile.setInterests(categories);
        }

        if (request.getSocialNetworks() != null) {
            profile.setSocialNetworks(request.getSocialNetworks());
        }

        if (newPhotoUrl != null) {
            profile.setPhotoUrl(newPhotoUrl);
        }

        UserProfile savedProfile = userProfileRepository.save(profile);
        return mapToResponse(savedProfile);
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
                .interests(p.getInterests().stream()
                        .map(InterestCategory::getName)
                        .collect(Collectors.toSet()))
                .socialNetworks(p.getSocialNetworks())
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