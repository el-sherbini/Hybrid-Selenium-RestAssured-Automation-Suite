package ui.utils;

import config.ConfigManager;
import config.ConfigType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.driver.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public final class ElementActions {
    private static final Logger logger = LoggerFactory.getLogger(ElementActions.class);

    private static final WebDriver driver = DriverFactory.getDriver(ConfigManager.getProperty(ConfigType.UI, "BROWSER"));
    private static final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    public static void openURL(String url) {
        logger.info("Opening URL: {}", url);
        driver.get(url);
    }

    private static void waitForClickable(By locator) {
        try {
            logger.debug("Waiting for element to be clickable: {}", locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            logger.debug("Element is clickable: {}", locator);
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for element to be clickable: {}", locator);
            throw new RuntimeException("Element not clickable: " + locator, e);
        }
    }

    public static void clickElement(By locator) {
        waitForClickable(locator);
        try {
            logger.debug("Clicking on element: {}", locator);

            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.debug("Scrolled element into view: {}", locator);

            try {
                element.click();
                logger.info("Clicked element normally: {}", locator);
            } catch (Exception clickException) {
                logger.warn("Normal click failed, trying JS click for: {}", locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                logger.info("Clicked element via JS: {}", locator);
            }
        } catch (Exception e) {
            logger.error("Failed to click element: {}", locator, e);
            throw new RuntimeException("Failed to click element: " + locator, e);
        }
    }

    public static WebElement findElement(By locator) {
        try {
            logger.debug("Finding element presence: {}", locator);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.debug("Element found: {}", locator);
            return element;
        } catch (TimeoutException e) {
            logger.error("Element not found: {}", locator);
            throw new RuntimeException("Element not found: " + locator, e);
        }
    }

    public static List<WebElement> findElements(By locator) {
        try {
            logger.debug("Finding all elements presence: {}", locator);
            List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            logger.debug("Elements found count {} for locator: {}", elements.size(), locator);
            return elements;
        } catch (TimeoutException e) {
            logger.error("Elements not found: {}", locator);
            throw new RuntimeException("Elements not found: " + locator, e);
        }
    }

    public static WebElement waitForVisibility(By locator) {
        try {
            logger.debug("Waiting for visibility of element: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.debug("Element visible: {}", locator);
            return element;
        } catch (TimeoutException e) {
            logger.error("Element not visible: {}", locator);
            throw new RuntimeException("Element not visible: " + locator, e);
        }
    }

    public static String getTextFromElement(By locator) {
        try {
            logger.debug("Getting text from element: {}", locator);
            WebElement element = waitForVisibility(locator);
            String text = element.getText();
            logger.info("Got text '{}' from element: {}", text, locator);
            return text;
        } catch (TimeoutException e) {
            logger.error("Failed to get text from element: {}", locator);
            throw new RuntimeException("Failed to get text from " + locator, e);
        }
    }

    public static void sendTextToElement(By locator, String text) {
        try {
            logger.debug("Sending text '{}' to element: {}", text, locator);
            waitForVisibility(locator).sendKeys(text);
            logger.info("Text '{}' sent to element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to send keys '{}' to element: {}", text, locator, e);
            throw new RuntimeException("Failed to send keys to element: " + locator, e);
        }
    }

    public static void selectFromDropdownByVisibleText(By locator, String visibleText) {
        logger.debug("Selecting '{}' from dropdown: {}", visibleText, locator);
        WebElement dropdown = findElement(locator);
        String tagName = dropdown.getTagName();

        if (!tagName.equalsIgnoreCase("select")) {
            logger.error("Element is not a <select> tag. Found: <{}>", tagName);
            throw new RuntimeException("Element is not a <select> tag. Found: <" + tagName + ">");
        }

        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
        logger.info("Selected '{}' from dropdown: {}", visibleText, locator);
    }

    public static void sendTextWithDelay(By locator, String text, long delayInMillis) {
        try {
            logger.debug("Sending text '{}' to element {} with delay {} ms", text, locator, delayInMillis);
            WebElement element = findElement(locator);
            Thread.sleep(delayInMillis);
            element.sendKeys(text);
            Thread.sleep(delayInMillis);
            element.sendKeys(Keys.TAB);
            logger.info("Sent text '{}' to element {} with delay", text, locator);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted during delay when sending text to element: {}", locator, e);
            throw new RuntimeException("Thread was interrupted during sleep", e);
        } catch (Exception e) {
            logger.error("Failed to send text with delay to element: {}", locator, e);
            throw new RuntimeException("Failed to send text with delay to element: " + locator, e);
        }
    }
}
