package be.mira.jongeren.mailinglist.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!production")
public class MailSenderMock implements MailSender{

    public void send(String toMailAddress, String body){
        // Send mail.
        System.out.println("[MIRA Jeugdkern] Mailinglijst inschrijving");
    }

}
