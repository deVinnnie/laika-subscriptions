package be.mira.jongeren.mailinglist.service;

import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.domain.SubscriptionEvent;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import be.mira.jongeren.mailinglist.mail.ActivationMailTemplate;
import be.mira.jongeren.mailinglist.repository.SubscriberRepository;
import be.mira.jongeren.mailinglist.repository.SubscriptionEventRepository;
import be.mira.jongeren.mailinglist.repository.SubscriptionListRepository;
import be.mira.jongeren.mailinglist.util.TokenGenerator;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class SubscriberServiceImpl implements SubscriberService {

    @Inject
    private SubscriberRepository subscriberRepository;

    @Inject
    private SubscriptionListRepository subscriptionListRepository;

    @Inject
    private TokenGenerator tokenGenerator;

    @Inject
    private SubscriptionEventRepository subscriptionEventRepository;

    private MailSender mailSender;

    @Inject
    public SubscriberServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public Subscriber subscribe(Subscriber subscriber, String[] lists){
        // Set the current date as subscription date.
        subscriber.setSubscriptionDate(LocalDateTime.now());

        // Generate token
        String token = tokenGenerator.generate();
        subscriber.setToken(token);

        // Persist in database
        subscriberRepository.save(subscriber);

        for(String listTitle : lists) {
            SubscriptionList list = subscriptionListRepository.findByTitle(listTitle);
            list.add(subscriber);
        }

        // Prepare Mail
        /*ActivationMailTemplate mailTemplate = new ActivationMailTemplate();
        String body = null;
        try {
            body = mailTemplate.format(subscriber.getToken(), InetAddress.getLocalHost().getHostName()+"/activate/"+subscriber.getId());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/

        //mailSender.send(subscriber.getEmail(), body);
        return subscriber;
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
        this.generateEvents();
    }

    @Override
    public void unsubscribe(Long id){
        unsubscribe(subscriberRepository.getOne(id));
    }

    public boolean activate(Long id, String token){
        Subscriber subscriber = subscriberRepository.getOne(id);
        subscriber.activate(token);

        this.generateEvents();

        return subscriber.isActive();
    }

    private void generateEvents() {
        List<SubscriptionList> all = subscriptionListRepository.findAll();
        for (SubscriptionList list : all) {
            SubscriptionEvent latestSubscriptionEvent = subscriptionEventRepository.findTopSubscriptionEvent(list);

            // If the count didnt change then don't create a new entry.
            if(latestSubscriptionEvent == null || list.count() != latestSubscriptionEvent.getCount()){
                SubscriptionEvent count = new SubscriptionEvent(list, list.count());
                subscriptionEventRepository.save(count);
            }
        }
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
