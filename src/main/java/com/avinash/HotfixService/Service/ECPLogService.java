/**
 * This is a Service class. Methods from this class are mainly called by REST Controller.
 *
 * @author Avinash Tingre
 */
package com.avinash.HotfixService.Service;

import com.avinash.HotfixService.Model.ECPLog;
import com.avinash.HotfixService.Repository.ECPLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Component
public class ECPLogService {
    private static final Logger LOG = LoggerFactory.getLogger(ECPLogService.class);

    @Autowired
    ECPLogRepository ecpRepo;

    @Value("classpath:data/SampleHotfixData.json")
    Resource resource;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Get all results from Database with matching parameters.
     */
    @SuppressWarnings("unchecked")
    public List<ECPLog> searchData(String ecpNo, String description, List<String> cramerVersion,
                                   String latestEcp, String requestor, String fixedBy, List<String> module, String caseOrCrNo,
                                   String filesModifiedInPerforce, String filesReleasedToCustomer, String rolledIntoVersion,
                                   String specificFunc, int page_no, int page_size) {
        ecpNo = formatString(ecpNo);
        description = formatString(description);
        latestEcp = formatString(latestEcp);
        requestor = formatString(requestor);
        fixedBy = formatString(fixedBy);
        caseOrCrNo = formatString(caseOrCrNo);
        filesModifiedInPerforce = formatString(filesModifiedInPerforce);
        filesReleasedToCustomer = formatString(filesReleasedToCustomer);
        rolledIntoVersion = formatString(rolledIntoVersion);
        specificFunc = formatString(specificFunc);

        List<ECPLog> result;

        if (page_no == -1 || page_size == -1) {
            result = ecpRepo.findByOptions(ecpNo, description, cramerVersion,
                    latestEcp, requestor, fixedBy, module, caseOrCrNo, filesModifiedInPerforce,
                    filesReleasedToCustomer, rolledIntoVersion, specificFunc);
        } else {
            result = ecpRepo.findByOptionsWithPaging(ecpNo, description, cramerVersion,
                    latestEcp, requestor, fixedBy, module, caseOrCrNo, filesModifiedInPerforce,
                    filesReleasedToCustomer, rolledIntoVersion, specificFunc,
                    PageRequest.of(page_no, page_size));
        }


        Collections.sort(result);
        return result;
    }

    /***
     * Count all hotfixes in database.
     */
    public long countAllHotfixes() {
        return ecpRepo.count();
    }

    public Boolean minimumValuesProvided(String ecpNo, String description, List<String> cramerVersion,
                                         String latestEcp, String requestor, String fixedBy, List<String> module, String caseOrCrNo,
                                         String filesModifiedInPerforce, String filesReleasedToCustomer, String rolledIntoVersion,
                                         String specificFunc){
        Boolean flag=false;
        if (!ecpNo.isEmpty() || !description.isEmpty() || !latestEcp.isEmpty() || !requestor.isEmpty() || !fixedBy.isEmpty()
                || !caseOrCrNo.isEmpty() || !filesModifiedInPerforce.isEmpty() || !filesReleasedToCustomer.isEmpty()
                || !rolledIntoVersion.isEmpty() || !specificFunc.isEmpty() || !cramerVersion.isEmpty() || !module.isEmpty()) {
             flag = true;
        }
        return  flag;

    }

    /***
     * Count total matching records for given parameters.
     */
    public Long countMatchingRecords(String ecpNo, String description, List<String> cramerVersion,
                                     String latestEcp, String requestor, String fixedBy, List<String> module, String caseOrCrNo,
                                     String filesModifiedInPerforce, String filesReleasedToCustomer, String rolledIntoVersion,
                                     String specificFunc) {
        ecpNo = formatString(ecpNo);
        description = formatString(description);
        latestEcp = formatString(latestEcp);
        requestor = formatString(requestor);
        fixedBy = formatString(fixedBy);
        caseOrCrNo = formatString(caseOrCrNo);
        filesModifiedInPerforce = formatString(filesModifiedInPerforce);
        filesReleasedToCustomer = formatString(filesReleasedToCustomer);
        rolledIntoVersion = formatString(rolledIntoVersion);
        specificFunc = formatString(specificFunc);

        Long result = ecpRepo.findByOptionsGetCount(ecpNo, description, cramerVersion,
                latestEcp, requestor, fixedBy, module, caseOrCrNo, filesModifiedInPerforce,
                filesReleasedToCustomer, rolledIntoVersion, specificFunc);
        return result;
    }

    /***
     * Save hotfixes details in database.
     */
    public ECPLog save(ECPLog ecp_obj) {

        return ecpRepo.save(ecp_obj);
    }

    /***
     * Delete all hotfix records from database.
     */
    public void deleteAll() {
        ecpRepo.deleteAll();
    }

    /***
     * Save batch of hotfixes in database.
     */
    public List<ECPLog> saveAll(Iterable<ECPLog> ecp_list) {
        return ecpRepo.saveAll(ecp_list);
    }

    /**
     * Get all records from Database.
     */
    public List<ECPLog> findAll() {
        List<ECPLog> result = ecpRepo.findAll();
        if (result.isEmpty())
            return new ArrayList<>();
        return result;
    }



    /***
     * Get only those hotfixes which are superseded by given hotfix number.
     */
    public Map<Integer, String> getUnderlyingHF(String latestEcp) {
        List<ECPLog> ecp = ecpRepo.findByLatestEcp(latestEcp);
        Map<Integer, String> result_map = new TreeMap<Integer, String>(Collections.reverseOrder());

        for (ECPLog e : ecp) {
            try {
                result_map.put(Integer.valueOf(e.getSequence()), e.getEcpNo());
            } catch (Exception ex) {
                result_map.put(-1, e.getEcpNo());
            }
        }
        return result_map;
    }

    /***
     * Get distinct product versions among all records.
     */
    public Set<String> getDistinctVersions() {

        Set<String> versions = new HashSet<String>();
        List<ECPLog> all_ecp = this.findAll();
        for (ECPLog ecp : all_ecp) {
            versions.add(ecp.getCramerVersion());
        }

        return versions;
    }

    /***
     * Get distinct product modules among all records.
     */
    public Set<String> getDistinctModules() {
        Set<String> modules = new HashSet<String>();

        List<ECPLog> all_ecp = this.findAll();
        for (ECPLog ecp : all_ecp) {
            modules.add(ecp.getModule());
        }

        return modules;
    }

    /***
     * Utility method for appending/prepending/replacing all whitespaces with ".*"
     */
    public String formatString(String str) {
        StringBuffer sb = new StringBuffer(str.trim());
        sb.insert(0, ".*");
        sb.append(".*");

        return sb.toString().replaceAll("\\s", ".*");
    }


    public void loadSampleData() throws IOException {
        deleteAll();
        LOG.info("Old records deleted");

        File file = resource.getFile();
        String hfRecords = new String(Files.readAllBytes(file.toPath()));

        ECPLog[] arr = objectMapper.readValue(hfRecords, ECPLog[].class);
        List<ECPLog> ecpObjects = Arrays.asList(arr);
        List<ECPLog> result = saveAll(ecpObjects);
        LOG.info("Total records inserted: " + result.size());
    }

}
