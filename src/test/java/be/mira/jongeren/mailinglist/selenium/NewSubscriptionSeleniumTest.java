package be.mira.jongeren.mailinglist.selenium;

import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.repository.SubscriberRepository;
import be.mira.jongeren.mailinglist.selenium.pages.ActivationPage;
import be.mira.jongeren.mailinglist.selenium.pages.SubscriptionCreationPage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NewSubscriptionSeleniumTest extends SeleniumTest {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Test
    public void createNewSubscription(){
        // 1. Sign up
        SubscriptionCreationPage page = new SubscriptionCreationPage(driver(), getBaseUrl());
        page
            .enterEmailAddresss("luke.skywalker@galaxy.com")
            .enterFirstName("Luke")
            .enterLastName("Skywalker");

        ActivationPage activationPage = page.submit();

        assertEquals(1, subscriberRepository.count());
        Subscriber subscriber = subscriberRepository.findOne(1L);

        // 2. Activate with Token
        activationPage
                .enterToken(subscriber.getToken())
                .submit();

        subscriber = subscriberRepository.findOne(1L);
        assertTrue(subscriber.isActive());
    }
}
