package be.mira.jongeren.mailinglist.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ActivationPage extends PageObject{

    @FindBy(name = "token")
    private WebElement tokenInputElement;

    @FindBy(tagName = "form")
    private WebElement formElement;

    public ActivationPage(WebDriver webDriver) {
        super(webDriver);
    }


    public ActivationPage enterToken(String token) {
        tokenInputElement.sendKeys(token);
        return this;
    }

    public void submit(){
        formElement.submit();
    }
}
