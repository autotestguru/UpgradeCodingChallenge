package com.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;

/**
 * This class contains impementation of all Selenium/UI methods that maybe required to interact with page objects
 */
public class ComponentActions {

    private static Logger logger = LogManager.getLogger(ComponentActions.class);
    private WebElement element;
    private Field field;

    /**
     * This method is called in page classes where field reflection identifies page object required for next
     * step and passed into generate locator WebElement to use for further action
     * @param field - Reflection field object passed from class to have annotations read for page locator info
     * @return an instance of ComponentActions class to use to interact with Selenium methods implementation
     */
    protected ComponentActions getObject(Field field){
        this.field = field;
        if(!waitForElement(0)) {
            logger.info("Element " + field.getName() + " did not appear in default timeout: " + AutomationContext.getContext().getDefaultTimeOut());
            element = null;
        }
        return this;
    }

    /**
     * This method determines whether object is displayed on AUT UI or not
     * @return true or false depending on if locator object was displayed or not
     */
    public boolean isDisplayed(){
        if(element == null)
            return false;
        else
            return element.isDisplayed();
    }

    /**
     * This method performs click the locator object
     */
    public void click(){
        logger.info("Clicking on element: " + field.getName());
        if(element == null)
            throw new BaseException("Element " + field.getName() + " was not available on AUT to perform click operation");
        element.click();
    }

    /**
     * This method sets text in user field identified by locator object
     * @param text - user defined text passed by test script
     */
    public void setText(String text){
        logger.info("Setting text: " + text + " in element: " + field.getName());
        if(element == null)
            throw new BaseException("Element " + field.getName() + " was not available on AUT to set text operation");
        element.clear();
        element.sendKeys(text);
    }

    /**
     * This method is used to select a value by its displayed value in a dropdown
     * @param value - Visible text that is to be selected in dropdown
     */
    public void selectValue(String value){
        logger.info("Selecting value " + value + " from elemment: " + field.getName());
        if(element == null)
            throw new BaseException("Element " + field.getName() + " was not available on AUT to perform select operation");
        new Select(element).selectByVisibleText(value);
    }

    /**
     * This method is used to read text from locator object
     * @return Text value for text read from AUT UI
     */
    public String getText(){
        if(element == null)
            throw new BaseException("Element " + field.getName() + " was not available on AUT to read operation");
        String text = element.getText();
        logger.info("Read text: " + text + " from element: " + field.getName());
        return text;
    }

    /**
     * This method can be used to determine visibility of a locator object and wait for it if necessary.
     * @param timeOutInSeconds - timeout value to use for period of time to suspend execution while waiting
     * @return true of false depending on locator object visibility with given time out value
     */
    public boolean waitForElement(int timeOutInSeconds){
        int timeOut = timeOutInSeconds;
        if(timeOut == 0)
            timeOut = AutomationContext.getContext().getDefaultTimeOut();

        FindBy by = field.getDeclaredAnnotation(FindBy.class);
        FindBy.FindByBuilder builder = new FindBy.FindByBuilder();
        By byObj = builder.buildIt(by,field);
        WebDriver driver = WebDriverManager.getInstance();
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeOut);
        try {
            element = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(byObj));
        }catch (TimeoutException te){
            return false;
        }
        return true;
    }
}
