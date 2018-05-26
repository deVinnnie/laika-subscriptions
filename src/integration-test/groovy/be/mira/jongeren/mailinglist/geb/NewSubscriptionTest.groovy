package be.mira.jongeren.mailinglist.geb

import be.mira.jongeren.mailinglist.domain.Subscriber
import be.mira.jongeren.mailinglist.geb.pages.SubscriptionCreationPage
import be.mira.jongeren.mailinglist.repository.SubscriberRepository
import be.mira.jongeren.mailinglist.repository.SubscriptionEventRepository
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

/**
 * be.mira.jongeren.mailinglist.geb.Test for the green path scenario.
 *
 * A subscription to the main-sequence list and immediate activation with token.
 *
 */
class NewSubscriptionTest extends BaseTest {

    @Autowired
    SubscriberRepository subscriberRepository

    @Autowired
    SubscriptionEventRepository subscriptionEventRepository

    @Test
    void createNewSubscription() {
        // 1. Sign up
        to SubscriptionCreationPage

        page.firstNameInputElement << "Luke"
        page.lastNameInputElement << "Skywalker"
        page.emailAddressInputElement << "luke.skywalker@galaxy.com"

        page.checkSubscriptionList("main-sequence")

        page.submitButton.click()

        assertEquals(1, subscriberRepository.count())
        Subscriber subscriber = subscriberRepository.findByEmail("luke.skywalker@galaxy.com")
        //assertFalse(subscriber.isActive());

        // 2. Activate with Token
        // Assume that mail would have been sent correctly.
        //be.mira.jongeren.mailinglist.geb.pages.SubscriptionCreationPage homePage = activationPage
        //        .enterToken(subscriber.getToken())
        //        .submit();

        subscriber = subscriberRepository.findByEmail("luke.skywalker@galaxy.com")
        assertTrue(subscriber.isActive());

        // 3. Trigger for SubscriptionEvent should have run.
        assertEquals(2, subscriptionEventRepository.count())

        // 4. A green box should appear on the Home page.
        assert page.callout.present
    }

}