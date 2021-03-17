/**
 * This is a RestController class for exposing the REST endpoints.
 *
 * @author Avinash Tingre
 */
package com.avinash.HotfixService.Controller;

import com.avinash.HotfixService.HotfixviewerApplication;
import com.avinash.HotfixService.Model.*;
import com.avinash.HotfixService.Service.DatabaseLogHandler;
import com.avinash.HotfixService.Service.ECPLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/HFLogViewer")
@Tag(name = "Hotfix Search", description = "API for fetching hotfixes")
public class EcpLogController {
    private static final Logger LOG = LoggerFactory.getLogger(EcpLogController.class);

    @Autowired
    private ECPLogService ecpService;

    @Value("${headerPrefix}")
    private String headerPrefix;

    @Autowired
    private DatabaseLogHandler dbHandler;

    /**
     * Fetch details of matching hotfixes.
     */
    @Operation(summary = "Find all hotfixes", description = "Hotfix search with given criteria.", tags = {"Hotfix Search"})
    @RequestMapping(value = "/getAllResults", method = RequestMethod.GET)
    public ResponseEntity<Metadata> getHotfixDetails(
            @RequestParam(value = "page_no", defaultValue = "-1", required = false) int page_no,
            @RequestParam(value = "page_size", defaultValue = "-1", required = false) int page_size,
            @RequestParam(value = "ecpNo", defaultValue = "", required = false) String ecpNo,
            @RequestParam(value = "description", defaultValue = "", required = false) String description,
            @RequestParam(value = "cramerVersion", defaultValue = "", required = false) List<String> cramerVersion,
            @RequestParam(value = "latestEcp", defaultValue = "", required = false) String latestEcp,
            @RequestParam(value = "requestor", defaultValue = "", required = false) String requestor,
            @RequestParam(value = "fixedBy", defaultValue = "", required = false) String fixedBy,
            @RequestParam(value = "module", defaultValue = "", required = false) List<String> module,
            @RequestParam(value = "caseOrCrNo", defaultValue = "", required = false) String caseOrCrNo,
            @RequestParam(value = "filesModifiedInPerforce", defaultValue = "", required = false) String filesModifiedInPerforce,
            @RequestParam(value = "filesReleasedToCustomer", defaultValue = "", required = false) String filesReleasedToCustomer,
            @RequestParam(value = "rolledIntoVersion", defaultValue = "", required = false) String rolledIntoVersion,
            @RequestParam(value = "specificFunc", defaultValue = "", required = false) String specificFunc,
            HttpServletRequest httpRequest, @RequestHeader(value = "Hostname", defaultValue = "disabled", required = false) String hostname,
            @RequestHeader(value = "HostAddress", defaultValue = "disabled", required = false) String HostAddress,
            @RequestHeader(value = "NTNET", defaultValue = "disabled", required = false) String ntnet) {
        Metadata ro = new SearchResultMetadata();
        Boolean minimumValuesProvided = ecpService.minimumValuesProvided(ecpNo, description, cramerVersion, latestEcp, requestor,
                fixedBy, module, caseOrCrNo, filesModifiedInPerforce, filesReleasedToCustomer, rolledIntoVersion,
                specificFunc);

        if(minimumValuesProvided){
            List<String> requestInput = new ArrayList<String>();

            if (ecpNo.length() > 0) requestInput.add("Hotfix No: " + ecpNo + ", ");
            if (latestEcp.length() > 0) requestInput.add("Latest Hotfix: " + latestEcp + ", ");
            if (description.length() > 0) requestInput.add("Description: " + description + ", ");
            if (cramerVersion.size() > 0) requestInput.add("Versions: " + cramerVersion + ", ");
            if (requestor.length() > 0) requestInput.add("Requested by: " + requestor + ", ");
            if (fixedBy.length() > 0) requestInput.add("Fixed by: " + fixedBy + ", ");
            if (module.size() > 0) requestInput.add("Modules: " + module + ", ");
            if (caseOrCrNo.length() > 0) requestInput.add("Case or CR No: " + caseOrCrNo + ", ");
            if (filesModifiedInPerforce.length() > 0) requestInput.add("Files modified: " + filesModifiedInPerforce + ", ");
            if (filesReleasedToCustomer.length() > 0) requestInput.add("Files released: " + filesReleasedToCustomer + ", ");
            if (specificFunc.length() > 0) requestInput.add("Specific function: " + specificFunc + ", ");

            logToDatabase(hostname, HostAddress, ntnet, requestInput, "/getAllResults");

            if (cramerVersion.isEmpty()) {
                cramerVersion = HotfixviewerApplication.distinctVersion;
            }
            if (module.isEmpty()) {
                module = HotfixviewerApplication.distinctModules;
            }


            List<ECPLog> ecp_list = ecpService.searchData(ecpNo, description, cramerVersion, latestEcp, requestor,
                    fixedBy, module, caseOrCrNo, filesModifiedInPerforce, filesReleasedToCustomer, rolledIntoVersion,
                    specificFunc, page_no, page_size);


            ro.setCount(ecp_list.size());
            ro.setDetails(ecp_list);
        }else {
            ro.setCount(0);
            ro.setDetails(null);
        }

        return ResponseEntity.ok().body(ro);

    }


    /***
     * Ge count of total matching records for given parameters.
     */
    @RequestMapping(value = "/getTotalCountAllResults", method = RequestMethod.GET)
    public ResponseEntity<Metadata> getTotalCountAllResults(
            @RequestParam(value = "ecpNo", defaultValue = "", required = false) String ecpNo,
            @RequestParam(value = "description", defaultValue = "", required = false) String description,
            @RequestParam(value = "cramerVersion", defaultValue = "", required = false) List<String> cramerVersion,
            @RequestParam(value = "latestEcp", defaultValue = "", required = false) String latestEcp,
            @RequestParam(value = "requestor", defaultValue = "", required = false) String requestor,
            @RequestParam(value = "fixedBy", defaultValue = "", required = false) String fixedBy,
            @RequestParam(value = "module", defaultValue = "", required = false) List<String> module,
            @RequestParam(value = "caseOrCrNo", defaultValue = "", required = false) String caseOrCrNo,
            @RequestParam(value = "filesModifiedInPerforce", defaultValue = "", required = false) String filesModifiedInPerforce,
            @RequestParam(value = "filesReleasedToCustomer", defaultValue = "", required = false) String filesReleasedToCustomer,
            @RequestParam(value = "rolledIntoVersion", defaultValue = "", required = false) String rolledIntoVersion,
            @RequestParam(value = "specificFunc", defaultValue = "", required = false) String specificFunc,
            HttpServletRequest httpRequest, @RequestHeader(value = "Hostname", defaultValue = "disabled", required = false) String hostname,
            @RequestHeader(value = "HostAddress", defaultValue = "disabled", required = false) String HostAddress,
            @RequestHeader(value = "NTNET", defaultValue = "disabled", required = false) String ntnet) {

        Boolean minimumValuesProvided = ecpService.minimumValuesProvided(ecpNo, description, cramerVersion, latestEcp, requestor,
                fixedBy, module, caseOrCrNo, filesModifiedInPerforce, filesReleasedToCustomer, rolledIntoVersion,
                specificFunc);
        Metadata resultObject = new SearchResultMetadata();

        if (minimumValuesProvided){
            if (cramerVersion.isEmpty()) {
                cramerVersion = HotfixviewerApplication.distinctVersion;
            }
            if (module.isEmpty()) {
                module = HotfixviewerApplication.distinctModules;
            }

            Long result = ecpService.countMatchingRecords(ecpNo, description, cramerVersion, latestEcp, requestor,
                    fixedBy, module, caseOrCrNo, filesModifiedInPerforce, filesReleasedToCustomer, rolledIntoVersion,
                    specificFunc);

            //Long result = ecpService.countTotalHotfixes();

            resultObject.setCount( result.intValue());
            resultObject.setDetails(null);
        }else {
            resultObject.setCount(0);
            resultObject.setDetails(null);
        }


        return ResponseEntity.ok().body(resultObject);

    }


    private void logToDatabase(String hostname, String hostaddress, String ntnet,
                               List<String> searchInput, String requestName) {

        try {

            UserDetails userDetails = new UserDetails();
            userDetails.setDate(new Date());
            userDetails.setRequestPath(requestName);
            userDetails.setSearchInput(searchInput);
            userDetails.setHostaddress(hostaddress);
            userDetails.setHostname(hostname);
            userDetails.setNtnet(ntnet);

            dbHandler.addUserDetails(userDetails);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("Exception occurred while logging to database.");
        }


    }

    /**
     * Parse and store data from excel file into database.
     */
    @RequestMapping(value = "/getUnderlyingHFs", method = RequestMethod.GET)
    public ResponseEntity<Metadata> getUnderlyingHFs(
            @RequestParam(value = "latestEcp", defaultValue = "-", required = true) String latestEcp,
            HttpServletRequest request) {

        // To restrict the requests only from specific hosts, Uncomment the below line.
        // if (customConfig.getAllowedHosts().contains(client)) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, headerPrefix);

        Map<Integer, String> map = ecpService.getUnderlyingHF(latestEcp);
        Metadata ro = new UnderlyingHFMetadata();
        ro.setCount(map.size());
        ro.setDetails(map);



        return ResponseEntity.ok().headers(headers).body(ro);

    }

    @RequestMapping(value = "/getDistinctCramerVersions", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getDistinctCramerVersions(HttpServletRequest request) {

        // To restrict the requests only from specific hosts, Uncomment the below line.
        // if (customConfig.getAllowedHosts().contains(client)) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, headerPrefix);

        List<String> result = HotfixviewerApplication.distinctVersion;

        return ResponseEntity.ok().headers(headers).body(result);

    }

    @RequestMapping(value = "/getSummary", method = RequestMethod.GET)
    public HotfixSummary getDatabaseSummary() {
        return dbHandler.getSummary();
    }


    @RequestMapping(value = "/getUserDetails", method = RequestMethod.GET)
    public List<UserDetails> getUserDetails(
            @RequestParam(value = "host", defaultValue = "--", required = false) String host) {

        return dbHandler.getUserDetails(host);
    }

    @RequestMapping(value = "/getDistinctModules", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getDistinctModules(HttpServletRequest request) {

        // To restrict the requests only from specific hosts, Uncomment the below line.
        // if (customConfig.getAllowedHosts().contains(client)) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, headerPrefix);

        List<String> result = HotfixviewerApplication.distinctModules;

        return ResponseEntity.ok().headers(headers).body(result);

    }

}
