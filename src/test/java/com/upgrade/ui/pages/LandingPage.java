package com.upgrade.ui.pages;

import com.framework.BaseException;
import com.framework.ComponentActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

public class LandingPage extends ComponentActions implements BasePage {

    @FindBy(name = "desiredAmount")
    private WebElement loanAmount;

    @FindBy(xpath = "//select[@data-auto='dropLoanPurpose']")
    private WebElement loanPurpose;

    @FindBy(xpath = "//button[@data-auto='CheckYourRate']")
    private WebElement checkYourRate;

    public ComponentActions getObject(String fieldName){
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            return super.getObject(field);
        }catch (NoSuchFieldException e) {
            throw new BaseException("Element " + fieldName + " was not found in " + this.getClass());
        }
    }
}
