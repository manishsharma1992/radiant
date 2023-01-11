package com.mycity.radaint.domain.payload.request;

import org.springframework.data.annotation.Id;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
	
	@Id
	private String id;
	
	private String providerUserId;
	
	private String displayName;
	
	private String email;
	
	private String password;
	
	private String matchingPassword;
	
	@Enumerated(EnumType.STRING)
	private SocialProvider socialProvider;
	
	
}
