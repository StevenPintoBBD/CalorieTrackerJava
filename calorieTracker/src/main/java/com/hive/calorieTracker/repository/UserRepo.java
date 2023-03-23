package com.hive.calorieTracker.repository;

import com.hive.calorieTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
