package com.mycity.radaint.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("users")
public class User {
	
	@Id
	private String id;
	
	private String providerUserId;
	
	private String provider;
	
	private String username;
	
	private String password;
	
	private String displayName;
	
	private boolean enabled;
	
	@Builder.Default
	@Field("roles")
	private Set<Role> roles = new HashSet<>();

}
