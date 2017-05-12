package be.mira.jongeren.mailinglist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class MailSenderImpl implements MailSender{

    private JavaMailSender mailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String toMailAddress, String body){
        // Send mail.
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("mailaddress");
            messageHelper.setTo(toMailAddress);
            messageHelper.setSubject("[MIRA Jeugdkern] Mailinglijst Inschrijving");
            messageHelper.setText(body);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }
    }
}
