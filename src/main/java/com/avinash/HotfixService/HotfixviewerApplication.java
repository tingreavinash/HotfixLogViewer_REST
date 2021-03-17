/**
 * Spring Boot Main class for starting the application.
 *
 * @author Avinash Tingre
 */

package com.avinash.HotfixService;

import com.avinash.HotfixService.Controller.EcpLogController;
import com.avinash.HotfixService.Model.ECPLog;
import com.avinash.HotfixService.Model.HotfixSummary;
import com.avinash.HotfixService.Service.ECPFileHandler;
import com.avinash.HotfixService.Service.ECPLogService;
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
    public static List<String> distinctVersion = new ArrayList<String>();
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

    @Value("${app.use_sample_data}")
    Boolean isLoadSampleData;

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
        ecpService.deleteAll();
        LOG.info("Old records deleted");

        File file = resource.getFile();
        String hfRecords = new String(Files.readAllBytes(file.toPath()));

        ECPLog[] arr = objectMapper.readValue(hfRecords, ECPLog[].class);
        List<ECPLog> ecpObjects = Arrays.asList(arr);
        List<ECPLog> result = ecpService.saveAll(ecpObjects);
        LOG.info("Total records inserted: " + result.size());
    }

    /**
     * This method runs immediately after starting spring boot app.
     * It will delete old records from Database and will add all new records from excel into DB.
     */

    @Override
    public void run(String... args) throws IOException, CloneNotSupportedException {
        LOG.info("============ Hotfix Application Started ============");

        if (isLoadSampleData) {
            loadSampleData();

        } else {

            long total_records_inserted = ecpHandler.mergeExcelDataToDB();
            LOG.info("Total records inserted: " + total_records_inserted + "\n");

            LOG.info("====== Database Summary ======");
            HotfixSummary hfSummary = ecpController.getDatabaseSummary();
            LOG.info("Total hotfixes in DB: " + hfSummary.getTotalHotfixes());
            LOG.info("Newly added hotfixes: " + hfSummary.getNewlyAddedHotfixes());
        }

        Set<String> version_set = ecpService.getDistinctVersions();
        Set<String> module_set = ecpService.getDistinctModules();

        distinctVersion.addAll(version_set);
        distinctModules.addAll(module_set);

    }

}
