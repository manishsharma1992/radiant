package com.mycity.radaint.domain.payload.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String token;
	@Builder.Default private String type = "Bearer";
	private String username;
	private String displayName;
	private Set<String> roles;
}
