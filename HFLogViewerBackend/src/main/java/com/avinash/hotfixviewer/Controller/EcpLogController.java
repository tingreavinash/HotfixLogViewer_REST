/**
 * This is a RestController class for exposing the REST endpoints.
 * @author Avinash Tingre
 */
package com.avinash.hotfixviewer.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avinash.hotfixviewer.HotfixviewerApplication;
import com.avinash.hotfixviewer.Model.DBHistory;
import com.avinash.hotfixviewer.Model.ECPLog;
import com.avinash.hotfixviewer.Model.ECPLogResponseObject;
import com.avinash.hotfixviewer.Model.UnderlyingHFResponseObject;
import com.avinash.hotfixviewer.Model.YAMLConfig;
import com.avinash.hotfixviewer.Service.DBHistoryService;
import com.avinash.hotfixviewer.Service.ECPFileHandler;
import com.avinash.hotfixviewer.Service.ECPLogService;


@RestController
@RequestMapping("/HFLogViewer")
public class EcpLogController {
	private static final Logger LOG = LoggerFactory.getLogger(EcpLogController.class);

	
	@Autowired
	private ECPLogService ecpService;
	@Autowired
	private ECPFileHandler ecpExcel;
	@Autowired
	private YAMLConfig customConfig;
	@Autowired
	private DBHistoryService dbhistoryService;
	/**
	 * REST endpoint for getting "Pageable" results matching below parameters.
	 * @param page_no (mandatory)
	 * @param page_size (mandatory)
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
			@RequestParam(value= "page_no", required = true) int page_no,
			@RequestParam(value= "page_size", required = true) int page_size,
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
			HttpServletRequest request) {
		String client = request.getRemoteHost();

		if (true) {
			//To restrict the requests only from specific hosts, Uncomment the below line.
			//if (customConfig.getAllowedHosts().contains(client)) {
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

		return new ResponseEntity<ECPLogResponseObject>(HttpStatus.UNAUTHORIZED);

	}

	
	/**
	 * REST endpoint for getting "all" results matching below parameters.
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
	 * @return
	 */
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
			HttpServletRequest request,
			@RequestHeader("Hostname") String clienthost,
			@RequestHeader("SearchInput") String SearchInput) {
		String client = request.getRemoteHost();
		LOG.info("--------------");
		LOG.info("User:\t"+clienthost);
		LOG.info("Input:\t"+SearchInput);
		
		if (true) {
		//To restrict the requests only from specific hosts, Uncomment the below line.
		//if (customConfig.getAllowedHosts().contains(client)) {
			if (cramerVersion.isEmpty() || cramerVersion == null) {
				cramerVersion = HotfixviewerApplication.distinctCramerVersion;
			}
			if (module.isEmpty()) {
				module = HotfixviewerApplication.distinctModules;
			}
			
			List<ECPLog> ecp_list = ecpService.getResultByFields(ecpNo, description, cramerVersion, latestEcp, requestor,
					fixedBy, module, caseOrCrNo, filesModifiedInPerforce, filesReleasedToCustomer, rolledIntoVersion,
					specificFunc);

			/*HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, customConfig.getHeader_acao());
			headers.add(HttpHeaders.ACCEPT_CHARSET, "text/html;charset=utf-8");
			*/
			ECPLogResponseObject ro = new ECPLogResponseObject();
			ro.setCount(ecp_list.size());
			ro.setRecords(ecp_list);
			//return ResponseEntity.ok().headers(headers).body(ro);
			return ResponseEntity.ok().body(ro);

		}
		return new ResponseEntity<ECPLogResponseObject>(HttpStatus.UNAUTHORIZED);

	}

	/**
	 * REST endpoint for storing records from excel into DB.
	 * It will delete existing records and add new records.
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	/*@RequestMapping(value = "/loadDataInDB", method = RequestMethod.GET)
	public ResponseEntity<String> loadDataInDB(HttpServletRequest request) throws IOException {
		String client = request.getRemoteHost();
		if (customConfig.getAllowedHosts().contains(client)) {
			long record_count = ecpExcel.mergeExcelDataToDB();
			if (record_count == 0) {
				return new ResponseEntity<String>("Operation failed.", HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>("Total records stored in DB:" + record_count, HttpStatus.OK);
			}
			
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

	}*/
	

	@RequestMapping(value = "/getUnderlyingHFs", method = RequestMethod.GET)
	public ResponseEntity<UnderlyingHFResponseObject> getUnderlyingHFs(
			@RequestParam(value="latestEcp",defaultValue = "-", required=true) String latestEcp,
			HttpServletRequest request) {
		String client = request.getRemoteHost();
		if (true) {
			//To restrict the requests only from specific hosts, Uncomment the below line.
			//if (customConfig.getAllowedHosts().contains(client)) {
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, customConfig.getHeader_acao());
			
			Map<Integer, String> map = ecpService.getUnderlyingHF(latestEcp);
			UnderlyingHFResponseObject ro = new UnderlyingHFResponseObject();
			ro.setCount(map.size());
			ro.setRecords(map);
			
			return ResponseEntity.ok().headers(headers).body(ro);

		}
		return new ResponseEntity<UnderlyingHFResponseObject>(HttpStatus.UNAUTHORIZED);

	}
	
	@RequestMapping(value = "/getDistinctCramerVersions", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getDistinctCramerVersions(
			HttpServletRequest request) {
		String client = request.getRemoteHost();
		if (true) {
			//To restrict the requests only from specific hosts, Uncomment the below line.
			//if (customConfig.getAllowedHosts().contains(client)) {
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, customConfig.getHeader_acao());
			
			List<String> result = HotfixviewerApplication.distinctCramerVersion;
			
			return ResponseEntity.ok().headers(headers).body(result);

		}
		return new ResponseEntity<List<String>>(HttpStatus.UNAUTHORIZED);

	}
	
	@RequestMapping(value="/getSummary", method=RequestMethod.GET)
	public DBHistory getDatabaseSummary() {
		DBHistory summary = dbhistoryService.getLatestSummary();
		return summary ;
	}
	
	@RequestMapping(value = "/getDistinctModules", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getDistinctModules(
			HttpServletRequest request) {
		String client = request.getRemoteHost();
		if (true) {
			//To restrict the requests only from specific hosts, Uncomment the below line.
			//if (customConfig.getAllowedHosts().contains(client)) {
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, customConfig.getHeader_acao());
			
			List<String> result = HotfixviewerApplication.distinctModules;
			
			return ResponseEntity.ok().headers(headers).body(result);

		}
		return new ResponseEntity<List<String>>(HttpStatus.UNAUTHORIZED);

	}


}
