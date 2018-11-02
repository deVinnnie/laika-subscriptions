package be.mira.jongeren.mailinglist.service;

import be.mira.jongeren.mailinglist.common.MockMvcTest;
import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.repository.SubscriberRepository;
import be.mira.jongeren.mailinglist.repository.SubscriptionListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.jupiter.api.Assertions.*;

class SubscriberServiceTest extends MockMvcTest{

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriptionListRepository subscriptionListRepository;

    @Test
    void unsubscribeRemovesSubscriberFromDB(){
        dbSetup(
            insertInto("subscriber")
                .columns("id", "voornaam", "achternaam", "email", "active")
                .values("10", "Luke", "Skywalker", "luke@rebellion.org", "true")
                .build()
        );

        subscriberService.unsubscribe(10L);
        assertEquals(0, subscriberRepository.count());
    }

    @Test
    void unsubscribeRemovesSubscriberFromAllSubscriptionLists(){
        dbSetup(
            sequenceOf(
                insertInto("subscriber")
                    .columns("id", "voornaam", "achternaam", "email", "active")
                    .values("20", "Leia", "Organa", "leia@rebellion.org", "true")
                    .build(),
                insertInto("subscription_list_subscribers")
                    .columns("subscriptions_id", "subscribers_id")
                    .values("10", "20")
                    .build()
            )
        );

        subscriberService.unsubscribe(20L);
        assertEquals(0, subscriberRepository.count());
        assertEquals(0, subscriptionListRepository.getOne(10L).getSubscribers().size());
    }

    @Test
    void subscribeShouldAddSubscriberToAllLists(){
        subscriberService.subscribe(
                new Subscriber("Luke", "Skywalker", "luke@rebellion.org"),
                new String[]{"supernova", "main-sequence"}
            );

        assertEquals(1, subscriberRepository.count());
        subscriptionListRepository.findAll()
            .forEach(
                subscriptionList -> {
                    assertEquals(1, subscriptionList.getSubscribers().size());
                }
            );
    }

    @Test
    void multipleSubscribersShouldBeAddedToAllLists(){
        subscriberService.subscribe(
                new Subscriber("Luke", "Skywalker", "luke@rebellion.org"),
                new String[]{"supernova", "main-sequence"}
        );

        subscriberService.subscribe(
                new Subscriber("Leia", "Organa", "leia@rebellion.org"),
                new String[]{"supernova", "main-sequence"}
        );

        assertEquals(2, subscriberRepository.count());
        subscriptionListRepository.findAll()
                .forEach(
                        subscriptionList -> {
                            assertEquals(2, subscriptionList.getSubscribers().size());
                        }
                );
    }
}
