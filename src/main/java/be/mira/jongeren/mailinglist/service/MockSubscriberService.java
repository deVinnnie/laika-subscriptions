package be.mira.jongeren.mailinglist.service;

import be.mira.jongeren.mailinglist.domain.Subscriber;
import be.mira.jongeren.mailinglist.repository.SubscriberRepository;
import be.mira.jongeren.mailinglist.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Profile("development")
@Transactional
public class MockSubscriberService implements SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Override
    public void subscribe(Subscriber subscriber) {
        System.out.println("[MIRA Jeugdkern] Mailinglijst inschrijving");


        String token = tokenGenerator.generate();
        subscriber.setToken(token);

        // Persist in database
        subscriberRepository.save(subscriber);
    }

    public boolean activate(Long id, String token){
        Subscriber subscriber = subscriberRepository.findOne(id);
        subscriber.activate(token);
        subscriberRepository.flush();
        return subscriber.isActive();
    }

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
}
