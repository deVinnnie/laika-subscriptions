package be.mira.jongeren.mailinglist.geb

import be.mira.jongeren.mailinglist.repository.SubscriberRepository
import be.mira.jongeren.mailinglist.geb.pages.SubscriptionCreationPage
import org.junit.jupiter.api.Test

import javax.inject.Inject

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

import static be.mira.jongeren.mailinglist.geb.pages.SubscriptionCreationPage.SUBSCRIPTIONLIST_MAIN_SEQUENCE

class DuplicateSubscriptionTest extends BaseTest {

    @Inject
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

        assert page.lastNameInputElement == ""
        assert page.lastNameInputElement == ""
        assert page.emailAddressInputElement == ""

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
