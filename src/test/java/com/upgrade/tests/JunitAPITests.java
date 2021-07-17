package com.upgrade.tests;

import com.framework.AutomationContext;
import com.framework.BaseException;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JunitAPITests {

    private static Logger logger = LogManager.getLogger(JunitAPITests.class);
    AutomationContext context = AutomationContext.getContext();
    RequestSpecification rs;
    /**
     * These steps are executed before each step ensuring that REST Assured is set to make API calls
     * to an endpoint
     */
    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = System.getProperty("rest.baseapi");
        RestAssured.basePath = System.getProperty("rest.basepath");
    }

    @DisplayName(value = "System can pull loan application information for correct loan application unique id")
    @Test
    public void testLoanApplicationCorrectUniqueId() {

        String correctLoanAppUuid = "b8096ec7-2150-405f-84f5-ae99864b3e96";
        String requestBody = readJsonBodyFromFile("resumeAPIBody.json");
        if(requestBody.length() == 0)
            throw new BaseException("No request body was found in file: resumeAPIBody.json");

        rs = RestAssured.given().filter(new ResponseLoggingFilter())
                .filter(new RequestLoggingFilter());
        rs.header("x-cf-source-id", "coding-challenge");
        rs.header("Content-Type", "application/json");
        rs.header("x-cf-corr-id", UUID.randomUUID().toString());
        rs.body(requestBody.replace("${loanAppUuid}", correctLoanAppUuid));

        Response response =  rs.relaxedHTTPSValidation().post().then().extract().response();
        Assertions.assertTrue(response.getStatusCode() == 200, "Failed to get correct status " +
                "code 200 from request");
        Assertions.assertTrue(response.jsonPath().getString("loanAppResumptionInfo.productType").
                equals("PERSONAL_LOAN"), "Incorrect loan application product type was returned");
    }

    @DisplayName(value = "System returns 404 NOT_FOUND error for incorrect loan application unique id")
    @Test
    public void testLoanApplicationIncorrectUniqueId(){

        String inCorrectLoanAppUuid = "b8096ec7-2150-405f-84f5-ae99864b3e99";
        String requestBody = readJsonBodyFromFile("resumeAPIBody.json");
        if(requestBody.length() == 0)
            throw new BaseException("No request body was found in file: resumeAPIBody.json");

        rs = RestAssured.given().filter(new ResponseLoggingFilter())
                .filter(new RequestLoggingFilter());
        rs.header("x-cf-source-id", "coding-challenge");
        rs.header("Content-Type", "application/json");
        rs.header("x-cf-corr-id", UUID.randomUUID().toString());
        rs.body(requestBody.replace("${loanAppUuid}", inCorrectLoanAppUuid));

        Response response =  rs.relaxedHTTPSValidation().post().then().extract().response();
        Assertions.assertTrue(response.getStatusCode() == 404, "API response code was not 400 as expected");
        Assertions.assertTrue(response.jsonPath().getString("codeName").equals("MISSING_LOAN_APPLICATION"), "Incorrect code name was returned");
    }

    /**
     * This method contains logic to read a file from json directory based on file name
     * @param fileName - Name of file to be read from json directory in test resources
     * @return - JSON string
     */
    private String readJsonBodyFromFile(String fileName){
        File jsonPayload = new File(ClassLoader.getSystemResource("json/" + fileName).getFile());
        String jsonBody = "";
        try {
            jsonBody = new String(Files.readAllBytes(jsonPayload.toPath()));
        } catch (IOException ioe) {
            logger.error("Failed to read file: " + fileName, ioe);
        }
        return jsonBody;
    }
}
