package com.demo.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.user_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
