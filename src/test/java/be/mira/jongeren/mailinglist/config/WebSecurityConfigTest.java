package be.mira.jongeren.mailinglist.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "consult_user=dummy",
                "consult_password=dummy"
        })
@ActiveProfiles({"development", "test","mock"})
class WebSecurityConfigTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void requestOnProtectedPageRedirectsToLoginPage() {
        ResponseEntity<String> response = restTemplate.getForEntity("/consult", String.class);
        assertThat(response.getBody(), anyOf(
                is(nullValue()),
                containsString("Please enter your username and password")
        ));
    }

    @Test
    void requestOnPublicPageWorksWithoutLogin() {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody(), not(containsString("Please enter your username and password")));
    }
}
