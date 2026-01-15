package com.eventure.profile_service.controller;

import com.eventure.profile_service.DTO.ProfileRequestDTO;
import com.eventure.profile_service.DTO.ProfileResponseDTO;
import com.eventure.profile_service.DTO.UserProfileSummaryDto;
import com.eventure.profile_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
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
    public ProfileResponseDTO updateProfile(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader(value = "X-User-Email", required = false) String email,
            @RequestPart("data") ProfileRequestDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        if (email == null) {
            email = "unknown@no-email.com";
        }
        return userProfileService.createOrUpdateProfile(userId, email, request, file);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(@RequestHeader("X-User-Id") Long userId) {
        userProfileService.deleteProfile(userId);
        return ResponseEntity.noContent().build();
    }
}