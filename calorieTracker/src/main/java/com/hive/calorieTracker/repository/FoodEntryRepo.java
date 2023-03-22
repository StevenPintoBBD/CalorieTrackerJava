package com.hive.calorieTracker.repository;

import com.hive.calorieTracker.model.FoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodEntryRepo extends JpaRepository<FoodEntry, Long> {

}
