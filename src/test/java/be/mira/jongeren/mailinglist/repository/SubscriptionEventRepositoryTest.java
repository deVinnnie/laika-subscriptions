package be.mira.jongeren.mailinglist.repository;

import be.mira.jongeren.mailinglist.common.MockMvcTest;
import be.mira.jongeren.mailinglist.domain.SubscriptionEvent;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static org.junit.jupiter.api.Assertions.*;

class SubscriptionEventRepositoryTest extends MockMvcTest {

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;

    @BeforeEach
    public void setUp(){
        dbSetup(
            insertInto("subscription_event")
                    .columns("id", "subscription_list_id", "count", "timestamp")
                    .values("101",  "10", "1", "2015-01-02 20:00:00")
                    .values("102",  "10", "2", "2015-03-03 20:00:00")
                    .values("90",  "10", "2", "2014-03-03 20:00:00")
                    .build()
        );
    }

    @Test
    void findBySubscriptionListReturnsEventsInChronologicalOrder(){
        SubscriptionList list = new SubscriptionList();
        list.setId(10L);

        List<SubscriptionEvent> subscriptionEventList = subscriptionEventRepository.findBySubscriptionList(list);

        assertEquals(3, subscriptionEventList.size());

        assertEquals(90L, (long) subscriptionEventList.get(0).getId());
        assertEquals(101L, (long) subscriptionEventList.get(1).getId());
    }

    @Test
    void findTopSubscriptionEventReturnsMostRecent(){
        SubscriptionList list = new SubscriptionList();
        list.setId(10L);

        SubscriptionEvent topSubscriptionEvent = subscriptionEventRepository.findTopSubscriptionEvent(list);
        assertEquals(102L, (long) topSubscriptionEvent.getId());
    }

    @Test
    void findTopSubscriptionEventForListWithNoEntriesGivesNull(){
        SubscriptionList list = new SubscriptionList();
        list.setId(20L);

        SubscriptionEvent topSubscriptionEvent = subscriptionEventRepository.findTopSubscriptionEvent(list);
        assertNull(topSubscriptionEvent);
    }
}
