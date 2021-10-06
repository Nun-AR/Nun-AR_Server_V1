package com.nunar.nunar.repository;

import com.nunar.nunar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u from User u WHERE u.id = :id")
    User findById(String id);
}
