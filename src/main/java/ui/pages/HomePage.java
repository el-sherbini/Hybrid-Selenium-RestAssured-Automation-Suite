package ui.pages;

import org.openqa.selenium.By;

import static ui.utils.ElementActions.clickElement;

public class HomePage {
    private final By loginPageBtn = By.id("nav-link-accountList");
    private final By allBtn = By.id("nav-hamburger-menu");
    private final By seeAllBtn = By.xpath("//div[contains(text(),'See all')]");
    private final By videoGamesBtn = By.xpath("//a/div[text()='Video Games']");
    private final By allVideoGamesBtn = By.xpath("//a[contains(text(),'All Video Games')]");

    public void clickLoginPageBtn() {
        clickElement(loginPageBtn);
    }

    public void clickAllBtn() {
        clickElement(allBtn);
    }

    public void clickSeeAllBtn() {
        clickElement(seeAllBtn);
    }

    public void clickVideoGamesBtn() {
        clickElement(videoGamesBtn);
    }

    public void clickAllVideoGamesBtn() {
        clickElement(allVideoGamesBtn);
    }
}