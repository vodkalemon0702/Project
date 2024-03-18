package com.fsse2401.project_man.repository;

import com.fsse2401.project_man.data.entity.CartItemEntity;
import com.fsse2401.project_man.data.entity.ProductEntity;
import com.fsse2401.project_man.data.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository<CartItemEntity,Integer> {
    CartItemEntity findByUserEntityUidAndProductEntityPid(Integer uid,Integer pid);
    List<CartItemEntity> findAllByUserEntity(UserEntity userEntity);
}
