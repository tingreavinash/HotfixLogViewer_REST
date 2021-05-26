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
    private static final List<ECPLog> ecp_list = new ArrayList<ECPLog>();
    private static List<String> row_values = new ArrayList<String>();
    private static final List<String> result = new ArrayList<String>();
    private static final LinkedHashMap<String, Integer> COLUMN_INDEX = new LinkedHashMap<>();
    private static int ColumnCount;

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


    private static synchronized List<String> getListFromRow(Row row) throws NullPointerException {
        result.clear();

        for (int i = 0; i < ColumnCount; i++) {
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

        while (result.size() < ColumnCount) {
            result.add("-");
        }
        return result;

    }

    private static synchronized String getValue(String key) {
        return row_values.get(COLUMN_INDEX.get(key));
    }

    @SuppressWarnings("deprecation")
    private static synchronized ECPLog createObjectFromlist(long count) throws NullPointerException {
        ECPLog ecp_object = new ECPLog();
        ecp_object.set_id(count + 1);

        ecp_object.setCramerVersion(getValue(ECPLogConstants.cramerVersion));
        ecp_object.setIsPreRequisite(getValue(ECPLogConstants.isPreRequisite));
        ecp_object.setPrereqForLatestEcp(getValue(ECPLogConstants.prereqForLatestEcp));
        ecp_object.setLatestEcp(getValue(ECPLogConstants.latestEcp));
        ecp_object.setEcpNo(getValue(ECPLogConstants.ecpNo));
        if ((getValue(ECPLogConstants.latestEcp)).equalsIgnoreCase(getValue(ECPLogConstants.ecpNo))) {
            ecp_object.setIsThisLatestHF("TRUE");
        } else {
            ecp_object.setIsThisLatestHF("FALSE");
        }
        ecp_object.setSequence(getValue(ECPLogConstants.sequence));
        ecp_object.setOrNo(getValue(ECPLogConstants.orNo));
        ecp_object.setDescription(getValue(ECPLogConstants.description));
        ecp_object.setStatus(getValue(ECPLogConstants.status));
        ecp_object.setFixedBy(getValue(ECPLogConstants.fixedBy));
        ecp_object.setModule(getValue(ECPLogConstants.module));
        ecp_object.setVersion(getValue(ECPLogConstants.version));
        ecp_object.setCaseOrCrNo(getValue(ECPLogConstants.caseOrCrNo));
        ecp_object.setRequestor(getValue(ECPLogConstants.requestor));
        ecp_object.setFilesModifiedInPerforce(getValue(ECPLogConstants.filesModifiedInPerforce));
        ecp_object.setFileLocationInPerforce(getValue(ECPLogConstants.fileLocationInPerforce));
        ecp_object.setFilesReleasedToCustomer(getValue(ECPLogConstants.filesReleasedToCustomer));
        ecp_object.setType(getValue(ECPLogConstants.type));
        ecp_object.setNotes(getValue(ECPLogConstants.notes));
        ecp_object.setDownloadCenter(getValue(ECPLogConstants.downloadCenter));
        ecp_object.setEcpReplaced(getValue(ECPLogConstants.ecpReplaced));
        ecp_object.setAdditionalInfo(getValue(ECPLogConstants.additionalInfo));
        ecp_object.setFixRolledIntoModule(getValue(ECPLogConstants.fixRolledIntoModule));
        ecp_object.setRolledIntoVersion(getValue(ECPLogConstants.rolledIntoVersion));
        ecp_object.setRollupCr(getValue(ECPLogConstants.rollupCr));
        ecp_object.setEscapingDefect(getValue(ECPLogConstants.escapingDefect));
        ecp_object.setReportingVersion(getValue(ECPLogConstants.reportingVersion));
        ecp_object.setOriginalIssue(getValue(ECPLogConstants.originalIssue));
        ecp_object.setAddedToExtranet(getValue(ECPLogConstants.addedToExtranet));
        ecp_object.setAddedToExtranetUpdate(getValue(ECPLogConstants.addedToExtranetUpdate));
        ecp_object.setAddedToPatchBundle(getValue(ECPLogConstants.addedToPatchBundle));
        ecp_object.setHfNotBuiltSep(getValue(ECPLogConstants.hfNotBuiltSep));
        ecp_object.setC4IssueAlso(getValue(ECPLogConstants.c4IssueAlso));
        ecp_object.setC5IssueAlso(getValue(ECPLogConstants.c5IssueAlso));
        ecp_object.setMissingBasicFunc(getValue(ECPLogConstants.missingBasicFunc));
        ecp_object.setNewComponent(getValue(ECPLogConstants.newComponent));
        ecp_object.setCausedByNewComp(getValue(ECPLogConstants.causedByNewComp));
        ecp_object.setPlatformIssue(getValue(ECPLogConstants.platformIssue));
        ecp_object.setPerfIssue(getValue(ECPLogConstants.perfIssue));
        ecp_object.setUpgradeIssue(getValue(ECPLogConstants.upgradeIssue));
        ecp_object.setNewFuncAdded(getValue(ECPLogConstants.newFuncAdded));
        ecp_object.setMandatoryEcp(getValue(ECPLogConstants.mandatoryEcp));
        ecp_object.setSpecificFunc(getValue(ECPLogConstants.specificFunc));
        ecp_object.setMultiModulesAffected(getValue(ECPLogConstants.multiModulesAffected));
        ecp_object.setSeverity(getValue(ECPLogConstants.severity));
        ecp_object.setPriority(getValue(ECPLogConstants.priority));
        ecp_object.setEcpFaulty(getValue(ECPLogConstants.ecpFaulty));
        ecp_object.setHfRolllupInfo(getValue(ECPLogConstants.hfRolllupInfo));

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");


        try {
            ecp_object.setRequestDate(df.parse(getValue(ECPLogConstants.requestDate)));
        } catch (ParseException e) {
            ecp_object.setRequestDate(new Date(0, 0, 0));
        }
        try {
            ecp_object.setTargetDate(df.parse(getValue(ECPLogConstants.targetDate)));
        } catch (ParseException e) {
            ecp_object.setTargetDate(new Date(0, 0, 0));
        }
        try {
            ecp_object.setReleasedDate(df.parse(getValue(ECPLogConstants.releasedDate)));
        } catch (ParseException e) {
            ecp_object.setReleasedDate(new Date(0, 0, 0));
        }
        return ecp_object;
    }

    public synchronized void initializeColumnIndexes(Row headRow) {
        ColumnCount = headRow.getLastCellNum();
        for (int colIndex = 0; colIndex < ColumnCount; colIndex++) {
            String headerName;
            try {
                headerName = headRow.getCell(colIndex).getStringCellValue();
            } catch (NullPointerException ex) {
                headerName = "xxx";
            }

            COLUMN_INDEX.put(headerName, colIndex);
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

                    try {
                        row_values = getListFromRow(r);
                        ecplog = createObjectFromlist(total_records);
                    } catch (NullPointerException ex) {
                        Exception ex1 = new NullPointerException("Column names are incorrectly defined. Check the values defined in ECPLogConstants class.\n");
                        ex1.setStackTrace(ex.getStackTrace());
                        throw ex1;
                    }

                    ecpService.save(ecplog);
                    total_records++;
                    row_values.clear();
                }
            }
            LOG.info("Data loading finished: " + new Date());
            saveSummaryInDB();
        } catch (FileNotFoundException ex) {
            throw ex;
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
            throw ex;
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
