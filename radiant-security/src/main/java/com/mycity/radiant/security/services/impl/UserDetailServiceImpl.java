package com.mycity.radiant.security.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import com.mycity.radaint.domain.ERole;
import com.mycity.radaint.domain.Role;
import com.mycity.radaint.domain.User;
import com.mycity.radaint.domain.payload.request.RegistrationRequest;
import com.mycity.radaint.domain.payload.request.SocialProvider;
import com.mycity.radiant.security.domain.LocalUser;
import com.mycity.radiant.security.domain.OAuth2UserInfo;
import com.mycity.radiant.security.domain.OAuth2UserInfoFactory;
import com.mycity.radiant.security.repository.UserRepository;
import com.mycity.radiant.security.services.UserService;
import com.mycity.radiant.security.utils.GeneralUtils;

@Service
public class UserDetailServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		return UserDetailsImpl.build(user);
	}

	@Override
	public User registerNewUser(RegistrationRequest registrationRequest) throws Exception {
		if (registrationRequest.getId() != null && userRepository.existsById(registrationRequest.getId())) {
			throw new Exception("User with User id " + registrationRequest.getId() + " already exist");
		} else if (ObjectUtils.isNotEmpty(userRepository.findByUsername(registrationRequest.getEmail()))) {
			throw new Exception("User with email id " + registrationRequest.getEmail() + " already exist");
		}
		User user = buildUser(registrationRequest);

		return user;
	}

	@Override
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken,
			OidcUserInfo userInfo) throws Exception {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new Exception("Name not found from OAuth2 provider");
		} 
		else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new Exception("Email not found from OAuth2 provider");
		}
		RegistrationRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		User user = userRepository.findByUsername(userDetails.getEmail());
		if(ObjectUtils.isNotEmpty(user)) {
			if(!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new Exception (
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		}
		else {
			user = registerNewUser(userDetails);
		}
		return LocalUser.create(user, attributes, idToken, userInfo);
	}
	
	private RegistrationRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return RegistrationRequest.builder()
			.providerUserId(oAuth2UserInfo.getId())
			.displayName(oAuth2UserInfo.getName())
			.email(oAuth2UserInfo.getEmail())
			.socialProvider(GeneralUtils.toSocialProvider(registrationId))
			.password("changeit")
			.build();
	}
	
	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setDisplayName(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}
	
	private User buildUser(final RegistrationRequest formDTO) {
		
		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(Role.builder()
				.name(ERole.ROLE_USER)
				.build()
		);
		
		return User.builder()
				.displayName(formDTO.getDisplayName())
				.username(formDTO.getDisplayName())
				.password(passwordEncoder.encode(formDTO.getPassword()))
				.roles(roles)
				.provider(formDTO.getSocialProvider().getProviderType())
				.enabled(true)
				.providerUserId(formDTO.getProviderUserId())
			.build();
	}
	
	

}
