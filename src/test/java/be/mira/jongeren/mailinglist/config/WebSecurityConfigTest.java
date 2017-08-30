package be.mira.jongeren.mailinglist.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "consult_user=dummy",
                "consult_password=dummy"
        })
@ActiveProfiles({"development", "test","mock"})
public class WebSecurityConfigTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void requestOnProtectedPageRedirectsToLoginPage() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/consult", String.class);
        assertThat(response.getBody(), anyOf(
                is(nullValue()),
                containsString("Please enter your username and password")
        ));
    }

    @Test
    public void requestOnPublicPageWorksWithoutLogin() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody(), not(containsString("Please enter your username and password")));
    }
}
