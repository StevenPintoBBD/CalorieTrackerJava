package com.hive.calorieTracker.repository;

import com.hive.calorieTracker.model.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
}
