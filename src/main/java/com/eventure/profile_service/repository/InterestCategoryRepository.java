package com.eventure.profile_service.repository;


import com.eventure.profile_service.model.entity.InterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestCategoryRepository extends JpaRepository<InterestCategory, Long> {
    Optional<InterestCategory> findByName(String name);
}