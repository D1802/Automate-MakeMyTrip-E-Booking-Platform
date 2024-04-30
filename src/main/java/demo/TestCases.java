package demo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bouncycastle.asn1.eac.Flags;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait of Time 30 Seconds
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public void testCase01() {
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.google.com");
        // Navigeta to MakeMyTrip Page
        WebElement searchBar = driver.findElement(By.xpath("//textarea[@aria-label='Search']"));
        searchBar.sendKeys("makemytrip.");
        WebElement googleSearchButton = driver
                .findElement(By.xpath("//div[@class='FPdoLc lJ9FBc']/center/input[@value='Google Search']"));
        googleSearchButton.click();

        WebElement webSite = driver.findElement(By.xpath("(//div/span[text()='MakeMyTrip'])[1]"));
        webSite.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlContains("makemytrip"));

        if (driver.getCurrentUrl().contains("makemytrip"))
            System.out.println("The URL of the Make My Trip homepage contains \"makemytrip.\"");
        else
            System.out.println("Having an Execption to Load the WebSite");

        System.out.println("end Test case: testCase01");
    }

    public void testCase02() throws InterruptedException {
        System.out.println("Start Test case: testCase02");
        //driver.get("https://www.makemytrip.com/");
        driver.get("https://www.google.com");
        // Navigeta to MakeMyTrip Page
        WebElement searchBar = driver.findElement(By.xpath("//textarea[@aria-label='Search']"));
        searchBar.sendKeys("makemytrip.");
        searchBar.sendKeys(Keys.ENTER);

        
        WebElement webSite = driver.findElement(By.xpath("(//div/span[text()='MakeMyTrip'])[1]"));
        webSite.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // // Wait for the iframe containing the advertisement
        WebElement advertisementFrame = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@title,'notification')]")));
        System.out.println(advertisementFrame.isDisplayed());
        // Switch to the iframe
        driver.switchTo().frame(advertisementFrame);
        WebElement close = driver.findElement(By.xpath("//a[contains(@id,'close')]"));
        //System.out.println(close.isDisplayed());
        if (close.isDisplayed())
            close.click();

        WebElement advertisment2 = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@class= 'imageSlideContainer']/section")));
        WebElement closethe = driver.findElement(By.xpath("//div/section/span[@data-cy='closeModal']"));
        //System.out.println(advertisment2.isDisplayed());
        if (advertisment2.isDisplayed())
            closethe.click();

        WebElement dropDownForm = driver.findElement(By.xpath("//label[@for='fromCity']"));
        dropDownForm.click();

        WebElement fromCity = driver.findElement(By.xpath("//input[@placeholder='From']"));
        fromCity.sendKeys("blr");

        // Selecting the Value of DropDown same in To and From
        WebElement dropdwonValue_from = driver.findElement(By.xpath("(//ul[@role='listbox']/li//p/span)[2]"));

        if (dropdwonValue_from.getText().equals("BLR")) {
            System.out.println("Departure destination is Verified Successfully");
            dropdwonValue_from.click();
        } else
            System.out.println("Departure is not Verify");

        WebElement dropdownTo = driver.findElement(By.xpath("//label[@for='toCity']"));
        dropdownTo.click();

        WebElement toCity = driver.findElement(By.xpath("//input[@placeholder='To']"));
        toCity.sendKeys("del");

        WebElement dropdwonValue_to = driver.findElement(By.xpath("(//ul[@role='listbox']/li//p/span)[2]"));

        if (dropdwonValue_to.getText().equals("DEL")) {
            System.out.println("Arrival destination is Verified Successfully");
            dropdwonValue_to.click();
        } else
            System.out.println("Arrival is not Verify");

        WebElement departureDate = driver
                .findElement(By.xpath("//div[contains(@class,'dates')]/label/span[contains(@class,'lbl_input')]"));
        System.out.println(departureDate.isDisplayed());
        departureDate.click();

        //NextMonthDate("29");
        DateSelection("May", "29");
        
        WebElement searchButton = driver.findElement(By.xpath("//a[text()='Search']"));
        searchButton.click();

        WebElement icon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[contains(@class,'CrossIcon')]")));
        if (icon.isDisplayed())
            icon.click();

        List<WebElement> price = driver
                .findElements(By.xpath("//div[@class='priceSection priceLockPersuasionExists']/div/div"));
        List<WebElement> flightName = driver.findElements(By.xpath("//div/p[@class='boldFont blackText airlineName']"));

        for (int i = 0; i < price.size(); i++) {
            System.out.println("Flight Name::" + flightName.get(i).getText() + "  Price::" + price.get(i).getText());
        }
        System.out.println("End Test case: testCase02");
        

    }

    public void DateSelection(String month, String Day) throws InterruptedException {

        boolean flag = true;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        while (flag) {
            if (driver.findElement(By.xpath("//div[@class='DayPicker-Day' and contains(@aria-label,'" + month
                    + "') and contains(@aria-label,'" + Day + "')]")).isDisplayed()) {

                WebElement  selectDate =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='DayPicker-Day' and contains(@aria-label,'" + month+ "') and contains(@aria-label,'" + Day + "')]")));
                selectDate.click();
                flag = false;
            } else {
               
                Thread.sleep(2000);
                WebElement prev = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='DayPicker-NavBar']/span[contains(@aria-label,'Previous')]")));
                prev.click();
            }
        }

    }

    public void NextMonthDate(String Day) throws InterruptedException {

        LocalDate currentDate = LocalDate.now();
        // Extract the current month
        Month currentMonth = currentDate.getMonth();
        // Extract the next month
        Month nextMonth = currentMonth.plus(1);
        String nextMonthName = nextMonth.name();
        System.out.println(nextMonthName);

        List<WebElement> headingCalender = driver
                .findElements(By.xpath("//div[@class='DayPicker-Month']/div[@role='heading']/div"));
        System.out.println(headingCalender.size());

        List<WebElement> day = driver
                .findElements(By.xpath("//div[@class='DayPicker-Day' and contains(@aria-label,'May')]//p[1]"));
        System.out.println(day.size());
        for (WebElement heading : headingCalender) {
            System.out.println(heading.getText().toUpperCase());
            if (heading.getText().toUpperCase().contains(nextMonthName)) {
                System.out.println("Element is Found");

                for (int i = 0; i < day.size(); i++) {

                    if (day.get(i).getText().contains(Day)) {

                        System.out.println(day.get(i).getText());
                        day.get(i).click();
                        Thread.sleep(2000);

                        break;
                    }
                }
            }
        }

    }

    public void selectSoureceAndDestination(String FROM, String TO) throws InterruptedException {

        // driver.get("https://www.makemytrip.com/");
        driver.get("https://www.google.com");
        // Navigeta to MakeMyTrip Page
        WebElement searchBar = driver.findElement(By.xpath("//textarea[@aria-label='Search']"));
        searchBar.sendKeys("makemytrip.");
        searchBar.sendKeys(Keys.ENTER);
        WebElement webSite = driver.findElement(By.xpath("(//div/span[text()='MakeMyTrip'])[1]"));
        webSite.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Wait for the iframe containing the advertisement
    
    //     WebElement advertisementFrame = wait.until(
    //             ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@title,'notification')]")));
    //     if(advertisementFrame.isDisplayed()){
    //     // Switch to the iframe
    //     driver.switchTo().frame(advertisementFrame);
    //     WebElement close = driver.findElement(By.xpath("//a[contains(@id,'close')]"));
    //    // System.out.println(close.isDisplayed());
    //     if (close.isDisplayed())
    //         close.click();}

    //     WebElement advertisment2 = wait.until(ExpectedConditions
    //             .visibilityOfElementLocated(By.xpath("//div[@class= 'imageSlideContainer']/section")));
    //     if(advertisment2.isDisplayed()){
    //     WebElement closethe = driver.findElement(By.xpath("//div/section/span[@data-cy='closeModal']"));
    //    // System.out.println(advertisment2.isDisplayed());
    //     if (advertisment2.isDisplayed())
    //         closethe.click();}

        WebElement selectTrain = driver.findElement(By.xpath("//li[@data-cy='menu_Trains']"));
        selectTrain.click();
        Thread.sleep(2000);
        WebElement dropDownForm = driver.findElement(By.xpath("//label[@for='fromCity']"));
        dropDownForm.click();
    
        WebElement fromCity = driver.findElement(By.xpath("//input[@type= 'text' and @placeholder='From']"));
        fromCity.sendKeys(FROM);
        Thread.sleep(2000);
        WebElement dropdwonValue_from = driver.findElement(By.xpath("(//ul[@role='listbox']/li//span)[3]"));
        dropdwonValue_from.click();

        
        WebElement fromTo = driver.findElement(By.xpath("//input[@type= 'text' and @placeholder='To']"));
        fromTo.sendKeys(TO);
        // Wait for the dropdown value to be clickable
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[text()='"+TO.toUpperCase()+"']")));
        WebElement dropdwonValue_to = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//ul[@role='listbox']/li//div/span)[1]")));//change
        dropdwonValue_to.click();
    
        }

    public void testCase03() throws InterruptedException {
        System.out.println("Start Test case: testCase03");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        selectSoureceAndDestination("ndls", "ypr");//ypr //ndls
        DateSelection("May", "29");
       
        WebElement ddValue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='travelForPopup']/li[@data-cy='3A']")));
        ddValue.click();

        WebElement searchButton = driver.findElement(By.xpath("//a[text()='Search']"));
        searchButton.click();

        WebElement TierSelect = driver.findElement(By.xpath("//input[contains(@id,'3A')]"));
        if (!TierSelect.isSelected())
            TierSelect.click();

        List<WebElement> trainName = driver.findElements(By.xpath("//div[@class='train-name']"));
        List<WebElement> trainPrice = driver.findElements(By.xpath("//div[contains(@class,'ticket-price')]"));

        for (int i = 0; i <trainName.size(); i++) {
            System.out.println(
                // ? mark sign show that the symbols of rupees in the string
                    "Train Name: " + trainName.get(i).getText() + "Train Price: " + trainPrice.get(i).getText());
        }
        System.out.println("END Test case: testCase03");
    }


    public void busSelection(String From,String To) throws InterruptedException{
        driver.get("https://www.makemytrip.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Wait for the iframe containing the advertisement
    //     WebElement advertisementFrame = wait.until(
    //             ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@title,'notification')]")));
    //     if(advertisementFrame.isDisplayed()){
    //     // Switch to the iframe
    //     driver.switchTo().frame(advertisementFrame);
    //     WebElement close = driver.findElement(By.xpath("//a[contains(@id,'close')]"));
    //    // System.out.println(close.isDisplayed());
    //     if (close.isDisplayed())
    //         close.click();}

    //     WebElement advertisment2 = wait.until(ExpectedConditions
    //             .visibilityOfElementLocated(By.xpath("//div[@class= 'imageSlideContainer']/section")));
    //     if(advertisment2.isDisplayed()){
    //     WebElement closethe = driver.findElement(By.xpath("//div/section/span[@data-cy='closeModal']"));
    //    // System.out.println(advertisment2.isDisplayed());
    //     if (advertisment2.isDisplayed())
    //         closethe.click();}

        WebElement selectBus = driver.findElement(By.xpath("//li[@data-cy='menu_Buses']"));
        selectBus.click();

        Thread.sleep(2000);
        WebElement dropDownForm = driver.findElement(By.xpath("//label[@for='fromCity']"));
        dropDownForm.click();
    
        WebElement fromCity = driver.findElement(By.xpath("//input[@type= 'text' and @placeholder='From']"));
        fromCity.sendKeys(From);
        Thread.sleep(2000);
        WebElement dropdwonValue_from = driver.findElement(By.xpath("(//ul[@role='listbox']/li//span)[1]"));
        dropdwonValue_from.click();

    
        WebElement toCity = driver.findElement(By.xpath("//input[@placeholder='To']"));
        toCity.sendKeys(To);
    
        // Wait for the dropdown value to be clickable
        WebElement dropdwonValue_to = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']/li//p/span[contains(text(),'Nepal')]")));
        dropdwonValue_to.click();

        System.out.println("END Test case: testCase03");
    }


    public void testCase04() throws InterruptedException{
        System.out.println("END Test case: testCase04");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        busSelection("bangl", "kathma");
        DateSelection("May", "29");

        WebElement searchButton = driver.findElement(By.xpath("//button[@id='search_button']"));
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();

        if (driver.findElement(By.xpath("//div[contains(@class,'error')]/div/span[@class='error-title']")).getText().equalsIgnoreCase("No buses found for 29 May")) {
            System.out.println("The message \"No buses found\" is displayed for the specified route and date.");
        } else {
            System.out.println("Root is Disaplayed");
        }
        System.out.println("END Test case: testCase04");

    }

}
