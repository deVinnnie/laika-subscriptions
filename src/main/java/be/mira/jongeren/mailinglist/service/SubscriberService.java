package be.mira.jongeren.mailinglist.service;

import be.mira.jongeren.mailinglist.domain.Subscriber;
import org.springframework.stereotype.Service;

public interface SubscriberService {

    void subscribe(Subscriber subscriber);

    boolean activate(Long id, String token);
}
