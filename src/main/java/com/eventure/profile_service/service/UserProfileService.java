package com.eventure.profile_service.service;

import com.eventure.profile_service.DTO.ProfileRequestDTO;
import com.eventure.profile_service.DTO.ProfileResponseDTO;
import com.eventure.profile_service.DTO.UserProfileSummaryDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {
    UserProfileSummaryDto getUserProfileSummary(Long id);

    ProfileResponseDTO createProfile(
            Long userId, String email, ProfileRequestDTO request, MultipartFile file);

    ProfileResponseDTO updateProfile(Long userId, ProfileRequestDTO request, MultipartFile file);

    ProfileResponseDTO getProfileByUserId(Long userId);

    void deleteProfile(Long userId);
}
