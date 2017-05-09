package be.mira.jongeren.mailinglist.selenium.pages;

import org.apache.commons.jexl2.introspection.SandboxUberspectImpl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SubscriptionCreationPage extends PageObject{

    @FindBy(name = "voornaam")
    private WebElement firstNameInputElement;

    @FindBy(name = "achternaam")
    private WebElement lastNameInputElement;

    @FindBy(name = "email")
    private WebElement emailAddressInputElement;

    @FindBy(tagName = "form")
    private WebElement formElement;

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

    public ActivationPage submit(){
        this.formElement.submit();
        return new ActivationPage(driver());
    }
}
