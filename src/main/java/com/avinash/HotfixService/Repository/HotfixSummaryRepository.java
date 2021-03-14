package com.avinash.HotfixService.Repository;

import com.avinash.HotfixService.Model.HotfixSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotfixSummaryRepository extends MongoRepository<HotfixSummary, Long> {
    HotfixSummary findTopByOrderByDatabaseCreatedAtDesc();
}
