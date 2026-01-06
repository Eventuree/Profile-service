package com.eventure.profile_service.Controller;

import com.eventure.profile_service.DTO.UserProfileSummaryDto;
import com.eventure.profile_service.Service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/{id}/summary")
    public ResponseEntity<UserProfileSummaryDto> getUserProfileSummary(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserProfileSummary(id));
    }
}