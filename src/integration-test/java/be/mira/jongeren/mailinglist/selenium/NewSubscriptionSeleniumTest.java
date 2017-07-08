package be.mira.jongeren.mailinglist.selenium;

import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.repository.SubscriberRepository;
import be.mira.jongeren.mailinglist.repository.SubscriptionEventRepository;
import be.mira.jongeren.mailinglist.selenium.pages.ActivationPage;
import be.mira.jongeren.mailinglist.selenium.pages.SubscriptionCreationPage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static be.mira.jongeren.mailinglist.selenium.pages.SubscriptionCreationPage.SUBSCRIPTIONLIST_MAIN_SEQUENCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
* Selenium test for the green path scenario.
* 
* A subscription to the main-sequence list and immediate activation with token.
*
*/
public class NewSubscriptionSeleniumTest extends SeleniumTest {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;

    @Test
    public void createNewSubscription(){
        // 1. Sign up
        SubscriptionCreationPage page = new SubscriptionCreationPage(driver(), getBaseUrl());
        page
            .enterEmailAddresss("luke.skywalker@galaxy.com")
            .enterFirstName("Luke")
            .enterLastName("Skywalker")
            .checkSubscriptionList(SUBSCRIPTIONLIST_MAIN_SEQUENCE);

        ActivationPage activationPage = page.submit();

        assertEquals(1, subscriberRepository.count());
        Subscriber subscriber = subscriberRepository.findByEmail("luke.skywalker@galaxy.com");
        assertFalse(subscriber.isActive());

        // 2. Activate with Token
        // Assume that mail would have been sent correctly.
        SubscriptionCreationPage homePage = activationPage
                .enterToken(subscriber.getToken())
                .submit();

        subscriber = subscriberRepository.findByEmail("luke.skywalker@galaxy.com");
        assertTrue(subscriber.isActive());

        // 3. Trigger for SubscriptionEvent should have run.
        assertEquals(2, subscriptionEventRepository.count());

        // 4. A green box should appear on the Home page.
        assertTrue(homePage.isCalloutPresent());
    }
}
