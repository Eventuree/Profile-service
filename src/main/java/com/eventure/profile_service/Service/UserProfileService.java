package com.eventure.profile_service.Service;


import com.eventure.profile_service.DTO.UserProfileSummaryDto;

public interface UserProfileService {
    UserProfileSummaryDto getUserProfileSummary(Long id);
}