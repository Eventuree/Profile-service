package com.eventure.profile_service.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public String getFullName() {
        String first = firstName != null ? firstName : "";
        String last = lastName != null ? lastName : "";
        return (first + " " + last).trim();
    }

    private String email;

    @Column(name = "photo_url")
    private String photoUrl;

    private String gender;
    private Short age;
    private String bio;

    @Builder.Default
    @Column(name = "is_profile_active")
    private Boolean isProfileActive = true;

    private String location;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "profile_favorite_categories",
            joinColumns = @JoinColumn(name = "profile_id")
    )
    @Column(name = "category_id")
    private Set<Long> favoriteCategoryIds = new HashSet<>();

    @Column(name = "last_profile_edit")
    private LocalDateTime lastProfileEdit;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profile_socials", joinColumns = @JoinColumn(name = "profile_id"))
    @MapKeyColumn(name = "network_name")
    @Column(name = "url")
    private Map<String, String> socialNetworks;
}
