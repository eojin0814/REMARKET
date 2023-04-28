package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserId(Long userId);
	User findUserByEmail(String email);
	User findUserByName(String name);
	User findUserByEmailAndPassword(String email, String password);
}
