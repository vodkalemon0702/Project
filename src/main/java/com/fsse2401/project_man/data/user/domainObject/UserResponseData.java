package com.fsse2401.project_man.data.user.domainObject;

import com.fsse2401.project_man.data.user.entity.UserEntity;

public class UserResponseData {
    private Integer uid;
    private String firebaseUid;
    private String email;

    public UserResponseData(UserEntity entity) {
        this.uid = entity.getUid();
        this.firebaseUid = entity.getFireBaseUid();
        this.email = entity.getFireBaseUid();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
