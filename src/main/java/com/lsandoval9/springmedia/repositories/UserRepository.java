package com.lsandoval9.springmedia.repositories;

import com.lsandoval9.springmedia.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);

    Optional<UserEntity> findUserEntityByEmail(String email);

}
