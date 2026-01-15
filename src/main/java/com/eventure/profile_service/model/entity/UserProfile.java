package com.eventure.profile_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_interests",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    private Set<InterestCategory> interests = new HashSet<>();

    @Column(name = "last_profile_edit")
    private LocalDateTime lastProfileEdit;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "profile_socials",
            joinColumns = @JoinColumn(name = "profile_id")
    )
    @MapKeyColumn(name = "network_name")
    @Column(name = "url")
    private Map<String, String> socialNetworks;
}