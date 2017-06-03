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
        subscriptionList.remove(luke);

        assertEquals(1, subscriptionList.getSubscribers().size());
    }

    @Test
    public void removeSubscriberWithNonExistantSubscriberDoesNothing(){
        subscriptionList.remove(han);
        assertEquals(2, subscriptionList.getSubscribers().size());
    }

    @Test
    public void removeSubscriberWithNullDoesNothing(){
        subscriptionList.remove(null);
        assertEquals(2, subscriptionList.getSubscribers().size());
    }

    @Test
    public void countWithNewSubscriptionListReturnsZero() {
        SubscriptionList list = new SubscriptionList();
        assertEquals(0, list.count());
    }


    @Test
    public void countOnlyConsidersActiveSubscribers(){
        SubscriptionList list = new SubscriptionList();
        luke = new Subscriber("luke@rebellion.org");
        luke.setActive(false);

        leia = new Subscriber("leia@rebellion.org");
        leia.setActive(true);

        list.add(luke);
        list.add(leia);

        assertEquals(1, list.count());
    }
}
