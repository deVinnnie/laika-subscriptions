package be.mira.jongeren.mailinglist.repository;

import be.mira.jongeren.mailinglist.controller.MockMvcTest;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SubscriptionListRepositoryTest extends MockMvcTest{

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
