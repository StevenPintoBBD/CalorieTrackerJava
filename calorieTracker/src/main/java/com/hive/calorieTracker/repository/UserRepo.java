package com.hive.calorieTracker.repository;

import com.hive.calorieTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long> {
}
