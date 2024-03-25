package com.booking.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HotelDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @FindBy(css = "[data-testid='date-display-field-start']")
    private WebElement checkInDateDisplay;

    @FindBy(css = "[data-testid='date-display-field-end']")
    private WebElement checkOutDateDisplay;

    @FindBy(xpath = "//button[contains(text(), \"I'll reserve\")]")
    private WebElement reserveButton;

    public HotelDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("[data-testid='date-display-field-start']")
        ));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getCheckInDate() {
        return checkInDateDisplay.getText();
    }

    public String getCheckOutDate() {
        return checkOutDateDisplay.getText();
    }

    public void selectBedAndAmount() {
        // Try to select bed type if dropdown exists
        try {
            List<WebElement> bedDropdowns = driver.findElements(By.cssSelector("select"));
            if (!bedDropdowns.isEmpty()) {
                bedDropdowns.get(0).click();
            }
        } catch (Exception e) {
            // Bed selection might not be available
        }

        // Try to select amount if input exists
        try {
            List<WebElement> amountInputs = driver.findElements(By.cssSelector("input[type='number']"));
            if (!amountInputs.isEmpty()) {
                amountInputs.get(0).clear();
                amountInputs.get(0).sendKeys("1");
            }
        } catch (Exception e) {
            // Amount selection might not be available
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickReserveButton() {
        // Scroll to reserve button
        js.executeScript("arguments[0].scrollIntoView(true);", reserveButton);
        wait.until(ExpectedConditions.elementToBeClickable(reserveButton));
        reserveButton.click();
        wait.until(ExpectedConditions.urlContains("checkout"));
    }
}

