package be.mira.jongeren.mailinglist.service;

import be.mira.jongeren.mailinglist.common.MockMvcTest;
import be.mira.jongeren.mailinglist.domain.SubscriptionEvent;
import be.mira.jongeren.mailinglist.repository.SubscriptionEventRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static org.junit.Assert.assertEquals;

public class SubscriptionEventAdviceTest extends MockMvcTest {

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;


    @Test
    public void adviceMethodTriggeredByUnsubscribe(){
        dbSetup(
            insertInto("subscriber")
                .columns("id", "voornaam", "achternaam", "email", "active")
                .values("10", "Luke", "Skywalker", "luke@rebellion.org", "true")
                .build()
        );

        subscriberService.unsubscribe(10L);

        // One for each SubscriptionList
        assertEquals(2, subscriptionEventRepository.count());

        for (SubscriptionEvent count : subscriptionEventRepository.findAll()) {
            assertEquals(0, count.getCount());
        }
    }

    @Test
    public void adviceMethodTriggeredByActivate(){
        dbSetup(
            insertInto("subscriber")
                .columns("id", "voornaam", "achternaam", "email", "active", "token")
                .values("10", "Luke", "Skywalker", "luke@rebellion.org", "false", "aaaabbbbcccdddd")
                .build()
        );

        subscriberService.activate(10L, "aaaabbbbcccdddd");

        // One for each SubscriptionList
        assertEquals(2, subscriptionEventRepository.count());
    }
}
