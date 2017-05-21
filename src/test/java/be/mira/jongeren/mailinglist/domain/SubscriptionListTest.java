package be.mira.jongeren.mailinglist.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SubscriptionListTest {

    private SubscriptionList subscriptionList;
    private Subscriber luke;
    private Subscriber leia;
    private Subscriber han;

    @Before
    public void setUp(){
        subscriptionList = new SubscriptionList();
        luke = new Subscriber("luke@rebellion.org");
        leia = new Subscriber("leia@rebellion.org");
        han = new Subscriber("han@rebellion.org");
        List<Subscriber> subscribers = new ArrayList<>();
        subscribers.add(luke);
        subscribers.add(leia);
        subscriptionList.setSubscribers(subscribers);
    }

    @Test
    public void removeSubscriberDeletesFromList(){
        subscriptionList.removeSubscriber(luke);

        assertEquals(1, subscriptionList.getSubscribers().size());
    }

    @Test
    public void removeSubscriberWithNonExistantSubscriberDoesNothing(){
        subscriptionList.removeSubscriber(han);
        assertEquals(2, subscriptionList.getSubscribers().size());
    }

    @Test
    public void removeSubscriberWithNullDoesNothing(){
        subscriptionList.removeSubscriber(null);
        assertEquals(2, subscriptionList.getSubscribers().size());
    }
}
