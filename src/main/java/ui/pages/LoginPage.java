package ui.pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ui.utils.ElementActions.clickElement;
import static ui.utils.ElementActions.sendTextToElement;

public class LoginPage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private final By emailInput = By.name("email");
    private final By continueBtn = By.id("continue");
    private final By passwordInput = By.name("password");
    private final By signInBtn = By.id("signInSubmit");

    public void setEmailInput(String email) {
        sendTextToElement(emailInput, email);
    }

    public void clickContinueBtn() {
        clickElement(continueBtn);
    }

    public void setPasswordInput(String password) {
        sendTextToElement(passwordInput, password);
    }

    public void clickSignInBtn() {
        logger.info("Sign in");

        clickElement(signInBtn);
    }
}