package be.mira.jongeren.mailinglist.controller;

import be.mira.jongeren.mailinglist.controllers.SubscriberController;
import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.repository.SubscriberRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SubscriberControllerTest extends MockMvcTest {

    @Autowired
    private SubscriberController subscriberController;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Test
    public void addSubscriberAddsNewEntity() throws Exception {
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

        assertFalse(persistedSubscriber.isActive());
        assertNotNull(persistedSubscriber.getToken());
    }

    @Test
    public void addSubscriberWithoutSubscriptionLists() throws Exception {
        mockMvc()
                .perform(
                        post("/")
                                .param("voornaam", "Luke")
                                .param("achternaam", "Skywalker")
                                .param("email", "luke.skywalker@rebellion.org")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("/subscribers/index"));

        assertEquals(0, subscriberRepository.count());
    }

    @Test
    public void activateSubscriberWithCorrectTokenChangesState() throws Exception{
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
    public void activateSubscriberWithInCorrectTokenIsRejected() throws Exception{
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
    public void activateSubscriberWithTokenSizeLongerThan16IsRejected() throws Exception{
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
    public void activateSubscriberWithEmptyTokenIsRejected() throws Exception{
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
}
