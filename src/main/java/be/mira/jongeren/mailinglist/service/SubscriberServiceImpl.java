package be.mira.jongeren.mailinglist.service;

import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import be.mira.jongeren.mailinglist.repository.SubscriberRepository;
import be.mira.jongeren.mailinglist.repository.SubscriptionListRepository;
import be.mira.jongeren.mailinglist.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class SubscriberServiceImpl implements SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriptionListRepository subscriptionListRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    private MailSender mailSender;

    @Autowired
    public SubscriberServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void subscribe(Subscriber subscriber, String[] lists){
        // Set the current date as subscription date.
        subscriber.setSubscriptionDate(LocalDateTime.now());

        // Generate token
        String token = tokenGenerator.generate();
        subscriber.setToken(token);

        // Persist in database
        subscriberRepository.save(subscriber);

        for(String listTitle : lists) {
            List<SubscriptionList> all = subscriptionListRepository.findAll();
            SubscriptionList list = subscriptionListRepository.findByTitle(listTitle);
            list.add(subscriber);
        }

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

    @Override
    public void unsubscribe(Subscriber subscriber){
        Iterator<SubscriptionList> iterator = subscriber.getSubscriptions().iterator();
        while(iterator.hasNext()){
            SubscriptionList subscriptionList = iterator.next();
            subscriptionList.remove(subscriber);
            iterator.remove();
        }

        subscriberRepository.delete(subscriber);
    }

    @Override
    public void unsubscribe(Long id){
        unsubscribe(subscriberRepository.findOne(id));
    }

    public boolean activate(Long id, String token){
        Subscriber subscriber = subscriberRepository.getOne(id);
        subscriber.activate(token);
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
