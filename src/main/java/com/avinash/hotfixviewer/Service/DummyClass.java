package com.avinash.hotfixviewer.Service;

import com.avinash.hotfixviewer.Model.ECPLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DummyClass {

    public List<ECPLog> getDummyRecords() throws CloneNotSupportedException {

        List<ECPLog> records = new ArrayList<>();

        ECPLog obj1 = new ECPLog();
        obj1.set_id(1);
        obj1.setCramerVersion("OSS 10.2");
        obj1.setIsPreRequisite("YES");
        obj1.setPrereqForLatestEcp("XYZ 10.3.2-1000");
        obj1.setLatestEcp("XYZ 10.3.2-1500");
        obj1.setEcpNo("XYZ 10.3.2-1300");
        obj1.setIsThisLatestHF("FALSE");
        obj1.setSequence("20");
        obj1.setOrNo(null);
        obj1.setDescription("This is test description for given hotfix.");
        obj1.setStatus("Faulty");
        obj1.setFixedBy("Avinash Tingre");
        obj1.setModule("Inventory");
        obj1.setVersion("10.3");
        obj1.setCaseOrCrNo("121312-2321");
        obj1.setRequestor("Avinash Tingre");
        obj1.setFilesModifiedInPerforce("xyz.java\nabc.java");
        obj1.setFileLocationInPerforce("files1");
        obj1.setFilesReleasedToCustomer("mypkg.jar");
        obj1.setType("-");
        obj1.setNotes("Internal Notes");
        obj1.setDownloadCenter("Bitbucket");
        obj1.setEcpReplaced(null);
        obj1.setAdditionalInfo(null);
        obj1.setFixRolledIntoModule(null);
        obj1.setRolledIntoVersion("OSS 10.3.4");
        obj1.setRollupCr("24343-5345");
        obj1.setEscapingDefect(null);
        obj1.setReportingVersion("OSS 10.3.2");
        obj1.setOriginalIssue(null);
        obj1.setAddedToExtranet(null);
        obj1.setAddedToExtranetUpdate(null);
        obj1.setAddedToPatchBundle(null);
        obj1.setHfNotBuiltSep(null);
        obj1.setC4IssueAlso("no");
        obj1.setC5IssueAlso("no");
        obj1.setMissingBasicFunc(null);
        obj1.setNewComponent(null);
        obj1.setCausedByNewComp(null);
        obj1.setPlatformIssue(null);
        obj1.setPerfIssue("yes");
        obj1.setUpgradeIssue(null);
        obj1.setNewFuncAdded("search console");
        obj1.setMandatoryEcp("Yes");
        obj1.setSpecificFunc("Search console");
        obj1.setMultiModulesAffected("-");
        obj1.setSeverity("S2");
        obj1.setPriority("High");
        obj1.setEcpFaulty("No");
        obj1.setHfRolllupInfo("NA");

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            obj1.setRequestDate(new SimpleDateFormat("dd-MMM-yyyy").parse("10-Mar-2010") );
        } catch (ParseException e) {
            obj1.setRequestDate(new Date(0000, 00, 00));
        }
        try {
            obj1.setTargetDate(new SimpleDateFormat("dd-MMM-yyyy").parse("15-Mar-2010") );
        }catch (ParseException e) {
            obj1.setTargetDate(new Date(0000, 00, 00));
        }
        try {
            obj1.setReleasedDate(new SimpleDateFormat("dd-MMM-yyyy").parse("16-Mar-2010"));
        }catch (ParseException e) {
            obj1.setReleasedDate(new Date(0000, 00, 00));
        }

        records.add(obj1);

        ECPLog obj2 = (ECPLog) obj1.clone();
        obj2.set_id(2);
        obj2.setEcpNo("XYZ 10.3.2-1323");
        obj2.setDescription("Dummy description for HF 2.");

        records.add(obj2);
        return records;


    }


}
