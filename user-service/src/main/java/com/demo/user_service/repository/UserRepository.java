package com.demo.user_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.user_service.data.dto.UserDto;
import com.demo.user_service.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT new com.demo.user_service.data.dto.UserDto(u.email, u.name, u.userId, u.encryptedPwd) "
		+ "FROM User u")
	List<UserDto> findAllUserDto();

	@Query("SELECT new com.demo.user_service.data.dto.UserDto(u.email, u.name, u.userId, u.encryptedPwd) "
		+ "FROM User u "
		+ "WHERE u.userId = :userId")
	Optional<UserDto> findUserDtoByUserId(@Param("userId") String userId);

	Optional<User> findByEmail(String email);
}
