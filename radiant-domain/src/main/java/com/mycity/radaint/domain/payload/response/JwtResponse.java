package com.mycity.radaint.domain.payload.response;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
	private String token;
	@Builder.Default private String type = "Bearer";
	private String username;
	private Set<String> roles;
}
