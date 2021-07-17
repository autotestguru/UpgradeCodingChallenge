package com.upgrade.ui.pages;

import com.framework.BaseException;
import com.framework.ComponentActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

public class OfferPage extends ComponentActions implements BasePage {

    @FindBy(xpath = "//span[@data-auto='userLoanAmount']")
    private WebElement loanAmount;

    @FindBy(xpath = "//span[@data-auto='defaultMonthlyPayment']")
    private WebElement monthlyPayment;

    @FindBy(xpath = "//div[@data-auto='defaultLoanTerm']")
    private WebElement term;

    @FindBy(xpath = "//div[@data-auto='defaultLoanInterestRate']")
    private WebElement interestRate;

    @FindBy(xpath = "//div[@data-auto='defaultMoreInfoAPR']/div")
    private WebElement apr;

    @FindBy(xpath = "//div[@data-fetching='false']")
    private WebElement pageLoaded;

    public ComponentActions getObject(String fieldName){
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            return super.getObject(field);
        }catch (NoSuchFieldException e) {
            throw new BaseException("Element " + fieldName + " was not found in " + this.getClass() + " class");
        }
    }
}
