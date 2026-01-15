package com.eventure.profile_service.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

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
    private Set<String> interests;
    private Map<String, String> socialNetworks;
}