package com.hive.calorieTracker.repository;

import com.hive.calorieTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long> {
    @Query("SELECT allowance FROM Users where userName = ?")
    List<User> selectUserAllowance(String userName);

    @Query("SELECT bmr FROM Users where userName = ?")
    List<User> selectUserBmr(String userName);
}