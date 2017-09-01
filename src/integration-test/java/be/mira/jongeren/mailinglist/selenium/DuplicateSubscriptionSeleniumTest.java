package be.mira.jongeren.mailinglist.selenium;

import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.repository.SubscriberRepository;
import be.mira.jongeren.mailinglist.repository.SubscriptionEventRepository;
import be.mira.jongeren.mailinglist.selenium.pages.SubscriptionCreationPage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static be.mira.jongeren.mailinglist.selenium.pages.SubscriptionCreationPage.SUBSCRIPTIONLIST_MAIN_SEQUENCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DuplicateSubscriptionSeleniumTest extends SeleniumTest {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;

    @Test
    public void attemptDuplicateSubscription(){
        // 1. Sign up first time.
        SubscriptionCreationPage page = new SubscriptionCreationPage(driver(), getBaseUrl());
        page
                .enterEmailAddresss("luke.skywalker@galaxy.com")
                .enterFirstName("Luke")
                .enterLastName("Skywalker")
                .checkSubscriptionList(SUBSCRIPTIONLIST_MAIN_SEQUENCE);

        page = page.submit();

        // 2. Attempt to sign up with the same e-mailaddress a second time.
        page = page
                .enterEmailAddresss("luke.skywalker@galaxy.com")
                .enterFirstName("Luke")
                .enterLastName("Skywalker")
                .checkSubscriptionList(SUBSCRIPTIONLIST_MAIN_SEQUENCE)
                .submit();


        assertEquals(1, subscriberRepository.count());
        assertTrue(page.hasClass("error"));
    }
}
