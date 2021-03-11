/**
 * Spring Boot Main class for starting the application.
 *
 * @author Avinash Tingre
 */

package com.avinash.hotfixviewer;

import com.avinash.hotfixviewer.Controller.EcpLogController;
import com.avinash.hotfixviewer.Model.ECPLog;
import com.avinash.hotfixviewer.Model.HotfixSummary;
import com.avinash.hotfixviewer.Service.ECPFileHandler;
import com.avinash.hotfixviewer.Service.ECPLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@SpringBootApplication
public class HotfixviewerApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(HotfixviewerApplication.class);
    public static List<String> distinctCramerVersion = new ArrayList<String>();
    public static List<String> distinctModules = new ArrayList<String>();

    @Autowired
    ECPLogService ecpService;
    @Autowired
    ECPFileHandler ecpHandler;
    @Autowired
    EcpLogController ecpController;

    @Value("classpath:data/SampleHotfixData.json")
    Resource resource;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${app.load_data_from_file}")
    Boolean isLoadDataFromFileEnabled;

    public static void main(String[] args) {
        SpringApplication.run(HotfixviewerApplication.class, args);

    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption, @Value("${application-version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Hotfix Log Viewer - Backend")
                        .version(appVersion)
                        .description(appDesciption)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    private void loadSampleData() throws IOException {
        ecpService.deleteAllRecords();
        LOG.info("Old records deleted");

        File file = resource.getFile();
        String hfRecords = new String(Files.readAllBytes(file.toPath()));

        ECPLog[] arr = objectMapper.readValue(hfRecords, ECPLog[].class);
        List<ECPLog> ecpObjects = Arrays.asList(arr);
        List<ECPLog> result = ecpService.addAllECPRecords(ecpObjects);
        LOG.info("Total records inserted: " + result.size());
    }

    /**
     * This method runs immediately after starting spring boot app.
     * It will delete old records from Database and will add all new records from excel into DB.
     */

    @Override
    public void run(String... args) throws IOException, CloneNotSupportedException {
        LOG.info("\n\n\n");
        LOG.info("============ Hotfix Application Started ============");

        if (isLoadDataFromFileEnabled) {
            long total_records_inserted = ecpHandler.mergeExcelDataToDB();
            if (total_records_inserted != 0) {
                LOG.info("Total records inserted: " + total_records_inserted + "\n");
            } else {
                LOG.warn("Operation failed.");
            }
            LOG.info("====== Database Summary ======");
            HotfixSummary hfSummary = ecpController.getDatabaseSummary();
            LOG.info("Total hotfixes in DB: " + hfSummary.getTotalHotfixes());
            LOG.info("Newly added hotfixes: " + hfSummary.getNewlyAddedHotfixes());

        } else {
            loadSampleData();
        }

        Map<Set<String>, Set<String>> map = ecpService.getDistinctValues();

        Set<String> version_set = null;
        Set<String> module_set = null;
        for (Map.Entry<Set<String>, Set<String>> entry : map.entrySet()) {
            version_set = entry.getKey();
            module_set = entry.getValue();
        }
        if (version_set != null && module_set != null) {
            distinctCramerVersion.addAll(version_set);
            distinctModules.addAll(module_set);
        }


    }

}
