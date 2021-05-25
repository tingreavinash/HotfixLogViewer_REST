/**
 * This class is for merging the Excel file records into Database.
 *
 * @author Avinash Tingre
 */
package com.avinash.HotfixService.Service;

import com.avinash.HotfixService.Model.ECPLog;
import com.avinash.HotfixService.Model.HotfixSummary;
import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.impl.StreamingCell;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ECPFileHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ECPFileHandler.class);
    public static List<ECPLog> ecp_list = new ArrayList<ECPLog>();
    public static List<String> row_values = new ArrayList<String>();
    public static List<String> result = new ArrayList<String>();
    public static LinkedHashMap<String, Integer> COLUMN_INDEX = new LinkedHashMap<>();
    public static int maxColNumber;

    @Autowired
    private ECPLogService ecpService;
    @Autowired
    private DatabaseLogHandler dbhistoryService;
    @Value("${app.excelFilePath}")
    private String excelFilePath;

    @Value("${app.headerRowNum}")
    private int headerRowNum;


    @Value("${app.sheet_name}")
    private String sheetname;


    private static synchronized List<String> getListFromRow(Row row) {
        result.clear();

        for (int i = 0; i < maxColNumber; i++) {
            Cell c = row.getCell(i);

            if (c == null) {
                c = new StreamingCell(i, row.getRowNum(), true);
            }

            if (c.getColumnIndex() == COLUMN_INDEX.get(ECPLogConstants.requestDate)
                    || c.getColumnIndex() == COLUMN_INDEX.get(ECPLogConstants.targetDate)
                    || c.getColumnIndex() == COLUMN_INDEX.get(ECPLogConstants.releasedDate)) {
                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                if (c.getCellType() == CellType.NUMERIC && c.getDateCellValue() != null) {
                    String date = df.format(c.getDateCellValue());
                    result.add(date);
                } else if (c.getCellType() == CellType.STRING && !c.getStringCellValue().equals("")) {
                    result.add(c.getStringCellValue());
                } else {
                    result.add("-");
                }
                continue;
            }

            if (c.getCellType() == CellType.NUMERIC) {
                int val = (int) c.getNumericCellValue();
                result.add(String.valueOf(val));

            } else if (c.getCellType() == CellType.STRING && !c.getStringCellValue().equals("")) {
                result.add(c.getStringCellValue());

            } else {
                result.add("-");
            }

        }

        while (result.size() < maxColNumber) {
            result.add("-");
        }
        return result;

    }


    @SuppressWarnings("deprecation")
    private static synchronized ECPLog createObjectFromlist(List<String> row_values, long count) throws NullPointerException {
        ECPLog ecp_object = new ECPLog();
        ecp_object.set_id(count + 1);

        ecp_object.setCramerVersion(row_values.get(COLUMN_INDEX.get(ECPLogConstants.cramerVersion)));
        ecp_object.setIsPreRequisite(row_values.get(COLUMN_INDEX.get(ECPLogConstants.isPreRequisite)));
        ecp_object.setPrereqForLatestEcp(row_values.get(COLUMN_INDEX.get(ECPLogConstants.prereqForLatestEcp)));
        ecp_object.setLatestEcp(row_values.get(COLUMN_INDEX.get(ECPLogConstants.latestEcp)));
        ecp_object.setEcpNo(row_values.get(COLUMN_INDEX.get(ECPLogConstants.ecpNo)));
        if ((row_values.get(COLUMN_INDEX.get(ECPLogConstants.latestEcp)).equalsIgnoreCase(row_values.get(COLUMN_INDEX.get(ECPLogConstants.ecpNo))))) {
            ecp_object.setIsThisLatestHF("TRUE");
        } else {
            ecp_object.setIsThisLatestHF("FALSE");
        }
        ecp_object.setSequence(row_values.get(COLUMN_INDEX.get(ECPLogConstants.sequence)));
        ecp_object.setOrNo(row_values.get(COLUMN_INDEX.get(ECPLogConstants.orNo)));
        ecp_object.setDescription(row_values.get(COLUMN_INDEX.get(ECPLogConstants.description)));
        ecp_object.setStatus(row_values.get(COLUMN_INDEX.get(ECPLogConstants.status)));
        ecp_object.setFixedBy(row_values.get(COLUMN_INDEX.get(ECPLogConstants.fixedBy)));
        ecp_object.setModule(row_values.get(COLUMN_INDEX.get(ECPLogConstants.module)));
        ecp_object.setVersion(row_values.get(COLUMN_INDEX.get(ECPLogConstants.version)));
        ecp_object.setCaseOrCrNo(row_values.get(COLUMN_INDEX.get(ECPLogConstants.caseOrCrNo)));
        ecp_object.setRequestor(row_values.get(COLUMN_INDEX.get(ECPLogConstants.requestor)));
        ecp_object.setFilesModifiedInPerforce(row_values.get(COLUMN_INDEX.get(ECPLogConstants.filesModifiedInPerforce)));
        ecp_object.setFileLocationInPerforce(row_values.get(COLUMN_INDEX.get(ECPLogConstants.fileLocationInPerforce)));
        ecp_object.setFilesReleasedToCustomer(row_values.get(COLUMN_INDEX.get(ECPLogConstants.filesReleasedToCustomer)));
        ecp_object.setType(row_values.get(COLUMN_INDEX.get(ECPLogConstants.type)));
        ecp_object.setNotes(row_values.get(COLUMN_INDEX.get(ECPLogConstants.notes)));
        ecp_object.setDownloadCenter(row_values.get(COLUMN_INDEX.get(ECPLogConstants.downloadCenter)));
        ecp_object.setEcpReplaced(row_values.get(COLUMN_INDEX.get(ECPLogConstants.ecpReplaced)));
        ecp_object.setAdditionalInfo(row_values.get(COLUMN_INDEX.get(ECPLogConstants.additionalInfo)));
        ecp_object.setFixRolledIntoModule(row_values.get(COLUMN_INDEX.get(ECPLogConstants.fixRolledIntoModule)));
        ecp_object.setRolledIntoVersion(row_values.get(COLUMN_INDEX.get(ECPLogConstants.rolledIntoVersion)));
        ecp_object.setRollupCr(row_values.get(COLUMN_INDEX.get(ECPLogConstants.rollupCr)));
        ecp_object.setEscapingDefect(row_values.get(COLUMN_INDEX.get(ECPLogConstants.escapingDefect)));
        ecp_object.setReportingVersion(row_values.get(COLUMN_INDEX.get(ECPLogConstants.reportingVersion)));
        ecp_object.setOriginalIssue(row_values.get(COLUMN_INDEX.get(ECPLogConstants.originalIssue)));
        ecp_object.setAddedToExtranet(row_values.get(COLUMN_INDEX.get(ECPLogConstants.addedToExtranet)));
        ecp_object.setAddedToExtranetUpdate(row_values.get(COLUMN_INDEX.get(ECPLogConstants.addedToExtranetUpdate)));
        ecp_object.setAddedToPatchBundle(row_values.get(COLUMN_INDEX.get(ECPLogConstants.addedToPatchBundle)));
        ecp_object.setHfNotBuiltSep(row_values.get(COLUMN_INDEX.get(ECPLogConstants.hfNotBuiltSep)));
        ecp_object.setC4IssueAlso(row_values.get(COLUMN_INDEX.get(ECPLogConstants.c4IssueAlso)));
        ecp_object.setC5IssueAlso(row_values.get(COLUMN_INDEX.get(ECPLogConstants.c5IssueAlso)));
        ecp_object.setMissingBasicFunc(row_values.get(COLUMN_INDEX.get(ECPLogConstants.missingBasicFunc)));
        ecp_object.setNewComponent(row_values.get(COLUMN_INDEX.get(ECPLogConstants.newComponent)));
        ecp_object.setCausedByNewComp(row_values.get(COLUMN_INDEX.get(ECPLogConstants.causedByNewComp)));
        ecp_object.setPlatformIssue(row_values.get(COLUMN_INDEX.get(ECPLogConstants.platformIssue)));
        ecp_object.setPerfIssue(row_values.get(COLUMN_INDEX.get(ECPLogConstants.perfIssue)));
        ecp_object.setUpgradeIssue(row_values.get(COLUMN_INDEX.get(ECPLogConstants.upgradeIssue)));
        ecp_object.setNewFuncAdded(row_values.get(COLUMN_INDEX.get(ECPLogConstants.newFuncAdded)));
        ecp_object.setMandatoryEcp(row_values.get(COLUMN_INDEX.get(ECPLogConstants.mandatoryEcp)));
        ecp_object.setSpecificFunc(row_values.get(COLUMN_INDEX.get(ECPLogConstants.specificFunc)));
        ecp_object.setMultiModulesAffected(row_values.get(COLUMN_INDEX.get(ECPLogConstants.multiModulesAffected)));
        ecp_object.setSeverity(row_values.get(COLUMN_INDEX.get(ECPLogConstants.severity)));
        ecp_object.setPriority(row_values.get(COLUMN_INDEX.get(ECPLogConstants.priority)));
        ecp_object.setEcpFaulty(row_values.get(COLUMN_INDEX.get(ECPLogConstants.ecpFaulty)));
        ecp_object.setHfRolllupInfo(row_values.get(COLUMN_INDEX.get(ECPLogConstants.hfRolllupInfo)));

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");


        try {
            ecp_object.setRequestDate(df.parse(row_values.get(COLUMN_INDEX.get(ECPLogConstants.requestDate))));
        } catch (ParseException e) {
            ecp_object.setRequestDate(new Date(0, 0, 0));
        }
        try {
            ecp_object.setTargetDate(df.parse(row_values.get(COLUMN_INDEX.get(ECPLogConstants.targetDate))));
        } catch (ParseException e) {
            ecp_object.setTargetDate(new Date(0, 0, 0));
        }
        try {
            ecp_object.setReleasedDate(df.parse(row_values.get(COLUMN_INDEX.get(ECPLogConstants.releasedDate))));
        } catch (ParseException e) {
            ecp_object.setReleasedDate(new Date(0, 0, 0));
        }


        return ecp_object;
    }

    public synchronized void initializeColumnIndexes(Row headRow) {
        maxColNumber = headRow.getLastCellNum();
        for (int i = 0; i < maxColNumber; i++) {
            String str;
            try {
                str = headRow.getCell(i).getStringCellValue();
            } catch (NullPointerException ex) {
                str = "xxx";
            }

            COLUMN_INDEX.put(str, i);
        }
    }

    public synchronized long mergeExcelDataToDB() throws Exception {
        long total_records = 0;
        InputStream fis = null;
        Workbook workbook = null;
        Sheet sheet;
        try {

            int startRow = headerRowNum;

            int endRow = getLastRowNum();
            ecpService.deleteAll();
            LOG.info("Old records Deleted.");
            File file = new File(excelFilePath);
            fis = new FileInputStream(file);

            workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(fis);

            sheet = workbook.getSheet(sheetname);
            LOG.info("Data loading started: " + new Date());

            for (Row r : sheet) {
                if (r.getRowNum() == headerRowNum) {
                    initializeColumnIndexes(r);
                }

                if (r.getRowNum() > startRow && r.getRowNum() < endRow) {
                    ECPLog ecplog = new ECPLog();

                    row_values = getListFromRow(r);
                    try {
                        ecplog = createObjectFromlist(row_values, total_records);
                    } catch (NullPointerException ex) {
                        throw new NullPointerException("Column names are incorrectly defined. Check the values defined in ECPLogConstants class.\n");
                    }

                    ecpService.save(ecplog);
                    total_records++;
                    row_values.clear();
                }
            }
            LOG.info("Data loading finished: " + new Date());
            saveSummaryInDB();
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException(ex.getMessage());
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

    private synchronized void saveSummaryInDB() {

        long oldRecords = 0;
        if (dbhistoryService.getSummary() != null) {
            HotfixSummary oldSummary = dbhistoryService.getSummary();
            oldRecords = oldSummary.getTotalHotfixes();
        }


        HotfixSummary summary = new HotfixSummary();
        summary.setDatabaseCreatedAt(new Date());
        summary.setNewlyAddedHotfixes(ecpService.countAllHotfixes() - oldRecords);
        summary.setTotalHotfixes(ecpService.countAllHotfixes());
        dbhistoryService.addSummary(summary);
        LOG.info("Summary updated in DB !");

    }

    private synchronized int getLastRowNum() throws IOException {
        int last_num = 0;
        boolean flag = true;
        int start_row = headerRowNum;
        InputStream fis = null;
        Workbook workbook = null;
        Sheet sheet;
        try {
            File file = new File(excelFilePath);
            fis = new FileInputStream(file);
            workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(fis);
            sheet = workbook.getSheet(sheetname);


            for (Row r : sheet) {

                if (r.getRowNum() >= start_row) {
                    for (Cell c : r) {
                        if (c.getColumnIndex() == 1) {
                            last_num++;
                            if (c.getCellType() == CellType.STRING) {

                                if (c.getStringCellValue().equals("eof")) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (!flag) {
                        break;
                    }

                }
            }

        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException(ex.getMessage());
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return last_num + headerRowNum - 1;
    }

}
