package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findById(Long userId);
	public User findUserByEmail(String email);

	public User findUserByName(String name);
	
	public User findUserByIdAndPassword(Long userId, String password);
}
