package com.enotes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.enotes.entity.UserDtls;

public interface UserRepository extends MongoRepository<UserDtls, String> {
	public UserDtls findByEmail(String email);
}
