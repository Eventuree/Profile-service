package com.eventure.profile_service.DTO;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class ProfileRequestDTO {
    private String firstName;
    private String lastName;
    private String gender;
    private Short age;
    private String bio;
    private String location;
    private Set<String> interests;
    private Map<String, String> socialNetworks;
}