package com.fsse2401.project_man.data.user.entity;

import com.fsse2401.project_man.data.entity.CartItemEntity;
import com.fsse2401.project_man.data.user.domainObject.FirebaseUserData;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    @Column(name = "firebase_uid",nullable = false)
    private String fireBaseUid;
    @Column(nullable = false)
    private String email;


    public UserEntity() {
    }
    public UserEntity(FirebaseUserData firebaseUserData) {
        this.fireBaseUid = firebaseUserData.getFirebaseUid();
        this.email = firebaseUserData.getEmail();
    }


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFireBaseUid() {
        return fireBaseUid;
    }

    public void setFireBaseUid(String fireBaseUid) {
        this.fireBaseUid = fireBaseUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
