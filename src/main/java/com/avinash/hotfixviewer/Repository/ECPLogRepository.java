/**
 * MongoRepository interface for interacting with MongoDB.
 * @author Avinash Tingre
 */
package com.avinash.hotfixviewer.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.avinash.hotfixviewer.Model.ECPLog;

public interface ECPLogRepository extends MongoRepository<ECPLog, Long> {
	
	/**
	 * Spring Data method for fetching result from database.
	 * Note: It will fetch records in batches (As per Page_No and Page_Size).
	 * @return List of ECPlog
	 */
	@Query(value = "{$and: [{ 'ecpNo' : { $regex: ?0, $options: 'i' } }, "
			+ "{ 'description' : { $regex: ?1, $options: 'i' } }," + "{ 'cramerVersion' : { $in: ?2 } },"
			+ "{ 'latestEcp' : { $regex: ?3, $options: 'i' } }," + "{ 'requestor' : { $regex: ?4, $options: 'i' } },"
			+ "{ 'fixedBy' : { $regex: ?5, $options: 'i' } }," + "{ 'module' : { $in: ?6 } },"
			+ "{ 'caseOrCrNo' : { $regex: ?7, $options: 'i' } },"
			+ "{ 'filesModifiedInPerforce' : { $regex: ?8, $options: 'i' } },"
			+ "{ 'filesReleasedToCustomer' : { $regex: ?9, $options: 'i' } },"
			+ "{ 'rolledIntoVersion' : { $regex: ?10, $options: 'i' } },"
			+ "{ 'specificFunc' : { $regex: ?11, $options: 'i' } }]}", sort = "{releasedDate : 1}")
	List<ECPLog> findByOptionsWithPaging(String ecpNo, String description, List<String> cramerVersion, String latestEcp,
			String requestor, String fixedBy, List<String> module, String caseOrCrNo, String filesModifiedInPerforce,
			String filesReleasedToCustomer, String rolledIntoVersion, String specificFunc, Pageable pageable);

	/**
	 * Spring Data method for fetching result from database. Note: It will fetch all
	 * records in one time.
	 * 
	 * @return List of ECPlog
	 */
	@Query(value = "{$and: [{ 'ecpNo' : { $regex: ?0, $options: 'i' } }, "
			+ "{ 'description' : { $regex: ?1, $options: 'i' } }," + "{ 'cramerVersion' : { $in: ?2 } },"
			+ "{ 'latestEcp' : { $regex: ?3, $options: 'i' } }," + "{ 'requestor' : { $regex: ?4, $options: 'i' } },"
			+ "{ 'fixedBy' : { $regex: ?5, $options: 'i' } }," + "{ 'module' : { $in: ?6 } },"
			+ "{ 'caseOrCrNo' : { $regex: ?7, $options: 'i' } },"
			+ "{ 'filesModifiedInPerforce' : { $regex: ?8, $options: 'i' } },"
			+ "{ 'filesReleasedToCustomer' : { $regex: ?9, $options: 'i' } },"
			+ "{ 'rolledIntoVersion' : { $regex: ?10, $options: 'i' } },"
			+ "{ 'specificFunc' : { $regex: ?11, $options: 'i' } }]}", sort = "{releasedDate : 1}")
	List<ECPLog> findByOptions(String ecpNo, String description, List<String> cramerVersion, String latestEcp,
			String requestor, String fixedBy, List<String> module, String caseOrCrNo, String filesModifiedInPerforce,
			String filesReleasedToCustomer, String rolledIntoVersion, String specificFunc);
	
	@Query(value="{ 'latestEcp' : ?0 }", sort="{sequence : -1}")
	List<ECPLog> findByLatestEcp(String latestEcp);
}
