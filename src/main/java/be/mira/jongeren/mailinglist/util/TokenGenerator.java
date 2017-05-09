package be.mira.jongeren.mailinglist.util;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    /**
     * Generate random token to confirm subscription.
     * */
    public String generate() {
        RandomStringUtils rutils = new RandomStringUtils();
        String token = RandomStringUtils.randomAlphanumeric(16);
        return token;
    }
}
