package be.mira.jongeren.mailinglist.mail;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ActivationMailTemplateTest {

    @Test
    public void formatAddsTokenToMailBody(){
        String token = "abcd";
        ActivationMailTemplate mailTemplate = new ActivationMailTemplate();
        String body = mailTemplate.format(token);
        assertTrue(body.contains("activatiecode: " + token));
    }

    @Test
    public void formatWithTokenNullGivesNull(){
        ActivationMailTemplate mailTemplate = new ActivationMailTemplate();
        String body = mailTemplate.format(null);
        assertNull(body);
    }
}
