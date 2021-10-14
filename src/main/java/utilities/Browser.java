package utilities;

import org.openqa.selenium.WebDriver;
import webdriver.WebDriverFactory;

import java.util.concurrent.TimeUnit;

/*
 * Browser configurations
 */
public class Browser {

    private static String baseUrl = PropertyLoader.loadProperty("baseURL");
    private static String browserName = PropertyLoader.loadProperty("browser");
    private static WebDriver driver;

    //Setting up the browser
    public static void Initialize()
    {
        driver = WebDriverFactory.getInstance(browserName);
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        navigateTo(baseUrl);
    }

    public static String getTitle()
    {
        return driver.getTitle();
    }

    public static WebDriver Driver()
    {
        return driver;
    }

    public static void navigateTo(String url)
    {
        driver.get(url);
    }

    public static void close()
    {
        driver.close();
    }
   
}
