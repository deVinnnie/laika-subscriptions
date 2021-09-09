package be.mira.jongeren.mailinglist.geb.pages

import geb.Page
import org.openqa.selenium.By

class SubscriptionCreationPage extends Page {
    static url = "/"

    public static final String SUBSCRIPTIONLIST_MAIN_SEQUENCE = "main-sequence"
    public static final String SUBSCRIPTIONLIST_SUPERNOVA = "supernova"

    static content = {
        firstNameInputElement { $("input[name=voornaam]") }
        lastNameInputElement { $("input[name=achternaam]") }
        emailAddressInputElement { $("input[name=email]") }
        formElement { $("form") }
        submitButton { $("button[type=submit]") }
        callout { $(".callout") }
        error { $(".error") }
    }

    void checkSubscriptionList(String list) {
        driver.findElement(By.id("list-"+list)).click();
    }
}