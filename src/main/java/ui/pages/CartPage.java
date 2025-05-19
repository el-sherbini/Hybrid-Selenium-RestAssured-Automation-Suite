package ui.pages;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.utils.Helpers;

import java.util.*;

import static ui.utils.ElementActions.clickElement;
import static ui.utils.ElementActions.findElements;

public class CartPage {
    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);
    private final By cartIcon = By.id("nav-cart");
    private final By cartItems = By.xpath("//div[@id='sc-active-cart']//ul[contains(@class, 'sc-item-content-list')]");
    private final By cartItemName = By.xpath(".//span[contains(@class, 'a-truncate-full') and contains(@class,'a-offscreen')]");
    private final By cartItemPrice = By.xpath(".//span[contains(@class,'apex-price-to-pay-value')]//span[@class='a-offscreen']");

    public void openCart() {
        logger.info("Opening cart page");
        clickElement(cartIcon);
    }

    public boolean verifyCartItems(Map<String, Double> expectedItems) {
        try {
            openCart();
            List<WebElement> items = findElements(cartItems);
            logger.debug("Found {} items in cart, expected {}", items.size(), expectedItems.size());
            if (items.size() != expectedItems.size()) {
                logger.warn("Cart item count mismatch: found {}, expected {}", items.size(), expectedItems.size());
                return false;
            }

            Map<String, Double> remainingItems = new HashMap<>(expectedItems);
            for (WebElement item : items) {
                String name = item.findElement(cartItemName).getDomProperty("textContent");
                if (name == null) {
                    logger.error("Cart item name is null");
                    return false;
                }
                name = name.trim();

                String priceText = item.findElement(cartItemPrice).getDomProperty("textContent");
                if (priceText == null) {
                    logger.error("Cart item price is null for item: {}", name);
                    return false;
                }

                double actualPrice = Helpers.parsePrice(priceText);
                Double expectedPrice = remainingItems.get(name);
                if (expectedPrice == null || Math.abs(actualPrice - expectedPrice) > 0.01) {
                    logger.warn("Price mismatch for item '{}': found {}, expected {}", name, actualPrice, expectedPrice);
                    return false;
                }
                logger.info("Verified item: {} - EGP {}", name, actualPrice);
                remainingItems.remove(name);
            }

            if (!remainingItems.isEmpty()) {
                logger.warn("Some expected items not found in cart: {}", remainingItems.keySet());
                return false;
            }
            logger.info("All cart items verified successfully");
            return true;

        } catch (Exception e) {
            logger.error("Failed to verify cart items", e);
            return false;
        }
    }
}