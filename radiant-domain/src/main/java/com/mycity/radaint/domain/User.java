package com.mycity.radaint.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("user")
public class User {
	
	@Id
	private String id;
	
	private String username;
	
	private String password;
	
	@Builder.Default
	private Set<Role> roles = new HashSet<>();

}
