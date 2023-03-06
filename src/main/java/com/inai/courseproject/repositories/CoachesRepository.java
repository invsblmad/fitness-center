package com.inai.courseproject.repositories;

import com.inai.courseproject.models.user.CoachesPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachesRepository extends JpaRepository<CoachesPortfolio, Integer> {
}
