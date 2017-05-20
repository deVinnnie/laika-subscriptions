package be.mira.jongeren.mailinglist.service;

import be.mira.jongeren.mailinglist.domain.Subscriber;

public interface SubscriberService {

    void subscribe(Subscriber subscriber, String[] lists);

    void unsubscribe(Subscriber subscriber);

    void unsubscribe(Long id);

    boolean activate(Long id, String token);
}
