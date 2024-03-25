package com.booking.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name = "ss")
    private WebElement searchInput;

    @FindBy(css = "button[data-testid='date-display-field-start']")
    private WebElement checkInDateButton;

    @FindBy(css = "button[data-testid='date-display-field-end']")
    private WebElement checkOutDateButton;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigate() {
        driver.get("https://www.booking.com");
        // Handle cookies popup if it appears
        try {
            if (acceptCookiesButton.isDisplayed()) {
                acceptCookiesButton.click();
            }
        } catch (Exception e) {
            // Cookies popup might not appear
        }
    }

    public void searchLocation(String location) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(location);
        try {
            Thread.sleep(1000); // Wait for autocomplete
            searchInput.sendKeys(org.openqa.selenium.Keys.ARROW_DOWN);
            searchInput.sendKeys(org.openqa.selenium.Keys.ENTER);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void selectCheckInDate(LocalDate date) {
        checkInDateButton.click();
        String dateString = formatDateForBooking(date);
        wait.until(ExpectedConditions.elementToBeClickable(
            driver.findElement(org.openqa.selenium.By.cssSelector("span[data-date='" + dateString + "']"))
        )).click();
    }

    public void selectCheckOutDate(LocalDate date) {
        String dateString = formatDateForBooking(date);
        wait.until(ExpectedConditions.elementToBeClickable(
            driver.findElement(org.openqa.selenium.By.cssSelector("span[data-date='" + dateString + "']"))
        )).click();
    }

    public void clickSearch() {
        searchButton.click();
        wait.until(ExpectedConditions.urlContains("searchresults"));
    }

    private String formatDateForBooking(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void searchHotel(String location, LocalDate checkIn, LocalDate checkOut) {
        searchLocation(location);
        selectCheckInDate(checkIn);
        selectCheckOutDate(checkOut);
        clickSearch();
    }
}

