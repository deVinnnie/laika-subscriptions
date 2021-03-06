package be.mira.jongeren.mailinglist.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SubscriptionCreationPage extends PageObject{

    public static String SUBSCRIPTIONLIST_MAIN_SEQUENCE = "main-sequence";
    public static String SUBSCRIPTIONLIST_SUPERNOVA = "supernova";

    @FindBy(name = "voornaam")
    private WebElement firstNameInputElement;

    @FindBy(name = "achternaam")
    private WebElement lastNameInputElement;

    @FindBy(name = "email")
    private WebElement emailAddressInputElement;

    @FindBy(tagName = "form")
    private WebElement formElement;

    @FindBy(className = "callout")
    private WebElement callout;

    public SubscriptionCreationPage(WebDriver webDriver) {
        super(webDriver);
    }

    public SubscriptionCreationPage(WebDriver webDriver, String baseUrl) {
        super(webDriver, baseUrl, "/");
    }

    public SubscriptionCreationPage enterFirstName(String firstName){
        firstNameInputElement.sendKeys(firstName);
        return this;
    }

    public SubscriptionCreationPage enterLastName(String lastName){
        lastNameInputElement.sendKeys(lastName);
        return this;
    }

    public SubscriptionCreationPage enterEmailAddresss(String emailAddress){
        emailAddressInputElement.sendKeys(emailAddress);
        return this;
    }

    public SubscriptionCreationPage checkSubscriptionList(String list) {
        driver().findElement(By.id("list-"+list)).click();
        return this;
    }

    public SubscriptionCreationPage submit(){
        this.formElement.submit();
        return new SubscriptionCreationPage(driver());
    }

    public boolean isCalloutPresent() {
        return (callout != null);
    }
}
