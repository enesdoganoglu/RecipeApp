package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.User;
import com.bilgeadam.repository.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findOptionalByUsername(String username);

    Optional<User> findByRole(ERole roles);

    Optional<User> findOptionalByEmail(String email);
}