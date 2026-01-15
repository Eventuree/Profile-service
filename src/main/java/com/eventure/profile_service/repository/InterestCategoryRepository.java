package com.eventure.profile_service.repository;

import com.eventure.profile_service.model.entity.InterestCategory;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestCategoryRepository extends JpaRepository<InterestCategory, Long> {
    Optional<InterestCategory> findByName(String name);

    Set<InterestCategory> findByNameIn(Collection<String> names);
}
