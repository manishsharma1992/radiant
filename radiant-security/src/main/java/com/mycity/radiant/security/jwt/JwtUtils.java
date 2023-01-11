package com.mycity.radiant.security.jwt;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.mycity.radiant.security.services.impl.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration.ms}")
	private String jwtExpirationMs;
	
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
				.setExpiration(DateUtils.addMilliseconds(new Date(), Integer.parseInt(jwtExpirationMs)))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String authtoken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authtoken);
			return true;
		} 
		catch (SignatureException e) {
			log.error("Invalid JWT Signature: {}", e.getMessage());
		}
		catch (MalformedJwtException e) {
			log.error("Invalid JWT Token: {}", e.getMessage());
		}
		catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {} ", e.getMessage());
		}
		catch(UnsupportedJwtException e) {
			log.error("JWT token is not suppported {}", e.getMessage());
		}
		catch(IllegalArgumentException e) {
			log.error("JWT claims string is empty: {} ", e.getMessage());
		}
		
		return false;
	}

}
