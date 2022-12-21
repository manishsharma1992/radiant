package com.mycity.radaint.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("role")
public class Role {
	
	@Id
	private String id;
	
	@Enumerated(EnumType.STRING)
	private ERole name;
}
