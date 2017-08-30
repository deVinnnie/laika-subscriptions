package be.mira.jongeren.mailinglist.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"mock"})
public class MailSenderMock implements MailSender{

    public void send(String toMailAddress, String body){
        // Send mail.
        System.out.println("[MIRA Jeugdkern] Mailinglijst inschrijving");
        System.out.println(body);
    }

}
