package scripts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageobjects.CarCheckHomePage;
import pageobjects.BasePage;

public class VehicleCheckTest extends TestBase {

	@DataProvider(name = "data-provider")
	public Object[][] carInputData() {
		//reading input file to get the vehicle numbers
		String inputFile = "car_input";
		List<String> vehNums = readData(inputFile);
		int vehListNum = vehNums.size();
		Object[][] objData = new Object[vehListNum][1];
		for (int i = 0; i < vehListNum; i++) {
			objData[i][0] = vehNums.get(i);
		}
		return objData;
	}

	@Test(dataProvider = "data-provider")
	public void checkVehicle(String data) throws InterruptedException {
		CarCheckHomePage myCarChkPageObj = BasePage.carCheckHomePage();
		myCarChkPageObj.clickTaxCheckImage();
		myCarChkPageObj.clearInputRegnum();
		//expected output fetching from car_output.txt file
		Map<String, Map> expected = getExpectedVehicleList();
		myCarChkPageObj.setInputRegnum(data);
		myCarChkPageObj.clickFreeCarCheck();
		Thread.sleep(2000);
		List<WebElement> li = myCarChkPageObj.getVehicleDetails();
		System.out.println(data);
		String vNum = data.replaceAll("\\s+", "");
		Map<String, String> expectedRes = expected.get(vNum);
		Map<String, String> actualRes = new HashMap<>();
		for (WebElement we : li) {
			String str = we.getText();
			String[] st = str.split("\n");
			if (st.length <= 1) {
				myCarChkPageObj.clickAlertDisplay();
				Assert.assertFalse(true, "vehicle details are not displayed for this registration number: " + data);
				break;
			}
			actualRes.put(st[0], st[1]);
		}
		validateVehicleDetails(actualRes, expectedRes);
		myCarChkPageObj.clickTaxCheckImage();
		myCarChkPageObj.clearInputRegnum();
	}

	// validate vehicle details
	public void validateVehicleDetails(Map<String, String> actualRes, Map<String, String> expectedRes) {
		SoftAssert softAssertion = new SoftAssert();
		softAssertion.assertEquals(actualRes.get("Model"), expectedRes.get("Model"), "Model mismatch");
		softAssertion.assertEquals(actualRes.get("Colour"), expectedRes.get("Colour"), "Colour is not same");
		softAssertion.assertEquals(actualRes.get("Year"), expectedRes.get("Year"), "Year is not same");
		softAssertion.assertEquals(actualRes.get("Make"), expectedRes.get("Make"), "Make is not same");
		softAssertion.assertEquals(actualRes.get("Registration"), expectedRes.get("Registration"),
				"displayed Registration number is not same");
		softAssertion.assertAll();
	}

}
