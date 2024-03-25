package com.booking.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "[data-testid='property-card']")
    private List<WebElement> hotelCards;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void waitForResults() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("[data-testid='property-card']")
        ));
    }

    public void findAndClickTolipHotel() {
        String hotelName = "Tolip Hotel Alexandria";
        boolean found = false;

        // Try to find on first page
        waitForResults();
        for (WebElement card : hotelCards) {
            String text = card.getText();
            if (text.contains("Tolip Hotel Alexandria")) {
                WebElement seeAvailabilityLink = card.findElement(
                    By.xpath(".//a[contains(text(), 'See availability')]")
                );
                seeAvailabilityLink.click();
                found = true;
                break;
            }
        }

        // If not found on first page, go to second page
        if (!found) {
            try {
                WebElement nextButton = driver.findElement(
                    By.cssSelector("button[aria-label='Next page']")
                );
                nextButton.click();
                Thread.sleep(2000);
                waitForResults();
                
                hotelCards = driver.findElements(By.cssSelector("[data-testid='property-card']"));
                for (WebElement card : hotelCards) {
                    String text = card.getText();
                    if (text.contains("Tolip Hotel Alexandria")) {
                        WebElement seeAvailabilityLink = card.findElement(
                            By.xpath(".//a[contains(text(), 'See availability')]")
                        );
                        seeAvailabilityLink.click();
                        found = true;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!found) {
            throw new RuntimeException("Tolip Hotel Alexandria not found in search results");
        }

        wait.until(ExpectedConditions.urlContains("hotel"));
    }
}

