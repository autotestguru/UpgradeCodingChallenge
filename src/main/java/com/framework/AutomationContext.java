package com.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/** Automation Context - This class collects, stores and provides information on running tests and supporting parameters
 *  Use getContext() to access context from any class.
 */
public class AutomationContext {

    private static Logger logger = LogManager.getLogger(AutomationContext.class);
    private static AutomationContext context;
    private static int defaultTimeOut;
    private static Map<String, String> testProperties;

    /**
     * Private constructor to make class a singleton
     */
    private AutomationContext(){
    }

    /** This method returns current context object
     * @return Static object of AutomationContext class
     */
    public static AutomationContext getContext() {
        if(context != null)
            return context;
        logger.info("Loading data properties in context");
        loadProperties();
        logger.info("Test execution will begin on " + System.getProperty("environment") + " environment");
        defaultTimeOut = Integer.parseInt(System.getProperty("defaultTimeOut"));
        if(testProperties==null)
            testProperties = new HashMap<>();
        if(context==null)
            context = new AutomationContext();
        return context;
    }

    /**
     * Method triggers at beginning of context loading and loads framework and environment properties
     * for use during test execution
     */
    private static void loadProperties() {
        Properties prop = new Properties();
        Properties prop1 = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream stream = loader.getResourceAsStream("fw.properties");
            prop.load(stream);
            for(Object key : prop.keySet().toArray()){
                if(!System.getProperties().containsKey(key))
                    System.setProperty(key.toString(), prop.get(key).toString());
            }
            InputStream stream1 = loader.getResourceAsStream("environments/" + System.getProperty("environment") + ".properties");
            prop1.load(stream1);
            for(Object key : prop1.keySet().toArray()){
                if(!System.getProperties().containsKey(key))
                    System.setProperty(key.toString(), prop1.get(key).toString());
            }
        } catch (Exception e) {
            throw new BaseException("An error occurred during reading the properties", e);
        }
    }

    /**
     * Get the current webdriver session
     * @return Object of type WebDriver
     */
    public WebDriver getWebDriver(){
        return WebDriverManager.getInstance();
    }

    /**
     * Get the default timeout from system properties
     * @return Current value of default timeout
     */
    public int getDefaultTimeOut(){
        return defaultTimeOut;
    }

    /**
     * This method is used to retrieve any test property in test/scenario. This can be accessed from the scenario
     * by using to the key name given during storing data
     * @param key - Name of the key
     * @return An object that has the value or list of values
     */
    public String getTestProperty(String key) {
        logger.info("Reading value for key " + key);
        String returnValue = "";
        if(testProperties.containsKey(key))
            returnValue = testProperties.get(key);
        logger.info("Key value: " + returnValue);
        return returnValue;
    }

    /**
     * Used to set a test property in test/scenario. These values are usually captured from the AUT using automation tools.
     * @param key - The key name for the stored object
     * @param value - An object that has the value or list of values
     */
    public void setTestProperty(String key, String value) {
        logger.info("Storing value " + value + " in key " + key);
        if(testProperties.containsKey(key))
            testProperties.remove(key);
        testProperties.put(key, value);
    }
}
