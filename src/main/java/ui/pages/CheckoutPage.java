package ui.pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static ui.utils.ElementActions.*;
import static ui.utils.Helpers.parsePrice;

public class CheckoutPage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutPage.class);

    private final By proceedToCheckoutBtn = By.name("proceedToRetailCheckout");
    private final By changeShippingDataBtn = By.xpath("//a[contains(text(),'Change')]");
    private final By addNewDeliveryAddressBtn = By.partialLinkText("Add a new delivery address");

    private final By addressFullNameInput = By.id("address-ui-widgets-enterAddressFullName");
    private final By phoneNumberInput = By.id("address-ui-widgets-enterAddressPhoneNumber");
    private final By stNameInput = By.id("address-ui-widgets-enterAddressLine1");
    private final By buildingNameNoInput = By.id("address-ui-widgets-enter-building-name-or-number");
    private final By cityAreaDropdown = By.id("address-ui-widgets-enterAddressCity");
    private final By districtDropdown = By.id("address-ui-widgets-enterAddressDistrictOrCounty");
    private final By landmarkInput = By.id("address-ui-widgets-landmark");

    private final By cashOnDeliveryOption = By.xpath("//span[text()='Cash on Delivery (COD)']/../../input");
    private final By usePaymentMethodBtn = By.xpath("span#checkout-primary-continue-button-id-announce");

    private final By itemsFeesText = By.xpath("//span[contains(text(),'Items')]/../../following-sibling::div/span");
    private final By shippingFeesText = By.xpath("//span[contains(text(),'Shipping')]/../../following-sibling::div/span");
    private final By CODFeesText = By.xpath("//span[contains(text(),'Cash')]/../../following-sibling::div/span");
    private final By totalFeesText = By.xpath("//span[contains(text(),'Total')]/../../following-sibling::div/span");
    private final By freeDeliveryText = By.xpath("//div[contains(text(),'Free Delivery')]/following-sibling::div");
    private final By orderTotalText = By.xpath("//span[contains(text(),'Order total')]/../following-sibling::div");

    public void addAddressAndSelectCashPayment(String fullName, String phoneNumber, String stName,
                                               String buildingNameNo, String cityArea, String district, String landmark){
        logger.info("Adding address and selecting Cash on Delivery");
        clickElement(proceedToCheckoutBtn);
        clickElement(changeShippingDataBtn);
        clickElement(addNewDeliveryAddressBtn);
        sendTextToElement(addressFullNameInput, fullName);
        sendTextToElement(phoneNumberInput, phoneNumber);
        sendTextToElement(stNameInput, stName);
        sendTextToElement(buildingNameNoInput, buildingNameNo);
        sendTextWithDelay(cityAreaDropdown, cityArea, 1000);
        sendTextWithDelay(districtDropdown, district, 1000);
        sendTextToElement(landmarkInput, landmark);
        clickElement(cashOnDeliveryOption);
        clickElement(usePaymentMethodBtn);
    }

    public boolean verifyTotalAmount(Map<String, Double> addedProducts) {
        logger.info("Verifying total amount with added products: {}", addedProducts);

        double totalProductsPrice = 0.0;
        for (Double price : addedProducts.values()) {
            if (price != null) {
                totalProductsPrice += price;
            }
        }

        double itemsFees = parsePrice(getTextFromElement(itemsFeesText));
        double shippingFees = parsePrice(getTextFromElement(shippingFeesText));
        double CODFees = parsePrice(getTextFromElement(CODFeesText));
        double totalFees = parsePrice(getTextFromElement(totalFeesText));
        double freeDelivery = parsePrice(getTextFromElement(freeDeliveryText));
        double orderTotal = parsePrice(getTextFromElement(orderTotalText));

        logger.debug("Order total breakdown: items={}, shipping={}, COD={}, total={}, freeDelivery={}, orderTotal={}",
                itemsFees, shippingFees, CODFees, totalFees, freeDelivery, orderTotal);

        return totalProductsPrice == itemsFees && orderTotal == itemsFees + shippingFees + CODFees + totalFees + freeDelivery;
    }
}