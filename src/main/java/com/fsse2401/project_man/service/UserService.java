package com.fsse2401.project_man.service;

import com.fsse2401.project_man.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_man.data.user.entity.UserEntity;

public interface UserService {
    UserEntity getEntityByFireBaseUserData(FirebaseUserData firebaseUserData);
}
