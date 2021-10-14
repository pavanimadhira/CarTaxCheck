package scripts;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import utilities.Browser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TestBase {

	private static final String SCREENSHOT_FORMAT = ".png";
	private static final String SCREENSHOT_FOLDER = "target/screenshots/";

	// Initializing the browser instance
	@BeforeClass
	public void init() throws Exception {
		Browser.Initialize();
	}

	// closing browser instances
	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		if (Browser.Driver() != null) {
			Browser.Driver().quit();
		}
	}

	// Reading data from car_input.txt
	public static List<String> readData(String fileName) {

		BufferedReader bufReader = null;
		String currentLine;
		List<String> vehicleNumList = new ArrayList<String>();

		try {
			bufReader = new BufferedReader(new FileReader("inputFiles\\" + fileName + ".txt"));

			while ((currentLine = bufReader.readLine()) != null) {
				// for none or single space use \s? and more than one space \s*
				Pattern vehNumPattern = Pattern.compile("([A-Z]{2}[0-9]{2}\s?[A-Z]{3})");
				Matcher matchList = vehNumPattern.matcher(currentLine);
				while (matchList.find()) {

					System.out.println(matchList.group());
					vehicleNumList.add(matchList.group());
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeResource(bufReader);
		}
		return vehicleNumList;
	}

	// Extracting data from car_output.txt
	public static Map<String, Map> getExpectedVehicleList() {
		Map<String, Map> map = new HashMap<String, Map>();

		BufferedReader bufReader = null;
		String currentLine = null;
		List<String[]> vehicleList = new ArrayList<>();

		try {
			bufReader = new BufferedReader(new FileReader("outputFiles\\car_output.txt"));
			bufReader.readLine();
			while ((currentLine = bufReader.readLine()) != null) {
				vehicleList.add(currentLine.split(","));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeResource(bufReader);
		}
		for (String[] veh : vehicleList) {

			Map<String, String> vehMap = new HashMap<>();
			vehMap.put("Registration", veh[0]);
			vehMap.put("Make", veh[1]);
			vehMap.put("Model", veh[2]);
			vehMap.put("Colour", veh[3]);
			vehMap.put("Year", veh[4]);
			map.put(veh[0], vehMap);
		}
		return map;

	}

	public static void closeResource(BufferedReader bufReader) {
		try {
			if (bufReader != null)
				bufReader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// screenshot for failed cases
	@AfterMethod
	public void setScreenshot(ITestResult result) {
		if (!result.isSuccess()) {
			try {
				WebDriver returned = new Augmenter().augment(Browser.Driver());
				if (returned != null) {
					File f = ((TakesScreenshot) returned).getScreenshotAs(OutputType.FILE);
					try {
						FileUtils.copyFile(f, new File(SCREENSHOT_FOLDER + result.getName() + SCREENSHOT_FORMAT));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (ScreenshotException se) {
				se.printStackTrace();
			}
		}
	}
}
