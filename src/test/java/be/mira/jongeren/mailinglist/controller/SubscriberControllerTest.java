package be.mira.jongeren.mailinglist.controller;

import be.mira.jongeren.mailinglist.controllers.SubscriberController;
import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.repository.SubscriberRepository;
import be.mira.jongeren.mailinglist.common.MockMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SubscriberControllerTest extends MockMvcTest {

    @Autowired
    private SubscriberController subscriberController;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Test
    void addSubscriberAddsNewEntity() throws Exception {
        mockMvc()
            .perform(
                    post("/")
                            .param("voornaam", "Luke")
                            .param("achternaam", "Skywalker")
                            .param("email", "luke.skywalker@rebellion.org")
                            .param("lists", "main-sequence", "supernova")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            )
            .andExpect(status().is3xxRedirection());

        assertThat(subscriberRepository.findAll(), hasSize(equalTo(1)));

        Subscriber persistedSubscriber = subscriberRepository.findAll().get(0);

        // TODO: Activate when mailing works
        // assertFalse(persistedSubscriber.isActive());
        assertNotNull(persistedSubscriber.getToken());
    }

    @Test
    void addSubscriberWithExistingEmailGives409() throws Exception {
        addSubscriberAddsNewEntity();
        mockMvc()
                .perform(
                        post("/")
                                .param("voornaam", "Luke")
                                .param("achternaam", "Skywalker")
                                .param("email", "luke.skywalker@rebellion.org")
                                .param("lists", "main-sequence", "supernova")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is4xxClientError());

        assertThat(subscriberRepository.findAll(), hasSize(equalTo(1)));
    }

    @Test
    void addSubscriberWithoutSubscriptionListsGives400() throws Exception {
        mockMvc()
            .perform(
                post("/")
                    .param("voornaam", "Luke")
                    .param("achternaam", "Skywalker")
                    .param("email", "luke.skywalker@rebellion.org")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(view().name("subscribers/index"))
            .andExpect(model().attributeHasErrors("subscriber"))
            .andExpect(content().string(containsString("class=\"error\"")));

        assertEquals(0, subscriberRepository.count());
    }

    @Test
    void addSubscriberWithoutAnyFieldsFilledInGives400() throws Exception {
        mockMvc()
            .perform(
                post("/")
            )
            .andExpect(status().is4xxClientError())
            .andExpect(view().name(containsString("index")));
    }

    @Test
    void addSubscriberWithoutEmailAddressGives400() throws Exception {
        mockMvc()
            .perform(
                post("/")
                    .param("voornaam", "Luke")
                    .param("achternaam", "Skywalker")
                    .param("lists", "main-sequence", "supernova")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(view().name(containsString("index")));

        assertEquals(0, subscriberRepository.count());
    }


    @Test
    void addSubscriberWithInvalidEmailAddressGives400() throws Exception {
        mockMvc()
                .perform(
                        post("/")
                                .param("voornaam", "Luke")
                                .param("achternaam", "Skywalker")
                                .param("email", "luke")
                                .param("lists", "main-sequence", "supernova")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(view().name(containsString("index")));

        assertEquals(0, subscriberRepository.count());
    }


    @Test
    void activateSubscriberWithCorrectTokenChangesState() throws Exception{
        // Prepare test subscriber.
        Subscriber subscriber = new Subscriber("Luke", "Skywalker", "luke.skywalker@rebellion.org");
        subscriber.setToken("aaaabbbbccccdddd");
        subscriberRepository.save(subscriber);

        // Enter activation token.
        mockMvc()
                .perform(
                    post(String.format("/activate/%d", subscriber.getId()))
                        .param("token", "aaaabbbbccccdddd")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(
                    status().is3xxRedirection()
                );

        assertTrue(subscriber.isActive());
    }

    @Test
    void activateSubscriberWithInCorrectTokenIsRejected() throws Exception{
        // Prepare test subscriber.
        Subscriber subscriber = new Subscriber("Luke", "Skywalker", "luke.skywalker@rebellion.org");
        subscriber.setToken("aaaabbbbccccdddd");
        subscriberRepository.save(subscriber);

        // Enter activation token.
        mockMvc()
                .perform(
                        post(String.format("/activate/%d", subscriber.getId()))
                                .param("token", "aaaazzzzxxxxdddd")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(
                        status().is4xxClientError()
                );

        assertFalse(subscriber.isActive());
    }

    @Test
    void activateSubscriberWithTokenSizeLongerThan16IsRejected() throws Exception{
        // Prepare test subscriber.
        Subscriber subscriber = new Subscriber("Luke", "Skywalker", "luke.skywalker@rebellion.org");
        subscriberRepository.save(subscriber);

        // Enter activation token.
        mockMvc()
                .perform(
                        post(String.format("/activate/%d", subscriber.getId()))
                                .param("token", "aaaazzzzxxkjhsflkjdfjkqshdfmlkdjgflmkjbvquyçàopzkjxxdddd")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(
                        status().is4xxClientError()
                );

        assertFalse(subscriber.isActive());
    }

    @Test
    void activateSubscriberWithEmptyTokenIsRejected() throws Exception{
        // Prepare test subscriber.
        Subscriber subscriber = new Subscriber("Luke", "Skywalker", "luke.skywalker@rebellion.org");
        subscriberRepository.save(subscriber);

        // Enter activation token.
        mockMvc()
                .perform(
                        post(String.format("/activate/%d", subscriber.getId()))
                                .param("token", "")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(
                        status().is4xxClientError()
                );

        assertFalse(subscriber.isActive());
    }

    @Test
    void unsubscribeRemovesSubscriber() throws Exception{
        // Prepare test subscriber.
        Subscriber subscriber = new Subscriber("Luke", "Skywalker", "luke.skywalker@rebellion.org");
        subscriberRepository.save(subscriber);

        mockMvc()
            .perform(
                post("/unsubscribe/")
                    .param("email", subscriber.getEmail())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            )
            .andExpect(
                status().is2xxSuccessful()
            );

        assertEquals(0, subscriberRepository.count());
    }

    @Test
    void unsubscribeForNonExistantSubscriberGivesRedirect() throws Exception{
        mockMvc()
            .perform(
                post("/unsubscribe/")
                    .param("email", "han@solo.org")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            )
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void redirectPageAfterUnsubscribeContainsCallout() throws Exception{
        // Prepare test subscriber.
        Subscriber subscriber = new Subscriber("Luke", "Skywalker", "luke.skywalker@rebellion.org");
        subscriberRepository.save(subscriber);

        mockMvc()
                .perform(
                        post("/unsubscribe/")
                                .param("email", subscriber.getEmail())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("callout")));
    }
}
