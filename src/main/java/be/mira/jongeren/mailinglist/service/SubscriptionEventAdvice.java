package be.mira.jongeren.mailinglist.service;

import be.mira.jongeren.mailinglist.domain.SubscriptionEvent;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import be.mira.jongeren.mailinglist.repository.SubscriptionEventRepository;
import be.mira.jongeren.mailinglist.repository.SubscriptionListRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Aspect
@Component
public class SubscriptionEventAdvice {

    @Inject
    private SubscriptionListRepository subscriptionListRepository;

    @Inject
    private SubscriptionEventRepository subscriptionEventRepository;

    @AfterReturning("execution(* be.mira.jongeren.mailinglist.service.SubscriberService.unsubscribe(..))" +
                    " || " +
                    "execution(* be.mira.jongeren.mailinglist.service.SubscriberService.activate(..)) ")
    public void generateEvents() {
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
}
