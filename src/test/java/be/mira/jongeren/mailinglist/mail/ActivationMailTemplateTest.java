package be.mira.jongeren.mailinglist.mail;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivationMailTemplateTest {

    @Test
    void formatAddsTokenToMailBody(){
        String token = "abcd";
        ActivationMailTemplate mailTemplate = new ActivationMailTemplate();
        String body = mailTemplate.format(token, "");
        assertTrue(body.contains("activatiecode: " + token));
    }

    @Test
    void formatWithTokenNullGivesNull(){
        ActivationMailTemplate mailTemplate = new ActivationMailTemplate();
        String body = mailTemplate.format(null, "");
        assertNull(body);
    }
}
