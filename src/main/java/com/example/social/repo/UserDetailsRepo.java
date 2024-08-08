package com.example.social.repo;

import com.example.social.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
    User findByEmail(String email);
}
