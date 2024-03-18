package com.fsse2401.project_man.repository;

import com.fsse2401.project_man.data.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {
    Optional<UserEntity>findByFireBaseUid(String firebaseUid);
}
