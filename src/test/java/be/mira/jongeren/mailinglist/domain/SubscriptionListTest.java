package be.mira.jongeren.mailinglist.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubscriptionListTest {

    private SubscriptionList subscriptionList;
    private Subscriber luke;
    private Subscriber leia;
    private Subscriber han;

    @BeforeEach
    void setUp(){
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
    void removeSubscriberDeletesFromList(){
        subscriptionList.remove(luke);

        assertEquals(1, subscriptionList.getSubscribers().size());
    }

    @Test
    void removeSubscriberWithNonExistantSubscriberDoesNothing(){
        subscriptionList.remove(han);
        assertEquals(2, subscriptionList.getSubscribers().size());
    }

    @Test
    void removeSubscriberWithNullDoesNothing(){
        subscriptionList.remove(null);
        assertEquals(2, subscriptionList.getSubscribers().size());
    }

    @Test
    void countWithNewSubscriptionListReturnsZero() {
        SubscriptionList list = new SubscriptionList();
        assertEquals(0, list.count());
    }


    @Test
    void countOnlyConsidersActiveSubscribers(){
        SubscriptionList list = new SubscriptionList();
        luke = new Subscriber("luke@rebellion.org");
        luke.setActive(false);

        leia = new Subscriber("leia@rebellion.org");
        leia.setActive(true);

        list.add(luke);
        list.add(leia);

        assertEquals(1, list.count());
    }

    @Test
    void getRawMailAddressesGivesCommaSeperatedListOfSubscribersMailAddresses(){
        SubscriptionList list = new SubscriptionList();
        luke = new Subscriber("luke@rebellion.org");
        luke.setActive(true);

        leia = new Subscriber("leia@rebellion.org");
        leia.setActive(true);

        list.add(luke);
        list.add(leia);

        assertEquals("luke@rebellion.org,leia@rebellion.org", list.getRawMailAddresses());
    }
}
