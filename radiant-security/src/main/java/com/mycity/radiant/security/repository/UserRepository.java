package com.mycity.radiant.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mycity.radaint.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByUsername(String username);

}
