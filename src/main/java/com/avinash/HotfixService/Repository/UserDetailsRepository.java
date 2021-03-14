package com.avinash.HotfixService.Repository;

import com.avinash.HotfixService.Model.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserDetailsRepository extends MongoRepository<UserDetails, Long> {

    @Query(value = "{ 'hostname' : { $regex: ?0, $options: 'i' } }", sort = "{date : -1}")
    List<UserDetails> findByHostname(String host);
}
