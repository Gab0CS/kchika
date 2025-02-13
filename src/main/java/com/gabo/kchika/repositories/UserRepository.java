package com.gabo.kchika.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabo.kchika.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByMail(String mail);
}
