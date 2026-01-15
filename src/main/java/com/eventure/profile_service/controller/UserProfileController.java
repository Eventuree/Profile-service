package com.eventure.profile_service.controller;

import com.eventure.profile_service.DTO.ProfileRequestDTO;
import com.eventure.profile_service.DTO.ProfileResponseDTO;
import com.eventure.profile_service.DTO.UserProfileSummaryDto;
import com.eventure.profile_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/{id}/summary")
    public ResponseEntity<UserProfileSummaryDto> getUserProfileSummary(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserProfileSummary(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileResponseDTO> createProfile(
            @RequestPart("data") ProfileRequestDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        if (request.getUserId() == null || request.getEmail() == null) {
            throw new IllegalArgumentException("UserId and Email are required for creation");
        }

        ProfileResponseDTO response = userProfileService.createProfile(request, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            @PathVariable Long userId,
            @RequestPart("data") ProfileRequestDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        ProfileResponseDTO response = userProfileService.updateProfile(userId, request, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.getProfileByUserId(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long userId) {
        userProfileService.deleteProfile(userId);
        return ResponseEntity.noContent().build();
    }
}
