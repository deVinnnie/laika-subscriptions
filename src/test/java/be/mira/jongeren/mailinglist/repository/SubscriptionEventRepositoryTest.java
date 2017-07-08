package be.mira.jongeren.mailinglist.repository;

import be.mira.jongeren.mailinglist.common.MockMvcTest;
import be.mira.jongeren.mailinglist.domain.SubscriptionEvent;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SubscriptionEventRepositoryTest extends MockMvcTest {

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;

    @Before
    public void setUp(){
        dbSetup(
            insertInto("subscription_event")
                    .columns("id", "subscription_list_id", "count", "timestamp")
                    .values("101",  "10", "1", "2015-01-02 20:00:00")
                    .values("102",  "10", "2", "2015-03-03 20:00:00")
                    .build()
        );
    }

    @Test
    public void findTopSubscriptionEventReturnsMostRecent(){
        SubscriptionList list = new SubscriptionList();
        list.setId(10L);

        SubscriptionEvent topSubscriptionEvent = subscriptionEventRepository.findTopSubscriptionEvent(list);
        assertEquals(102L, (long) topSubscriptionEvent.getId());
    }

    @Test
    public void findTopSubscriptionEventForListWithNoEntriesGivesNull(){
        SubscriptionList list = new SubscriptionList();
        list.setId(20L);

        SubscriptionEvent topSubscriptionEvent = subscriptionEventRepository.findTopSubscriptionEvent(list);
        assertNull(topSubscriptionEvent);
    }
}
