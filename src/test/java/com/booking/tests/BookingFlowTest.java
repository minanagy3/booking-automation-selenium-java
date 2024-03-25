package com.booking.tests;

import com.booking.pages.*;
import com.booking.utils.ExcelDataProvider;
import com.booking.utils.DateHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import java.io.File;
import java.time.LocalDate;

public class BookingFlowTest {
    private WebDriver driver;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private HotelDetailsPage hotelDetailsPage;
    private ReservationPage reservationPage;
    private ExcelDataProvider.TestData testData;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @BeforeClass
    public void setUp() {
        // Setup WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
    }

    @BeforeMethod
    public void loadTestData() {
        try {
            String excelPath = System.getProperty("user.dir") + 
                File.separator + "data" + File.separator + "test-data.xlsx";
            ExcelDataProvider dataProvider = new ExcelDataProvider(excelPath);
            testData = dataProvider.getTestData(1); // Row 1 (assuming row 0 is header)
            dataProvider.close();

            // Calculate dates if not provided in Excel
            if (testData.getCheckInDate() != null && !testData.getCheckInDate().isEmpty()) {
                checkInDate = ExcelDataProvider.parseDate(testData.getCheckInDate());
            } else {
                checkInDate = ExcelDataProvider.calculateCheckInDate();
            }

            if (testData.getCheckOutDate() != null && !testData.getCheckOutDate().isEmpty()) {
                checkOutDate = ExcelDataProvider.parseDate(testData.getCheckOutDate());
            } else {
                checkOutDate = ExcelDataProvider.calculateCheckOutDate(checkInDate);
            }

            // Initialize page objects
            homePage = new HomePage(driver);
            searchResultsPage = new SearchResultsPage(driver);
            hotelDetailsPage = new HotelDetailsPage(driver);
            reservationPage = new ReservationPage(driver);
        } catch (Exception e) {
            e.printStackTrace();
            // Use default values if Excel fails
            checkInDate = ExcelDataProvider.calculateCheckInDate();
            checkOutDate = ExcelDataProvider.calculateCheckOutDate(checkInDate);
            testData = new ExcelDataProvider.TestData("Alexandria", "", "");
        }
    }

    @Test(priority = 1, description = "Complete booking flow - Search, Select Hotel, and Reserve")
    public void testCompleteBookingFlow() {
        // Step 1: Navigate to booking.com
        homePage.navigate();

        // Step 2: Search for location, select dates, and click search
        String location = testData.getLocation() != null && !testData.getLocation().isEmpty() 
            ? testData.getLocation() : "Alexandria";
        homePage.searchHotel(location, checkInDate, checkOutDate);

        // Step 3: Wait for search results and find Tolip Hotel
        searchResultsPage.waitForResults();
        searchResultsPage.findAndClickTolipHotel();

        // Step 4: Select bed and amount, then click reserve
        hotelDetailsPage.waitForPageLoad();
        hotelDetailsPage.selectBedAndAmount();
        hotelDetailsPage.clickReserveButton();

        // Step 5: Wait for reservation page
        reservationPage.waitForPageLoad();
    }

    @Test(priority = 2, description = "Verify check-in and check-out dates in details page")
    public void testVerifyDatesInDetailsPage() {
        // Navigate and search
        homePage.navigate();
        String location = testData.getLocation() != null && !testData.getLocation().isEmpty() 
            ? testData.getLocation() : "Alexandria";
        homePage.searchHotel(location, checkInDate, checkOutDate);

        // Find and click Tolip Hotel
        searchResultsPage.waitForResults();
        searchResultsPage.findAndClickTolipHotel();

        // Wait for details page
        hotelDetailsPage.waitForPageLoad();

        // Get displayed dates
        String displayedCheckIn = hotelDetailsPage.getCheckInDate();
        String displayedCheckOut = hotelDetailsPage.getCheckOutDate();

        // Assert dates are displayed correctly
        org.testng.Assert.assertNotNull(displayedCheckIn, "Check-in date should be displayed");
        org.testng.Assert.assertNotNull(displayedCheckOut, "Check-out date should be displayed");
        
        // Verify the dates contain the correct day/month/year
        org.testng.Assert.assertTrue(
            DateHelper.containsDate(displayedCheckIn, checkInDate),
            "Check-in date should contain the correct date"
        );
        org.testng.Assert.assertTrue(
            DateHelper.containsDate(displayedCheckOut, checkOutDate),
            "Check-out date should contain the correct date"
        );
    }

    @Test(priority = 3, description = "Verify hotel name in reservation page")
    public void testVerifyHotelNameInReservationPage() {
        // Navigate and search
        homePage.navigate();
        String location = testData.getLocation() != null && !testData.getLocation().isEmpty() 
            ? testData.getLocation() : "Alexandria";
        homePage.searchHotel(location, checkInDate, checkOutDate);

        // Find and click Tolip Hotel
        searchResultsPage.waitForResults();
        searchResultsPage.findAndClickTolipHotel();

        // Select bed and amount, then click reserve
        hotelDetailsPage.waitForPageLoad();
        hotelDetailsPage.selectBedAndAmount();
        hotelDetailsPage.clickReserveButton();

        // Wait for reservation page
        reservationPage.waitForPageLoad();

        // Verify hotel name
        String hotelName = reservationPage.getHotelName();
        org.testng.Assert.assertTrue(
            hotelName.contains("Tolip Hotel Alexandria"),
            "Hotel name should contain 'Tolip Hotel Alexandria'"
        );
    }

    @AfterMethod
    public void tearDownMethod() {
        // Optional: Take screenshot on failure
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

