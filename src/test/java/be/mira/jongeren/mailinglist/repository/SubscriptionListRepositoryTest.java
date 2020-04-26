package be.mira.jongeren.mailinglist.repository;

import be.mira.jongeren.mailinglist.common.MockMvcTest;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionListRepositoryTest extends MockMvcTest {

    @Inject
    private SubscriptionListRepository subscriptionListRepository;


    @Test
    void findByTitleReturnsEntity(){
        assertEquals(2,subscriptionListRepository.count());
        SubscriptionList subscriptionList = subscriptionListRepository.findByTitle("main-sequence");
        assertNotNull(subscriptionList);
        assertEquals(10L, (long) subscriptionList.getId());
    }

}
