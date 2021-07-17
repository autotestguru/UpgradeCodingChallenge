package com.upgrade.ui.pages;

import com.framework.BaseException;
import com.framework.ComponentActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

public class MenuBarPage extends ComponentActions implements BasePage {

    @FindBy(xpath = "//label[@aria-label='Open Site Menu']")
    private WebElement menu;

    @FindBy(xpath = "//a[@href='/funnel/logout']")
    private WebElement logout;

    public ComponentActions getObject(String fieldName){
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            return super.getObject(field);
        }catch (NoSuchFieldException e) {
            throw new BaseException("Element " + fieldName + " was not found in " + this.getClass() + " class");
        }
    }
}
