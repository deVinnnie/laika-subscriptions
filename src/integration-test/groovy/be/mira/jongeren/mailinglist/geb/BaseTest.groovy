package be.mira.jongeren.mailinglist.geb

import be.mira.jongeren.mailinglist.Application
import be.mira.jongeren.mailinglist.util.ClearDatabaseOperation
import be.mira.jongeren.mailinglist.util.CreateListsOperation
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import geb.junit4.GebReportingTest
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import javax.sql.DataSource

import static com.ninja_squad.dbsetup.Operations.sequenceOf

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes=[Application.class])
@ActiveProfiles(["development", "test", "mock"])
abstract class BaseTest extends GebReportingTest{

    @Value('${local.server.port}')
    int port

    @Autowired
    DataSource dataSource

    @Before
    void dbSetup(){
        browser.setBaseUrl("http://localhost:${port}")
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource),
                sequenceOf(
                        ClearDatabaseOperation.operation,
                        CreateListsOperation.operation
                )
        )
        dbSetup.launch()
    }
}
