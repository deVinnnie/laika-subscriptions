package be.mira.jongeren.mailinglist.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberTest {

    @Test
    void activateWithCorrectTokenSucceeds(){
        String token = "abc";
        Subscriber subscriber = new Subscriber();
        subscriber.setToken(token);
        subscriber.activate(token);

        assertTrue(subscriber.isActive());
    }

    @Test
    void activateSubscriberWithUnsetTokenFails(){
        Subscriber subscriber = new Subscriber();
        subscriber.activate("abc");

        assertFalse(subscriber.isActive());
    }

    @Test
    void activateWithTokenNullFails(){
        Subscriber subscriber = new Subscriber();
        subscriber.setToken("abc");
        subscriber.activate(null);

        assertFalse(subscriber.isActive());
    }

    @Test
    void activateWithEmptyTokenFails(){
        Subscriber subscriber = new Subscriber();
        subscriber.setToken("");
        subscriber.activate(null);

        assertFalse(subscriber.isActive());
    }

    @Test
    void activateSubscriberWithUnsetTokenWithEmptyTokenFails(){
        Subscriber subscriber = new Subscriber();
        subscriber.activate("");

        assertFalse(subscriber.isActive());
    }

    @Test
    void activateSubscriberWithUnsetTokenWithNulTokenFails() {
        Subscriber subscriber = new Subscriber();
        subscriber.activate(null);

        assertFalse(subscriber.isActive());
    }

}
