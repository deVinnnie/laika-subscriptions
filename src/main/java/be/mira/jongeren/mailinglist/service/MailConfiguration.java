package be.mira.jongeren.mailinglist.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfiguration {

    @Value("${spring.mail.from}")
    public String fromMailAddress;

    public String subject = "[MIRA Jeugdkern] Mailinglijst Inschrijving";

}
