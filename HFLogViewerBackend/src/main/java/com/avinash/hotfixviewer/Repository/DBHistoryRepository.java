package com.avinash.hotfixviewer.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.avinash.hotfixviewer.Model.DBHistory;

public interface DBHistoryRepository extends MongoRepository<DBHistory, Long> {
	DBHistory findTopByOrderByDatabaseCreatedAtDesc();
}
