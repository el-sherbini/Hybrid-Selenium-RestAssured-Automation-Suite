package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static ui.utils.ElementActions.*;

public class VideoGamesPage {
    private static final Logger logger = LoggerFactory.getLogger(VideoGamesPage.class);

    private final By freeShippingCheckBox = By.xpath("//label[@for='apb-browse-refinements-checkbox_0']");
    private final By conditionNewBtn = By.xpath("//span[text()='New']/parent::a");
    private final By sortByPriceDropdown = By.xpath("//label[text()='Sort by:']/following-sibling::select");
    private final By productItems = By.xpath("//*[@data-component-type='s-search-result']");
    private final By priceWhole = By.className("a-price-whole");
    private final By addToCartButton = By.xpath(".//button[@aria-label='Add to cart']");
    private final By nextPageButton = By.className("s-pagination-next");
    private final By productTitle = By.xpath(".//a[contains(@class, 'a-text-normal')]/h2/span");

    public void clickFreeShippingCheckBox() {
        clickElement(freeShippingCheckBox);
    }

    public void clickConditionNewBtn() {
        logger.info("Applying filters: Free Shipping and New Condition");

        clickElement(conditionNewBtn);
    }

    public void selectSortByDropdown(String text) {
        logger.info("Selecting sort order: {}", text);

        selectFromDropdownByVisibleText(sortByPriceDropdown, text);
    }

    private final Map<String, Double> addedProducts = new HashMap<>();

    public Map<String, Double> getAddedProducts() {
        return addedProducts;
    }

    public void addProductsBelowLimit(double priceLimit) {
        boolean hasNextPage = true;

        while (hasNextPage) {
            List<WebElement> products = findElements(productItems);

            for (WebElement product : products) {
                try {
                    String priceText = product.findElement(priceWhole).getText().replace(",", "").trim();
                    double price = Double.parseDouble(priceText);

                    if (price <= priceLimit) {
                        try {
                            WebElement addButton = product.findElement(addToCartButton);
                            String productName = product.findElement(productTitle).getText();

                            addButton.click();
                            addedProducts.put(productName, price);

                            Thread.sleep(1000);
                        } catch (org.openqa.selenium.NoSuchElementException e) {
                            logger.warn("No Add to Cart button for product under price limit, skipping.");
                        }
                    }
                } catch (Exception e) {
                    logger.warn("Skipped a product due to error: {}", e.getMessage());
                }
            }

            try {
                WebElement nextBtn = findElement(nextPageButton);
                if (nextBtn.isDisplayed() && nextBtn.isEnabled()) {
                    nextBtn.click();
                    Thread.sleep(2000);
                } else {
                    hasNextPage = false;
                }
            } catch (Exception e) {
                hasNextPage = false;
            }
        }
    }

}