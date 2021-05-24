/**
 * Spring Boot Main class for starting the application.
 *
 * @author Avinash Tingre
 */

package com.avinash.HotfixService;

import com.avinash.HotfixService.Controller.EcpLogController;
import com.avinash.HotfixService.Model.HotfixSummary;
import com.avinash.HotfixService.Service.ECPFileHandler;
import com.avinash.HotfixService.Service.ECPLogService;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@EnableScheduling
@SpringBootApplication
public class HotfixviewerApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(HotfixviewerApplication.class);
    //Duration in miliseconds
    private static final long SCHEDULE_DURATION = 32400000;
    public static List<String> distinctVersion = new ArrayList<String>();
    public static List<String> distinctModules = new ArrayList<String>();
    @Autowired
    ECPLogService ecpService;
    @Autowired
    ECPFileHandler ecpHandler;
    @Autowired
    EcpLogController ecpController;
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


    /**
     * This method runs immediately after starting spring boot app.
     * It will delete old records from Database and will add all new records from excel into DB.
     */

    @Override
    public void run(String... args) throws IOException, CloneNotSupportedException {
        LOG.info("============ Hotfix Application Started ============");
        refreshDatabase();
    }


    @Scheduled(fixedDelay = SCHEDULE_DURATION)
    public void refreshDatabase() throws IOException {
        if (isLoadSampleData) {
            ecpService.loadSampleData();

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
