package com.eventure.profile_service.DTO;

public record UserProfileSummaryDto(
        String name, // firstName + lastName
        String avatarUrl,
        String email) {}
