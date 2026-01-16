package com.eventure.profile_service.DTO;

import java.util.Map;
import java.util.Set;
import lombok.Data;

@Data
public class ProfileRequestDTO {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private Short age;
    private String bio;
    private String location;
    private Set<Long> favoriteCategoryIds;
    private Map<String, String> socialNetworks;
}
