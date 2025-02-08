package com.gabo.kchika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabo.kchika.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
