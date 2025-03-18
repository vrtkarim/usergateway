package com.vrtkarim.usergateway.repositories;

import com.vrtkarim.usergateway.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User save(User user);

}
