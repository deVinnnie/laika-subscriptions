package be.mira.jongeren.mailinglist.repository;

import be.mira.jongeren.mailinglist.common.MockMvcTest;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SubscriptionListRepositoryTest extends MockMvcTest {

    @Autowired
    private SubscriptionListRepository subscriptionListRepository;


    @Test
    public void findByTitleReturnsEntity(){
        assertEquals(2,subscriptionListRepository.count());
        SubscriptionList subscriptionList = subscriptionListRepository.findByTitle("main-sequence");
        assertNotNull(subscriptionList);
        assertEquals(10L, (long) subscriptionList.getId());
    }

}
