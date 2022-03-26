package be.mira.jongeren.mailinglist.geb

import be.mira.jongeren.mailinglist.geb.pages.SubscriptionCreationPage
import be.mira.jongeren.mailinglist.repository.SubscriberRepository
import org.junit.jupiter.api.Test

import javax.inject.Inject

import static be.mira.jongeren.mailinglist.geb.pages.SubscriptionCreationPage.SUBSCRIPTIONLIST_MAIN_SEQUENCE
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class InvalidEmailTest extends BaseTest {

    @Inject
    SubscriberRepository subscriberRepository

    @Test
    void attemptDuplicateSubscription(){
        to SubscriptionCreationPage

        page.firstNameInputElement << "Luke"
        page.lastNameInputElement << "Skywalker"
        page.emailAddressInputElement << "luke.skywalker"

        page.checkSubscriptionList(SUBSCRIPTIONLIST_MAIN_SEQUENCE)

        page.submitButton.click()

        assertEquals(0, subscriberRepository.count())
        assertTrue(page.error.present)
    }
}
