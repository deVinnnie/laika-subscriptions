package be.mira.jongeren.mailinglist.controller;

import be.mira.jongeren.mailinglist.Application;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

/**
 * Base test class with the right configuration to use Spring MockMvc.
 *
 * Tests wishing to use MockMvc should inherit this class,
 * and utilize the mockMvc() method to do their requests.
 *
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Application.class}) // Spring Boot config (includes component scan)
@Transactional // Enables rollback after each test.
@ActiveProfiles({"development", "test"})
public abstract class MockMvcTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DataSource dataSource;

    private MockMvc mockMvc;

    @Before
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Before
    public void dbSetup(){
        Operation operation = sequenceOf(
                deleteAllFrom("subscription_list"),
                insertInto("subscription_list")
                        .columns("id","title")
                        .values("10", "main-sequence")
                        .values("20", "supernova")
                        .build()
        );

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

    /**
     * Convenience method for subclasses to access the MockMvc instance.
     */
    protected MockMvc mockMvc(){
        return this.mockMvc;
    }
}
