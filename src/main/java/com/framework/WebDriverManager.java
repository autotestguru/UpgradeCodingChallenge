package com.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * This class is used to instantiate Webdriver object in singularity ensuring there is only one object at all times
 */
public class WebDriverManager {

    //Singleton to ensure no more than one object can be instantiated
    private WebDriverManager(){
        driver = new ChromeDriver();
    }

    //Webdriver object to contain its instance
    private static WebDriver driver;

    /**
     * Public method to use to get an instance of the Webdriver class
     * @return instance of Webdriver class
     */
    public static WebDriver getInstance(){
        if(driver==null){
            new WebDriverManager();
        }
        return driver;
    }
}
