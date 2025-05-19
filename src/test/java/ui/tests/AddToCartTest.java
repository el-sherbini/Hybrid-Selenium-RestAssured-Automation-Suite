package ui.tests;

import config.ConfigType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import config.ConfigManager;
import ui.driver.DriverFactory;
import ui.pages.*;
import ui.utils.DataProviders;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.utils.ElementActions;

import java.util.Map;

public class AddToCartTest {
    private static final Logger logger = LoggerFactory.getLogger(AddToCartTest.class);

    private HomePage homePage;
    private LoginPage loginPage;
    private VideoGamesPage videoGamesPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private SoftAssert softAssert;

    @BeforeMethod
    public void setUpTest() {
        logger.info("Setting up WebDriver and page objects");
        WebDriver driver = DriverFactory.getDriver(ConfigManager.getProperty(ConfigType.UI, "BROWSER"));
        driver.manage().window().maximize();

        homePage = new HomePage();
        loginPage = new LoginPage();
        videoGamesPage = new VideoGamesPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        softAssert = new SoftAssert();

        // Step 1: Open https://www.amazon.eg/
        ElementActions.openURL(ConfigManager.getProperty(ConfigType.UI, "BASE_URL"));
    }

    @Test(dataProvider = "shippingData", dataProviderClass = DataProviders.class)
    public void testAddProductsBelow15kEGPWithCheckout(String fullName, String phoneNumber, String stName,
                                                        String buildingNameNo, String cityArea, String district, String landmark) {
        logger.info("Starting test for adding products below 15k EGP with shipping data: {}, {}, ...", fullName, phoneNumber, stName, buildingNameNo, cityArea, district, landmark);

        // Step 1: Log in
        homePage.clickLoginPageBtn();
        loginPage.setEmailInput(ConfigManager.getProperty(ConfigType.UI, "EMAIL"));
        loginPage.clickContinueBtn();
        loginPage.setPasswordInput(ConfigManager.getProperty(ConfigType.UI, "PASSWORD"));
        loginPage.clickSignInBtn();

        // Step 2: Open “All” menu from the left side
        homePage.clickAllBtn();

        // Step 3: Click on “video games” then choose “all video games”
        homePage.clickSeeAllBtn();
        homePage.clickVideoGamesBtn();
        homePage.clickAllVideoGamesBtn();

        // Step 4: Add filters for “free shipping” and “new” condition
        videoGamesPage.clickFreeShippingCheckBox();
        videoGamesPage.clickConditionNewBtn();

        // Step 5: Sort by price: high to low
        videoGamesPage.selectSortByDropdown("Price: High to Low");

        // Step 6: Add all products below that its cost below 15k EGP, if no product below 15k EGP move to next page
        videoGamesPage.addProductsBelowLimit(12000.00);

        // Step 7: Make sure that all products are already added to carts
        Map<String, Double> expectedProducts = videoGamesPage.getAddedProducts();
        boolean productsMatch = cartPage.verifyCartItems(expectedProducts);
        Assert.assertTrue(productsMatch, "Not all expected products were found in the cart.");

        // Step 8: Add address and choose cash as a payment method
        checkoutPage.addAddressAndSelectCashPayment(fullName, phoneNumber, stName, buildingNameNo, cityArea, district, landmark);

        // Step 9: Verify the total amount, including shipping fees
        boolean totalVerified = checkoutPage.verifyTotalAmount(expectedProducts);
        softAssert.assertTrue(totalVerified, "Total amount verification failed: Subtotal or total mismatch");

        // Assert all soft assertions
        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down WebDriver instance");
        DriverFactory.quitDriver();
    }
}