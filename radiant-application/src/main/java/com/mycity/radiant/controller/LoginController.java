package com.mycity.radiant.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycity.radaint.domain.payload.request.LoginRequest;
import com.mycity.radaint.domain.payload.response.JwtResponse;
import com.mycity.radiant.security.jwt.JwtUtils;
import com.mycity.radiant.security.services.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/rest/auth")
@Slf4j
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest request) {
		log.info("Trying to authenticate user -> {} ", request.getUsername());
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());

		 return ResponseEntity.ok(JwtResponse.builder()
				 .token(jwt)
				 .username(userDetails.getUsername())
				 .displayName(userDetails.getDisplayName())
				 .roles(roles)
				 .build());
	}
}
