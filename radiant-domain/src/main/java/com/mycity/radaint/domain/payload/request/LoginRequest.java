package com.mycity.radaint.domain.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
	String username;
	String password;
}
