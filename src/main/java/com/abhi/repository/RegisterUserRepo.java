package com.abhi.repository;

import com.abhi.model.RegisterUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterUserRepo extends JpaRepository<RegisterUserEntity, Integer> {
    Optional<RegisterUserEntity> findRegisterUserEntityByUserName(String userName);
}
