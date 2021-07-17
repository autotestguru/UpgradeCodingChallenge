package com.upgrade.ui.pages;

import com.framework.BaseException;
import com.framework.ComponentActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

public class LoginPage extends ComponentActions implements BasePage {

    @FindBy(xpath = "//input[@data-auto='username']")
    private WebElement emailAddress;

    @FindBy(xpath = "//input[@data-auto='password']")
    private WebElement password;

    @FindBy(xpath = "//button[@data-auto='login']")
    private WebElement signIn;

    public ComponentActions getObject(String fieldName){
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            return super.getObject(field);
        }catch (NoSuchFieldException e) {
            throw new BaseException("Element " + fieldName + " was not found in " + this.getClass() + " class");
        }
    }
}
