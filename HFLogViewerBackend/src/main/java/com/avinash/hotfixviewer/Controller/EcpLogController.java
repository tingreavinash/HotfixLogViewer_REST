/**
 * This is a RestController class for exposing the REST endpoints.
 * @author Avinash Tingre
 */
package com.avinash.hotfixviewer.Controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avinash.hotfixviewer.HotfixviewerApplication;
import com.avinash.hotfixviewer.Model.HotfixSummary;
import com.avinash.hotfixviewer.Model.ECPLog;
import com.avinash.hotfixviewer.Model.ECPLogResponseObject;
import com.avinash.hotfixviewer.Model.UnderlyingHFResponseObject;
import com.avinash.hotfixviewer.Model.UserDetails;
import com.avinash.hotfixviewer.Model.YAMLConfig;
import com.avinash.hotfixviewer.Service.DatabaseLogHandler;
import com.avinash.hotfixviewer.Service.ECPLogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/HFLogViewer")
@Tag(name = "Hotfix Search", description = "API for fetching hotfixes")
public class EcpLogController {
	private static final Logger LOG = LoggerFactory.getLogger(EcpLogController.class);

	@Autowired
	private ECPLogService ecpService;

	@Autowired
	private YAMLConfig customConfig;
	@Autowired
	private DatabaseLogHandler dbHandler;

	/**
	 * REST endpoint for getting "Pageable" results matching below parameters.
	 * 
	 * @param page_no                 (mandatory)
	 * @param page_size               (mandatory)
	 * @param ecpNo
	 * @param description
	 * @param cramerVersion
	 * @param latestEcp
	 * @param requestor
	 * @param fixedBy
	 * @param module
	 * @param caseOrCrNo
	 * @param filesModifiedInPerforce
	 * @param filesReleasedToCustomer
	 * @param rolledIntoVersion
	 * @param specificFunc
	 * @param request
	 * @return ECPLog objects
	 */
	@RequestMapping(value = "/getPageableResult", method = RequestMethod.GET)
	public ResponseEntity<ECPLogResponseObject> getPageableResult(
			@RequestParam(value = "page_no", required = true) int page_no,
			@RequestParam(value = "page_size", required = true) int page_size,
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
			HttpServletRequest request, @RequestHeader("Hostname") String hostname,
			@RequestHeader("HostAddress") String HostAddress,
			@RequestHeader("NTNET") String ntnet) {
		
		List<String> requestInput = new ArrayList<String>();
		requestInput.add("pageNo="+page_no);
		requestInput.add("pageSize="+page_size);
		requestInput.add("ecpNo="+ecpNo);
		requestInput.add("description="+description);
		requestInput.add("latestEcp="+latestEcp);
		requestInput.add("requestor="+requestor);
		requestInput.add("fixedBy="+fixedBy);
		requestInput.add("caseOrCRNo="+caseOrCrNo);
		requestInput.add("filesModifiedInPerforce="+filesModifiedInPerforce);
		requestInput.add("filesReleasedToCustomer="+filesReleasedToCustomer);
		requestInput.add("specificFunc="+specificFunc);
		requestInput.add("module="+module);
		requestInput.add("cramerVersion="+cramerVersion);
		
		logToDatabase(hostname, HostAddress, ntnet, requestInput, "/getPageableResult");
		

		if (cramerVersion.isEmpty() || cramerVersion == null) {
			cramerVersion = HotfixviewerApplication.distinctCramerVersion;
		}
		if (module.isEmpty() || module == null) {
			module = HotfixviewerApplication.distinctModules;
		}

		List<ECPLog> ecp_list = ecpService.getResultByFields(ecpNo, description, cramerVersion, latestEcp, requestor,
				fixedBy, module, caseOrCrNo, filesModifiedInPerforce, filesReleasedToCustomer, rolledIntoVersion,
				specificFunc, page_no, page_size);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, customConfig.getHeader_acao());

		ECPLogResponseObject ro = new ECPLogResponseObject();
		ro.setCount(ecp_list.size());
		ro.setRecords(ecp_list);
		return ResponseEntity.ok().headers(headers).body(ro);

	}

	/**
	 * REST endpoint for getting "all" results matching below parameters.
	 * 
	 * @param ecpNo
	 * @param description
	 * @param cramerVersion
	 * @param latestEcp
	 * @param requestor
	 * @param fixedBy
	 * @param module
	 * @param caseOrCrNo
	 * @param filesModifiedInPerforce
	 * @param filesReleasedToCustomer
	 * @param rolledIntoVersion
	 * @param specificFunc
	 * @param httpRequest
	 * @return
	 */
    @Operation(summary = "Find all hotfixes", description = "Hotfix search with given criteria.", tags = { "Hotfix Search" })
    @RequestMapping(value = "/getAllResults", method = RequestMethod.GET)
	public ResponseEntity<ECPLogResponseObject> getAllResults(
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
			HttpServletRequest httpRequest, @RequestHeader(value="Hostname", defaultValue = "disabled", required = false) String hostname,
			@RequestHeader(value="HostAddress", defaultValue = "disabled", required = false) String HostAddress,
			@RequestHeader(value="NTNET", defaultValue = "disabled", required = false) String ntnet) {
		

		List<String> requestInput = new ArrayList<String>();
		
		if(ecpNo.length() > 0) requestInput.add("Hotfix No: "+ecpNo+", ");
		if(latestEcp.length() > 0) requestInput.add("Latest Hotfix: "+latestEcp+", ");
		if(description.length() > 0) requestInput.add("Description: "+description+", ");
		if(cramerVersion.size() >0) requestInput.add("Versions: "+cramerVersion+", ");
		if(requestor.length() > 0) requestInput.add("Requested by: "+requestor+", ");
		if(fixedBy.length() > 0) requestInput.add("Fixed by: "+fixedBy+", ");
		if(module.size() > 0) requestInput.add("Modules: "+module+", ");
		if(caseOrCrNo.length() > 0) requestInput.add("Case or CR No: "+caseOrCrNo+", ");
		if(filesModifiedInPerforce.length() > 0) requestInput.add("Files modified: "+filesModifiedInPerforce+", ");
		if(filesReleasedToCustomer.length() > 0) requestInput.add("Files released: "+filesReleasedToCustomer+", ");
		if(specificFunc.length() > 0) requestInput.add("Specific function: "+specificFunc+", ");

		logToDatabase(hostname, HostAddress, ntnet, requestInput, "/getAllResults");
		
		if (cramerVersion.isEmpty() || cramerVersion == null) {
			cramerVersion = HotfixviewerApplication.distinctCramerVersion;
		}
		if (module.isEmpty()) {
			module = HotfixviewerApplication.distinctModules;
		}

		List<ECPLog> ecp_list = ecpService.getResultByFields(ecpNo, description, cramerVersion, latestEcp, requestor,
				fixedBy, module, caseOrCrNo, filesModifiedInPerforce, filesReleasedToCustomer, rolledIntoVersion,
				specificFunc);

		ECPLogResponseObject ro = new ECPLogResponseObject();
		ro.setCount(ecp_list.size());
		ro.setRecords(ecp_list);
		return ResponseEntity.ok().body(ro);

	}
	
	private void logToDatabase(String hostname, String hostaddress, String ntnet,
			List<String> searchInput, String requestName ) {
		
		try {
			
			UserDetails userDetails = new UserDetails();
			userDetails.setDate(new Date());
			userDetails.setRequestPath(requestName);
			userDetails.setSearchInput(searchInput);
			userDetails.setHostaddress(hostaddress);
			userDetails.setHostname(hostname);
			userDetails.setNtnet(ntnet);
			
			
			dbHandler.addUserDetails(userDetails);
			LOG.info("\nSaved user details to database.");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn("Exception occurred while logging to database.");
		}
		

	}

	/**
	 * REST endpoint for storing records from excel into DB. It will delete existing
	 * records and add new records.
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = "/getUnderlyingHFs", method = RequestMethod.GET)
	public ResponseEntity<UnderlyingHFResponseObject> getUnderlyingHFs(
			@RequestParam(value = "latestEcp", defaultValue = "-", required = true) String latestEcp,
			HttpServletRequest request) {

		// To restrict the requests only from specific hosts, Uncomment the below line.
		// if (customConfig.getAllowedHosts().contains(client)) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, customConfig.getHeader_acao());

		Map<Integer, String> map = ecpService.getUnderlyingHF(latestEcp);
		UnderlyingHFResponseObject ro = new UnderlyingHFResponseObject();
		ro.setCount(map.size());
		ro.setRecords(map);

		return ResponseEntity.ok().headers(headers).body(ro);

	}

	@RequestMapping(value = "/getDistinctCramerVersions", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getDistinctCramerVersions(HttpServletRequest request) {

		// To restrict the requests only from specific hosts, Uncomment the below line.
		// if (customConfig.getAllowedHosts().contains(client)) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, customConfig.getHeader_acao());

		List<String> result = HotfixviewerApplication.distinctCramerVersion;

		return ResponseEntity.ok().headers(headers).body(result);

	}

	@RequestMapping(value = "/getSummary", method = RequestMethod.GET)
	public HotfixSummary getDatabaseSummary() {
		HotfixSummary summary = dbHandler.getSummary();
		return summary;
	}
	
	@RequestMapping(value = "/getUserDetails", method = RequestMethod.GET)
	public List<UserDetails> getUserDetails(
			@RequestParam(value = "host", defaultValue = "--", required = false) String host) {

		List<UserDetails> userDetails = dbHandler.getUserDetails(host);
		return userDetails;
	}

	@RequestMapping(value = "/getDistinctModules", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getDistinctModules(HttpServletRequest request) {

		// To restrict the requests only from specific hosts, Uncomment the below line.
		// if (customConfig.getAllowedHosts().contains(client)) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, customConfig.getHeader_acao());

		List<String> result = HotfixviewerApplication.distinctModules;

		return ResponseEntity.ok().headers(headers).body(result);

	}

}
