package be.mira.jongeren.mailinglist.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubscriberTest {

    @Test
    public void activateWithCorrectTokenSucceeds(){
        String token = "abc";
        Subscriber subscriber = new Subscriber();
        subscriber.setToken(token);
        subscriber.activate(token);

        assertTrue(subscriber.isActive());
    }

    @Test
    public void activateSubscriberWithUnsetTokenFails(){
        Subscriber subscriber = new Subscriber();
        subscriber.activate("abc");

        assertFalse(subscriber.isActive());
    }

    @Test
    public void activateWithTokenNullFails(){
        Subscriber subscriber = new Subscriber();
        subscriber.setToken("abc");
        subscriber.activate(null);

        assertFalse(subscriber.isActive());
    }

    @Test
    public void activateWithEmptyTokenFails(){
        Subscriber subscriber = new Subscriber();
        subscriber.setToken("");
        subscriber.activate(null);

        assertFalse(subscriber.isActive());
    }

    @Test
    public void activateSubscriberWithUnsetTokenWithEmptyTokenFails(){
        Subscriber subscriber = new Subscriber();
        subscriber.activate("");

        assertFalse(subscriber.isActive());
    }

    @Test
    public void activateSubscriberWithUnsetTokenWithNulTokenFails() {
        Subscriber subscriber = new Subscriber();
        subscriber.activate(null);

        assertFalse(subscriber.isActive());
    }

}
