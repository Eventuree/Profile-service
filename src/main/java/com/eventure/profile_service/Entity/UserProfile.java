package com.eventure.profile_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

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

    @Column(name = "is_profile_active")
    private Boolean isProfileActive = true;

    private String location;

    @Column(name = "last_profile_edit")
    private LocalDateTime lastProfileEdit;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}