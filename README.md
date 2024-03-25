# Booking.com Automation Tests - Selenium Java

This project contains automated tests for Booking.com using Selenium WebDriver, TestNG, Maven, and Page Object Model (POM) design pattern.

## 📋 Requirements

- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser (latest version)

## 🚀 Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/booking-automation-selenium-java.git
   cd booking-automation-selenium-java
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

3. **Create Excel test data file:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.booking.utils.CreateExcelData"
   ```

   Or manually create `data/test-data.xlsx` with the following structure:
   - Column A: Location (e.g., "Alexandria")
   - Column B: CheckInDate (format: DD/MM/YYYY)
   - Column C: CheckOutDate (format: DD/MM/YYYY)

## 📁 Project Structure

```
booking-automation-selenium-java/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── booking/
│       │           ├── pages/          # Page Object Model classes
│       │           │   ├── HomePage.java
│       │           │   ├── SearchResultsPage.java
│       │           │   ├── HotelDetailsPage.java
│       │           │   └── ReservationPage.java
│       │           ├── tests/          # Test classes
│       │           │   └── BookingFlowTest.java
│       │           └── utils/          # Utility classes
│       │               ├── ExcelDataProvider.java
│       │               ├── DateHelper.java
│       │               └── CreateExcelData.java
│       └── resources/
│           └── testng.xml
├── data/                               # Test data files
│   └── test-data.xlsx
├── pom.xml
├── testng.xml
└── README.md
```

## 🧪 Test Cases

The project includes the following test cases:

1. **Complete booking flow** - End-to-end test covering:
   - Opening booking.com
   - Searching for Alexandria location
   - Selecting check-in (1 week from today) and check-out (4 days after check-in) dates
   - Finding and selecting Tolip Hotel Alexandria
   - Selecting bed and amount
   - Clicking "I'll reserve" button

2. **Verify check-in and check-out dates in details page** - Asserts that the chosen dates are displayed correctly on the hotel details page.

3. **Verify hotel name in reservation page** - Asserts that "Tolip Hotel Alexandria" is shown in the reservation page.

## 📊 Test Data

Test data is stored in `data/test-data.xlsx` with the following columns:
- **Location**: Search location (e.g., "Alexandria")
- **CheckInDate**: Check-in date (format: DD/MM/YYYY)
- **CheckOutDate**: Check-out date (format: DD/MM/YYYY)

If dates are not provided in Excel, the system will automatically calculate:
- Check-in: 1 week from today
- Check-out: 4 days after check-in

## 🏃 Running Tests

### Run all tests:
```bash
mvn test
```

### Run specific test class:
```bash
mvn test -Dtest=BookingFlowTest
```

### Run with TestNG XML:
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Run in IDE:
- Right-click on `testng.xml` → Run As → TestNG Suite
- Or right-click on `BookingFlowTest.java` → Run As → TestNG Test

## 🎯 Features

- ✅ Page Object Model (POM) design pattern
- ✅ Excel data provider using Apache POI
- ✅ TestNG for test execution and reporting
- ✅ WebDriverManager for automatic driver management
- ✅ Automatic date calculation
- ✅ Comprehensive test coverage
- ✅ Maven project structure

## 📝 Notes

- The tests handle dynamic content and may need selector adjustments based on Booking.com's UI changes
- Cookies popup is automatically handled
- Tests include proper waits and error handling
- WebDriverManager automatically downloads and manages ChromeDriver

## 🔧 Configuration

### Browser Configuration
Edit `BookingFlowTest.java` to change browser:
```java
// For Firefox
WebDriverManager.firefoxdriver().setup();
driver = new FirefoxDriver();

// For Edge
WebDriverManager.edgedriver().setup();
driver = new EdgeDriver();
```

### TestNG Configuration
Edit `testng.xml` to modify:
- Parallel execution
- Thread count
- Test groups
- Test priorities

## 📦 Dependencies

- **Selenium WebDriver** 4.15.0
- **TestNG** 7.8.0
- **Apache POI** 5.2.4 (for Excel)
- **WebDriverManager** 5.6.2 (for driver management)

## 📄 License

ISC

## 👤 Author

Junior QA Engineer

