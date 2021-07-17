package com.upgrade.tests;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.upgrade.ui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import java.util.Locale;
import static com.framework.AutomationContext.getContext;

public class JunitUITests {
    WebDriver driver;
    private static Logger logger = LogManager.getLogger(JunitUITests.class);

    @BeforeEach
    public void setUp(){
        driver = getContext().getWebDriver();
    }

    @DisplayName(value = "A new borrower can provide required information and generate an offer for loan")
    @Test
    public void testNewBorrowerLoanOffer(){

        //Launch borrower site - https://www.credify.tech/phone/nonDMFunnel
        driver.get(System.getProperty("landingPage"));
        BasePage page = new LandingPage();

        //Enter loan information (amount, purpose)
        page.getObject("loanAmount").setText("2,000");
        page.getObject("loanPurpose").selectValue("Home Improvement");
        page.getObject("checkYourRate").click();

        //Generate random personal data for borrower
        Faker faker = new Faker(new Locale("en-US"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.bothify("candidate#####@upgrade-challenge.com");
        Address address = faker.address();
        String streetAddress = address.streetAddress();
        String city = address.city();
        String state = address.stateAbbr();
        String zipCode = address.zipCode().split("-")[0];
        String password = faker.bothify("?#?#?#?") + faker.bothify("?", true);
        logger.info("Email address: " + email + "\nPassword: " + password);

        page = new BasicInformationPage();
        //Enter borrower personal information
        page.getObject("firstName").setText(firstName);
        page.getObject("lastName").setText(lastName);
        page.getObject("homeAddress").setText(streetAddress);

        //Based on random address generated, address auto-complete may not work. In that case
        //enter the city, state and zip code
        if(page.getObject("firstAddressInList").waitForElement(1)) {
            logger.info("Selecting autocomplete address: " + page.getObject("firstAddressInList").getText());
            page.getObject("firstAddressInList").click();
        }else{
            page.getObject("city").setText(city);
            page.getObject("state").setText(state);
            page.getObject("zipCode").setText(zipCode);
        }
        page.getObject("dob").setText("01011980");
        page.getObject("continueInfo").click();

        //Based on the state picked, loan amount may have to be altered to the recommended amount to proceed
        if(page.getObject("changeAmountNotification").waitForElement(1)){
            String notification = page.getObject("changeAmountNotification").getText();
            String textRemovedNumbers = notification.replaceAll("[A-Za-z$, ]*", "");
            String newAmount = textRemovedNumbers.substring(0, textRemovedNumbers.indexOf("."));
            page.getObject("changeAmount").setText(newAmount);
            page.getObject("updateAmount").click();
        }

        //Enter income information for borrower
        page.getObject("annualIncome").setText("120,000");
        page.getObject("additionalIncome").setText("5,000");
        page.getObject("continueInfo").click();

        //It seems there is an issue causing need to click the continue button twic; first time to remove
        // focus from AdditionalIncome field and the clicking continue. there is a better solution too though
        page.getObject("continueInfo").click();

        //Enter borrower login information
        page.getObject("emailAddress").setText(email);

        //Try to create account with incorrect password
        page.getObject("password").setText("12345678");
        page.getObject("termsOfUse").click();
        page.getObject("checkYourRate").click();

        //Check if password combination not match error appeared
        Assertions.assertTrue(page.getObject("passwordError").isDisplayed(),
                "Incorrect password combination error did not appear");

        //Enter new password with right combination
        page.getObject("password").setText(password);
        page.getObject("termsOfUse").click();
        page.getObject("checkYourRate").click();

        page = new OfferPage();
        //Wait for offer page to load and read top default offer details into data store
        page.getObject("pageLoaded").waitForElement(1);
        getContext().setTestProperty("loanAmount", page.getObject("loanAmount").getText());
        getContext().setTestProperty("monthlyPayment", page.getObject("monthlyPayment").getText());
        getContext().setTestProperty("term", page.getObject("term").getText());
        getContext().setTestProperty("interestRate", page.getObject("interestRate").getText());
        getContext().setTestProperty("apr", page.getObject("apr").getText());

        page = new MenuBarPage();
        //Logout
        page.getObject("menu").click();
        page.getObject("logout").click();

        driver.get(System.getProperty("loginPage"));
        page = new LoginPage();
        //Launch login page using email address and password created
        page.getObject("emailAddress").setText(email);
        page.getObject("password").setText(password);
        page.getObject("signIn").click();

        page = new OfferPage();
        page.getObject("pageLoaded").waitForElement(10);
        Assertions.assertTrue(driver.getCurrentUrl().contains("/offer-page"), "The landing page after login was " +
                "expected to be /offer-page but actual page was " + driver.getCurrentUrl());

        //Test if the loan offer information from data store is still there on offers page
        String loanAmount = page.getObject("loanAmount").getText();
        String monthlyPayment = page.getObject("monthlyPayment").getText();
        String term = page.getObject("term").getText();
        String interestRate = page.getObject("interestRate").getText();
        String apr = page.getObject("apr").getText();

        SoftAssertions softAssert = new SoftAssertions();
        softAssert.assertThat(loanAmount).isEqualTo(getContext().getTestProperty("loanAmount"));
        softAssert.assertThat(monthlyPayment).isEqualTo(getContext().getTestProperty("monthlyPayment"));
        softAssert.assertThat(term).isEqualTo(getContext().getTestProperty("term"));
        softAssert.assertThat(interestRate).isEqualTo(getContext().getTestProperty("interestRate"));
        softAssert.assertThat(apr).isEqualTo(getContext().getTestProperty("apr"));
        softAssert.assertAll();
    }

    @AfterEach
    public void tearDown(){
        driver.close();
    }
}
