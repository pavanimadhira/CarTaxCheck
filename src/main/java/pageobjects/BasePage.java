package pageobjects;

import org.openqa.selenium.support.PageFactory;
import utilities.Browser;


public class BasePage
{

    public static CarCheckHomePage carCheckHomePage()
    {
        return PageFactory.initElements(Browser.Driver(), CarCheckHomePage.class);
    }
}
