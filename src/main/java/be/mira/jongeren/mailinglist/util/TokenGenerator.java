package be.mira.jongeren.mailinglist.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenGenerator {

    /**
     * Generate random token to confirm subscription.
     * */
    public String generate() {
        return UUID.randomUUID().toString().replace("-", "").substring(0,16);
    }
}
