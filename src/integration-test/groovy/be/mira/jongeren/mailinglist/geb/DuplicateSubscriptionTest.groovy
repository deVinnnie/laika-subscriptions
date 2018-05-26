package be.mira.jongeren.mailinglist.geb

import be.mira.jongeren.mailinglist.repository.SubscriberRepository
import be.mira.jongeren.mailinglist.geb.pages.SubscriptionCreationPage
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import static be.mira.jongeren.mailinglist.geb.pages.SubscriptionCreationPage.SUBSCRIPTIONLIST_MAIN_SEQUENCE

class DuplicateSubscriptionTest extends BaseTest {

    @Autowired
    SubscriberRepository subscriberRepository

    @Test
    void attemptDuplicateSubscription(){
        // 1. Sign up first time.
        to SubscriptionCreationPage

        page.firstNameInputElement << "Luke"
        page.lastNameInputElement << "Skywalker"
        page.emailAddressInputElement << "luke.skywalker@galaxy.com"

        page.checkSubscriptionList(SUBSCRIPTIONLIST_MAIN_SEQUENCE)

        page.submitButton.click()

        // 2. Attempt to sign up with the same e-mailaddress a second time.
        page.firstNameInputElement << "Luke"
        page.lastNameInputElement << "Skywalker"
        page.emailAddressInputElement << "luke.skywalker@galaxy.com"

        page.checkSubscriptionList(SUBSCRIPTIONLIST_MAIN_SEQUENCE)

        page.submitButton.click()

        assertEquals(1, subscriberRepository.count())
        assertTrue(page.error.present)
    }
}
