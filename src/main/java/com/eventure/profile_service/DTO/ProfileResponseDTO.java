package com.eventure.profile_service.DTO;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponseDTO {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private String gender;
    private Short age;
    private String bio;
    private String location;
    private Set<Long> favoriteCategoryIds;
    @JsonProperty("social_media_links")
    private List<String> socialMediaLinks;
}
