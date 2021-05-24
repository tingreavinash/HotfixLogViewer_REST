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
    public static List<String> nested_list = new ArrayList<String>();
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

    /**
     * Private method for converting "Row" into List of strings.
     *
     * @param row
     * @return List<String>
     */
    private static synchronized List<String> getListFromRow(Row row) {
        nested_list.clear();

        for (int i = 0; i < maxColNumber; i++) {
            Cell c = row.getCell(i);

            if (c == null) {
                c = new StreamingCell(i, row.getRowNum(), true);
            }

            if (c.getColumnIndex() == COLUMN_INDEX.get("requestDate") || c.getColumnIndex() == COLUMN_INDEX.get("targetDate") || c.getColumnIndex() == COLUMN_INDEX.get("releasedDate")) {
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

        while (nested_list.size() < maxColNumber) {
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
    private static synchronized ECPLog createObjectFromlist(List<String> row_values, long count) {
        ECPLog ecp_object = new ECPLog();
        ecp_object.set_id(count + 1);
        /*
        Make sure below spelling matches with header names in excel sheet.
        Example - COLUMN_INDEX.get("cramerVersion")
        */
        ecp_object.setCramerVersion(row_values.get(COLUMN_INDEX.get("cramerVersion")));
        ecp_object.setIsPreRequisite(row_values.get(COLUMN_INDEX.get("isPreRequisite")));
        ecp_object.setPrereqForLatestEcp(row_values.get(COLUMN_INDEX.get("prereqForLatestEcp")));
        ecp_object.setLatestEcp(row_values.get(COLUMN_INDEX.get("latestEcp")));
        ecp_object.setEcpNo(row_values.get(COLUMN_INDEX.get("ecpNo")));
        if ((row_values.get(COLUMN_INDEX.get("latestEcp")).equalsIgnoreCase(row_values.get(COLUMN_INDEX.get("ecpNo"))))) {
            ecp_object.setIsThisLatestHF("TRUE");
        } else {
            ecp_object.setIsThisLatestHF("FALSE");
        }
        ecp_object.setSequence(row_values.get(COLUMN_INDEX.get("sequence")));
        ecp_object.setOrNo(row_values.get(COLUMN_INDEX.get("orNo")));
        ecp_object.setDescription(row_values.get(COLUMN_INDEX.get("description")));
        ecp_object.setStatus(row_values.get(COLUMN_INDEX.get("status")));
        ecp_object.setFixedBy(row_values.get(COLUMN_INDEX.get("fixedBy")));
        ecp_object.setModule(row_values.get(COLUMN_INDEX.get("module")));
        ecp_object.setVersion(row_values.get(COLUMN_INDEX.get("version")));
        ecp_object.setCaseOrCrNo(row_values.get(COLUMN_INDEX.get("caseOrCrNo")));
        ecp_object.setRequestor(row_values.get(COLUMN_INDEX.get("requestor")));
        ecp_object.setFilesModifiedInPerforce(row_values.get(COLUMN_INDEX.get("filesModifiedInPerforce")));
        ecp_object.setFileLocationInPerforce(row_values.get(COLUMN_INDEX.get("fileLocationInPerforce")));
        ecp_object.setFilesReleasedToCustomer(row_values.get(COLUMN_INDEX.get("filesReleasedToCustomer")));
        ecp_object.setType(row_values.get(COLUMN_INDEX.get("type")));
        ecp_object.setNotes(row_values.get(COLUMN_INDEX.get("notes")));
        ecp_object.setDownloadCenter(row_values.get(COLUMN_INDEX.get("downloadCenter")));
        ecp_object.setEcpReplaced(row_values.get(COLUMN_INDEX.get("ecpReplaced")));
        ecp_object.setAdditionalInfo(row_values.get(COLUMN_INDEX.get("additionalInfo")));
        ecp_object.setFixRolledIntoModule(row_values.get(COLUMN_INDEX.get("fixRolledIntoModule")));
        ecp_object.setRolledIntoVersion(row_values.get(COLUMN_INDEX.get("rolledIntoVersion")));
        ecp_object.setRollupCr(row_values.get(COLUMN_INDEX.get("rollupCr")));
        ecp_object.setEscapingDefect(row_values.get(COLUMN_INDEX.get("escapingDefect")));
        ecp_object.setReportingVersion(row_values.get(COLUMN_INDEX.get("reportingVersion")));
        ecp_object.setOriginalIssue(row_values.get(COLUMN_INDEX.get("originalIssue")));
        ecp_object.setAddedToExtranet(row_values.get(COLUMN_INDEX.get("addedToExtranet")));
        ecp_object.setAddedToExtranetUpdate(row_values.get(COLUMN_INDEX.get("addedToExtranetUpdate")));
        ecp_object.setAddedToPatchBundle(row_values.get(COLUMN_INDEX.get("addedToPatchBundle")));
        ecp_object.setHfNotBuiltSep(row_values.get(COLUMN_INDEX.get("hfNotBuiltSep")));
        ecp_object.setC4IssueAlso(row_values.get(COLUMN_INDEX.get("c4IssueAlso")));
        ecp_object.setC5IssueAlso(row_values.get(COLUMN_INDEX.get("c5IssueAlso")));
        ecp_object.setMissingBasicFunc(row_values.get(COLUMN_INDEX.get("missingBasicFunc")));
        ecp_object.setNewComponent(row_values.get(COLUMN_INDEX.get("newComponent")));
        ecp_object.setCausedByNewComp(row_values.get(COLUMN_INDEX.get("causedByNewComp")));
        ecp_object.setPlatformIssue(row_values.get(COLUMN_INDEX.get("platformIssue")));
        ecp_object.setPerfIssue(row_values.get(COLUMN_INDEX.get("perfIssue")));
        ecp_object.setUpgradeIssue(row_values.get(COLUMN_INDEX.get("upgradeIssue")));
        ecp_object.setNewFuncAdded(row_values.get(COLUMN_INDEX.get("newFuncAdded")));
        ecp_object.setMandatoryEcp(row_values.get(COLUMN_INDEX.get("mandatoryEcp")));
        ecp_object.setSpecificFunc(row_values.get(COLUMN_INDEX.get("specificFunc")));
        ecp_object.setMultiModulesAffected(row_values.get(COLUMN_INDEX.get("multiModulesAffected")));
        ecp_object.setSeverity(row_values.get(COLUMN_INDEX.get("severity")));
        ecp_object.setPriority(row_values.get(COLUMN_INDEX.get("priority")));
        ecp_object.setEcpFaulty(row_values.get(COLUMN_INDEX.get("ecpFaulty")));
        ecp_object.setHfRolllupInfo(row_values.get(COLUMN_INDEX.get("hfRolllupInfo")));

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");


        try {
            ecp_object.setRequestDate(new SimpleDateFormat("dd-MMM-yyyy").parse(row_values.get(COLUMN_INDEX.get("requestDate"))));
        } catch (ParseException e) {
            ecp_object.setRequestDate(new Date(0000, 00, 00));
        }
        try {
            ecp_object.setTargetDate(new SimpleDateFormat("dd-MMM-yyyy").parse(row_values.get(COLUMN_INDEX.get("targetDate"))));
        } catch (ParseException e) {
            ecp_object.setTargetDate(new Date(0000, 00, 00));
        }
        try {
            ecp_object.setReleasedDate(new SimpleDateFormat("dd-MMM-yyyy").parse(row_values.get(COLUMN_INDEX.get("releasedDate"))));
        } catch (ParseException e) {
            ecp_object.setReleasedDate(new Date(0000, 00, 00));
        }


        return ecp_object;
    }

    public synchronized void initializeColumnIndexes(Row headRow) {
        maxColNumber = headRow.getLastCellNum();
        for (int i = 0; i < maxColNumber; i++) {
            String str = null;
            try {
                str = headRow.getCell(i).getStringCellValue();
            } catch (NullPointerException ex) {
                str = "xxx";
            }

            COLUMN_INDEX.put(str, i);
        }
    }

    /**
     * Method for merging Excel data into Database.
     *
     * @return void
     * @throws IOException
     */
    public synchronized long mergeExcelDataToDB() throws IOException {
        long total_records = 0;
        InputStream fis = null;
        Workbook workbook = null;
        Sheet sheet;
        try {

            int startRow = 7;

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

                if (r.getRowNum() >= startRow && r.getRowNum() < endRow) {
                    ECPLog ecplog = new ECPLog();

                    row_values = getListFromRow(r);
                    ecplog = createObjectFromlist(row_values, total_records);

                    ecpService.save(ecplog);
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

    /**
     * Method for getting last row number in ECPLog.
     *
     * @return Last row number.
     * @throws IOException
     */
    private synchronized int getLastRowNum() throws IOException {
        int last_num = 0;
        boolean flag = true;
        int start_row = 7;
        InputStream fis = null;
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            File file = new File(excelFilePath);
            fis = new FileInputStream(file);
            workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(fis);
            //sheet = workbook.getSheetAt(2);
            sheet = workbook.getSheet(sheetname);


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
                        if (!flag)
                            break;
                    }
                    if (!flag) {
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
