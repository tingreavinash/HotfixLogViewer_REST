package com.avinash.hotfixviewer.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avinash.hotfixviewer.Model.UserDetails;

public interface UserDetailsRepository extends MongoRepository<UserDetails, Long> {
	
	@Query(value="{ 'clientHost' : { $regex: ?0, $options: 'i' } }", sort="{date : -1}")
	List<UserDetails> findByClientHost(String hostname);
}
