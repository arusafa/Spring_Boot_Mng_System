package com.aru.repositories;

import com.aru.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Long> {

    User findByEmail(String email);
}
