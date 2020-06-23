package com.avinash.hotfixviewer.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.avinash.hotfixviewer.Model.UserDetails;

public interface UserDetailsRepository extends MongoRepository<UserDetails, Long> {

}
