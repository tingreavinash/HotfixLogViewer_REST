/**
 * This class is for merging the Excel file records into Database.
 * @author Avinash Tingre
 */
package com.avinash.hotfixviewer.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avinash.hotfixviewer.Model.HotfixSummary;
import com.avinash.hotfixviewer.Model.ECPLog;
import com.avinash.hotfixviewer.Model.YAMLConfig;
import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.impl.StreamingCell;

@Service
public class ECPFileHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ECPFileHandler.class);

	@Autowired
	private ECPLogService ecpService;
	@Autowired
	private DatabaseLogHandler dbhistoryService;
	@Autowired
	public YAMLConfig customConfig;

	public static List<ECPLog> ecp_list = new ArrayList<ECPLog>();
	public static List<String> row_values = new ArrayList<String>();
	public static List<String> nested_list = new ArrayList<String>();

	/**
	 * Method for merging Excel data into Database.
	 * 
	 * @return void
	 * @throws IOException
	 */
	public long mergeExcelDataToDB() throws IOException {
		long total_records = 0;
		InputStream fis = null;
		Workbook workbook = null;
		Sheet sheet;
		try {
			int startRow = 7;
			int endRow = getLastRowNum();
			// int endRow = 1000;
			ecpService.deleteAllRecords();
			LOG.info("Old records Deleted.");
			File file = new File(customConfig.getExcelFilePath());
			fis = new FileInputStream(file);

			workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(fis);

			sheet = workbook.getSheetAt(2);
			LOG.info("Data loading started: " + new Date());

			for (Row r : sheet) {
				if (r.getRowNum() >= startRow && r.getRowNum() < endRow) {
					ECPLog ecplog = new ECPLog();

					row_values = getListFromRow(r);
					ecplog = createObjectFromlist(row_values, total_records);

					ecpService.addECP(ecplog);
					total_records++;
					row_values.clear();
				}
			}
			LOG.info("Data loading finished: " + new Date());
			saveSummaryInDB();
		} catch (FileNotFoundException ex) {
			LOG.error("File not found !!\n" + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}

			if (fis != null) {
				fis.close();
			}
			ecp_list.clear();
		}
		return total_records;
	}
	
	private void saveSummaryInDB() {
		
		long oldRecords =0;
		if (dbhistoryService.getSummary() !=null) {
			HotfixSummary oldSummary =dbhistoryService.getSummary();
			oldRecords=oldSummary.getTotalHotfixes();	
		}
		 
		
		HotfixSummary summary = new HotfixSummary();
		summary.setDatabaseCreatedAt(new Date());
		summary.setNewlyAddedHotfixes(ecpService.getCountOfHotfixes() - oldRecords);
		summary.setTotalHotfixes(ecpService.getCountOfHotfixes());
		dbhistoryService.addSummary(summary);
		LOG.info("Summary updated in DB !");
		
	}
	/**
	 * Private method for converting "Row" into List of strings.
	 * 
	 * @param row
	 * @return List<String>
	 */
	private static List<String> getListFromRow(Row row) {
		nested_list.clear();
		
		for (int i = 0; i < 52; i++) {
			Cell c = row.getCell(i);

			if (c == null) {
				c = new StreamingCell(i, row.getRowNum(), true);
			}
			if(c.getColumnIndex()==0) {
				continue;
			}

			if (c.getColumnIndex() == 11 || c.getColumnIndex() == 12 || c.getColumnIndex() == 13) {
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				if (c.getCellType() == CellType.NUMERIC && c.getDateCellValue() != null) {
					String date = df.format(c.getDateCellValue());
					nested_list.add(date);
				} else if (c.getCellType() == CellType.STRING && c.getStringCellValue() != "") {
					nested_list.add(c.getStringCellValue());
				} else {
					nested_list.add("-");
				}
				continue;
			}

			if (c.getCellType() == CellType.NUMERIC) {
				int val = (int) c.getNumericCellValue();
				nested_list.add(String.valueOf(val));

			} else if (c.getCellType() == CellType.STRING && c.getStringCellValue() != "") {
				nested_list.add(c.getStringCellValue());

			} else {
				nested_list.add("-");
			}

		}

		while (nested_list.size() < 52) {
			nested_list.add("-");
		}
		return nested_list;

	}

	/**
	 * Method for converting list into ECPLog object.
	 * 
	 * @param row_values : Output of getListFromRow()
	 * @param count      : This for counting the total number of objects (Records).
	 * @return Object of type ECPLog
	 */
	@SuppressWarnings("deprecation")
	private static ECPLog createObjectFromlist(List<String> row_values, long count) {

		ECPLog ecp_object = new ECPLog();
		ecp_object.set_id(count + 1);
		ecp_object.setCramerVersion(row_values.get(ECPLogConstants.cramerVersion));
		ecp_object.setIsPreRequisite(row_values.get(ECPLogConstants.isPreRequisite));
		ecp_object.setPrereqForLatestEcp(row_values.get(ECPLogConstants.prereqForLatestEcp));
		ecp_object.setLatestEcp(row_values.get(ECPLogConstants.latestEcp));
		ecp_object.setEcpNo(row_values.get(ECPLogConstants.ecpNo));
		if((row_values.get(ECPLogConstants.latestEcp)).equalsIgnoreCase(row_values.get(ECPLogConstants.ecpNo))) {
			ecp_object.setIsThisLatestHF("TRUE");
		}else {
			ecp_object.setIsThisLatestHF("FALSE");
		}
		ecp_object.setSequence(row_values.get(ECPLogConstants.sequence));
		ecp_object.setOrNo(row_values.get(ECPLogConstants.orNo));
		ecp_object.setDescription(row_values.get(ECPLogConstants.description));
		ecp_object.setStatus(row_values.get(ECPLogConstants.status));
		ecp_object.setFixedBy(row_values.get(ECPLogConstants.fixedBy));
		ecp_object.setModule(row_values.get(ECPLogConstants.module));
		ecp_object.setVersion(row_values.get(ECPLogConstants.version));
		ecp_object.setCaseOrCrNo(row_values.get(ECPLogConstants.caseOrCrNo));
		ecp_object.setRequestor(row_values.get(ECPLogConstants.requestor));
		ecp_object.setFilesModifiedInPerforce(row_values.get(ECPLogConstants.filesModifiedInPerforce));
		ecp_object.setFileLocationInPerforce(row_values.get(ECPLogConstants.fileLocationInPerforce));
		ecp_object.setFilesReleasedToCustomer(row_values.get(ECPLogConstants.filesReleasedToCustomer));
		ecp_object.setType(row_values.get(ECPLogConstants.type));
		ecp_object.setNotes(row_values.get(ECPLogConstants.notes));
		ecp_object.setDownloadCenter(row_values.get(ECPLogConstants.downloadCenter));
		ecp_object.setEcpReplaced(row_values.get(ECPLogConstants.ecpReplaced));
		ecp_object.setAdditionalInfo(row_values.get(ECPLogConstants.additionalInfo));
		ecp_object.setFixRolledIntoModule(row_values.get(ECPLogConstants.fixRolledIntoModule));
		ecp_object.setRolledIntoVersion(row_values.get(ECPLogConstants.rolledIntoVersion));
		ecp_object.setRollupCr(row_values.get(ECPLogConstants.rollupCr));
		ecp_object.setEscapingDefect(row_values.get(ECPLogConstants.escapingDefect));
		ecp_object.setReportingVersion(row_values.get(ECPLogConstants.reportingVersion));
		ecp_object.setOriginalIssue(row_values.get(ECPLogConstants.originalIssue));
		ecp_object.setAddedToExtranet(row_values.get(ECPLogConstants.addedToExtranet));
		ecp_object.setAddedToExtranetUpdate(row_values.get(ECPLogConstants.addedToExtranetUpdate));
		ecp_object.setAddedToPatchBundle(row_values.get(ECPLogConstants.addedToPatchBundle));
		ecp_object.setHfNotBuiltSep(row_values.get(ECPLogConstants.hfNotBuiltSep));
		ecp_object.setC4IssueAlso(row_values.get(ECPLogConstants.c4IssueAlso));
		ecp_object.setC5IssueAlso(row_values.get(ECPLogConstants.c5IssueAlso));
		ecp_object.setMissingBasicFunc(row_values.get(ECPLogConstants.missingBasicFunc));
		ecp_object.setNewComponent(row_values.get(ECPLogConstants.newComponent));
		ecp_object.setCausedByNewComp(row_values.get(ECPLogConstants.causedByNewComp));
		ecp_object.setPlatformIssue(row_values.get(ECPLogConstants.platformIssue));
		ecp_object.setPerfIssue(row_values.get(ECPLogConstants.perfIssue));
		ecp_object.setUpgradeIssue(row_values.get(ECPLogConstants.upgradeIssue));
		ecp_object.setNewFuncAdded(row_values.get(ECPLogConstants.newFuncAdded));
		ecp_object.setMandatoryEcp(row_values.get(ECPLogConstants.mandatoryEcp));
		ecp_object.setSpecificFunc(row_values.get(ECPLogConstants.specificFunc));
		ecp_object.setMultiModulesAffected(row_values.get(ECPLogConstants.multiModulesAffected));
		ecp_object.setSeverity(row_values.get(ECPLogConstants.severity));
		ecp_object.setPriority(row_values.get(ECPLogConstants.priority));
		ecp_object.setEcpFaulty(row_values.get(ECPLogConstants.ecpFaulty));
		ecp_object.setHfRolllupInfo(row_values.get(ECPLogConstants.hfRolllupInfo));
		
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 
		 

		
		try {
			ecp_object.setRequestDate(new SimpleDateFormat("dd-MMM-yyyy").parse(row_values.get(ECPLogConstants.requestDate)) );
		} catch (ParseException e) {
			ecp_object.setRequestDate(new Date(0000, 00, 00));
		}
		try {
			ecp_object.setTargetDate(new SimpleDateFormat("dd-MMM-yyyy").parse(row_values.get(ECPLogConstants.targetDate)) );
		}catch (ParseException e) {
			ecp_object.setTargetDate(new Date(0000, 00, 00));
		}
		try {
			ecp_object.setReleasedDate(new SimpleDateFormat("dd-MMM-yyyy").parse(row_values.get(ECPLogConstants.releasedDate)));
		}catch (ParseException e) {
			ecp_object.setReleasedDate(new Date(0000, 00, 00));
		}

		return ecp_object;
	}

	/**
	 * Method for getting last row number in ECPLog.
	 * 
	 * @return Last row number.
	 * @throws IOException
	 */
	private int getLastRowNum() throws IOException {
		int last_num = 0;
		boolean flag = true;
		int start_row = 7;
		InputStream fis = null;
		Workbook workbook = null;
		Sheet sheet = null;
		try {
			File file = new File(customConfig.getExcelFilePath());
			fis = new FileInputStream(file);
			workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(fis);
			sheet = workbook.getSheetAt(2);

			for (Row r : sheet) {

				if (r.getRowNum() >= start_row) {
					for (Cell c : r) {
						// cellno++;
						if (c.getColumnIndex() == 1) {
							last_num++;
							if (c.getCellType() == CellType.STRING) {

								if (c.getStringCellValue().equals("eof")) {
									flag = false;
									break;
								}
							}
						}
						if (flag != true)
							break;
					}
					if (flag != true) {
						break;
					}

				}
			}

		} catch (FileNotFoundException ex) {
			LOG.error("File not found !!\n" + ex.getMessage());
		} catch (Exception ex) {
			LOG.error("Exception: \n" + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return last_num + 4;
	}

}
