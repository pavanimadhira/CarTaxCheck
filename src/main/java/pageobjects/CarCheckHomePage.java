package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CarCheckHomePage {

	public CarCheckHomePage() {	}

	@FindBy(xpath = "//header/a[1]/img[1]")
	private WebElement carTaxCheckImage;

	@FindBy(xpath = "//input[@id='vrm-input']")
	private WebElement inputRegnum;

	@FindBy(xpath = "//button[contains(text(),'Free Car Check')]")
	private WebElement freeCarCheck;

	@FindBy(xpath = "//div[@id='m']/div[2]/div[5]/div[1]/div[1]/span[1]/div[2]/dl")
	private List<WebElement> vehicledetails;

	@FindBy(xpath = "//a[contains(text(),'Try Again')]")
	private WebElement alertDiaplayed;

	public void clickTaxCheckImage() {

		carTaxCheckImage.click();

	}

	public void setInputRegnum(String regNum) {

		inputRegnum.sendKeys(regNum);

	}

	public void clearInputRegnum() {

		inputRegnum.clear();

	}

	public void clickFreeCarCheck() {

		freeCarCheck.click();

	}

	public List<WebElement> getVehicleDetails() {

		return vehicledetails;
	}

	public void clickAlertDisplay() {
		alertDiaplayed.click();
	}

}
