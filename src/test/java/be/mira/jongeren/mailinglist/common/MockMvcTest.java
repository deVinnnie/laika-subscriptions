package be.mira.jongeren.mailinglist.common;

import be.mira.jongeren.mailinglist.Application;
import be.mira.jongeren.mailinglist.util.ClearDatabaseOperation;
import be.mira.jongeren.mailinglist.util.CreateListsOperation;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.sql.DataSource;

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
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={Application.class}) // Spring Boot config (includes component scan)
@Transactional // Enables rollback after each test.
@ActiveProfiles({"development", "test", "mock"})
@TestPropertySource(properties = {
    "consult_user=dummy",
    "consult_password=dummy",
    "spring.mail.from=laika"
})
public abstract class MockMvcTest {

    @Inject
    private WebApplicationContext context;

    @Inject
    private DataSource dataSource;

    private MockMvc mockMvc;

    @BeforeEach
    void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    protected Operation dbSetupOperation(){
        return sequenceOf(
                ClearDatabaseOperation.operation,
                CreateListsOperation.operation
        );
    }

    @BeforeEach
    void dbSetup(){
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), this.dbSetupOperation());
        dbSetup.launch();
    }

    protected void dbSetup(Operation operation){
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
