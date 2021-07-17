package com.upgrade.ui.pages;

import com.framework.BaseException;
import com.framework.ComponentActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

public class BasicInformationPage extends ComponentActions implements BasePage  {

    @FindBy(xpath = "//input[@data-auto='borrowerFirstName']")
    private WebElement firstName;

    @FindBy(xpath = "//input[@data-auto='borrowerLastName']")
    private WebElement lastName;

    @FindBy(xpath = "//input[@data-auto='borrowerStreet']")
    private WebElement homeAddress;

    @FindBy(xpath = "//input[@data-auto='borrowerCity']")
    private WebElement city;

    @FindBy(xpath = "//input[@data-auto='borrowerState']")
    private WebElement state;

    @FindBy(xpath = "//input[@data-auto='borrowerZipCode']")
    private WebElement zipCode;

    @FindBy(xpath = "//h2[text()='Change loan amount']/../p")
    private WebElement changeAmountNotification;

    @FindBy(xpath = "//input[@data-auto='changeLoanAmount']")
    private WebElement changeAmount;

    @FindBy(xpath = "//button[@data-auto='updateAmount']")
    private WebElement updateAmount;

    @FindBy(xpath = "(//ul[@id='geosuggest__list--borrowerStreet']/li)[1]")
    private WebElement firstAddressInList;

    @FindBy(xpath = "//input[@data-auto='borrowerDateOfBirth']")
    private WebElement dob;

    @FindBy(xpath = "//button[@data-auto='continuePersonalInfo']")
    private WebElement continueInfo;

    @FindBy(xpath = "//input[@data-auto='borrowerIncome']")
    private WebElement annualIncome;

    @FindBy(xpath = "//input[@data-auto='borrowerAdditionalIncome']")
    private WebElement additionalIncome;

    @FindBy(name = "username")
    private WebElement emailAddress;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(xpath = "(//input[@name='agreements']/../div)[1]")
    private WebElement termsOfUse;

    @FindBy(xpath = "//button[@data-auto='submitPersonalInfo']")
    private WebElement checkYourRate;

    @FindBy(xpath = "//div[@data-error='true' and contains(text(),'Password')]")
    private WebElement passwordError;


    public ComponentActions getObject(String fieldName){
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            return super.getObject(field);
        }catch (NoSuchFieldException e) {
            throw new BaseException("Element " + fieldName + " was not found in " + this.getClass() + " class");
        }
    }
}
