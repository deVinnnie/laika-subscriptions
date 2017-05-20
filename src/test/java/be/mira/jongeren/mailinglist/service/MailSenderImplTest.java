package be.mira.jongeren.mailinglist.service;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class MailSenderImplTest {

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.ALL);

    @Test
    public void testSomething() throws MessagingException {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.port", "3025");
        properties.setProperty("mail.smtp.host", "localhost");
        properties.setProperty("mail.smtp.user", "user");
        properties.setProperty("mail.smtp.user", "user");
        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setUsername("user");
        javaMailSender.setPassword("user");

        MailSender mailSender = new MailSenderImpl(javaMailSender, "laika");
        mailSender.send("laika", "Hello World");

        MimeMessage[] emails = greenMail.getReceivedMessages();
        assertEquals(1, emails.length);
        MimeMessage email = emails[0];
        assertEquals("laika", email.getHeader("From")[0]);
        assertEquals("laika", email.getRecipients(Message.RecipientType.TO)[0].toString());
    }
}
