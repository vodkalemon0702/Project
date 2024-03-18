package com.fsse2401.project_man.util;

import com.fsse2401.project_man.data.user.domainObject.FirebaseUserData;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtUtil {
    public static FirebaseUserData getFirebaseUserData(JwtAuthenticationToken jwtToken){
        return new FirebaseUserData(jwtToken);
    }
}
