package be.mira.jongeren.mailinglist.service;

import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.repository.SubscriberRepository;
import be.mira.jongeren.mailinglist.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

public class SubscriberServiceImpl implements SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    private MailSender mailSender;

    @Autowired
    public SubscriberServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void subscribe(Subscriber subscriber){
        // Set the current date as subscription date.
        subscriber.setSubscriptionDate(Calendar.getInstance());

        // Generate token
        String token = tokenGenerator.generate();
        subscriber.setToken(token);

        // Persist in database
        subscriberRepository.save(subscriber);


        // Prepare Mail
        String body = String.format("Beste, \n" +
                "Je hebt je ingescreven op onze mailinglijst. (Als je dit niet gedaan hebt, dan kan je deze mail negeren.)\n" +
                "\n" +
                "Om je inschrijving te voltooien moet je volgende activatiecode: %s\n" +
                "kopiëren op deze pagina: \n" +
                "\n" +
                "Met Vriendelijke Groeten,\n" +
                "Laika.\n" +
                "\n" +
                "--\n" +
                "Laika is de automatische mailer van de MIRA Jeugdkern. Laika is heel verlegen en antwoord daarom niet op mails.\n", "", "");


        mailSender.send(subscriber.getEmail(), body);
    }

    public boolean activate(Long id, String token){
        Subscriber subscriber = subscriberRepository.getOne(id);
        if(subscriber.getToken().equals(token)){
            subscriber.setActive(true);
        }
        return subscriber.isActive();
    }

    //<editor-fold desc="Getters & Setters">
    public SubscriberRepository getSubscriberRepository() {
        return subscriberRepository;
    }

    public void setSubscriberRepository(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public TokenGenerator getTokenGenerator() {
        return tokenGenerator;
    }

    public void setTokenGenerator(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }
    //</editor-fold>
}
