package com.mycity.radiant.security.services;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.mycity.radaint.domain.User;
import com.mycity.radaint.domain.payload.request.RegistrationRequest;
import com.mycity.radiant.security.domain.LocalUser;

public interface UserService extends UserDetailsService {
	
	User registerNewUser(RegistrationRequest registrationRequest) throws Exception;
	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) throws Exception;
	
}
