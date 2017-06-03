package be.mira.jongeren.mailinglist.service;

import be.mira.jongeren.mailinglist.domain.SubscriptionCount;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import be.mira.jongeren.mailinglist.repository.SubscriptionCountRepository;
import be.mira.jongeren.mailinglist.repository.SubscriptionListRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class SubscriptionCountAdvice {

    @Autowired
    private SubscriptionListRepository subscriptionListRepository;

    @Autowired
    private SubscriptionCountRepository subscriptionCountRepository;

    @AfterReturning("execution(* be.mira.jongeren.mailinglist.service.SubscriberService.unsubscribe(..))" +
                    " || " +
                    "execution(* be.mira.jongeren.mailinglist.service.SubscriberService.activate(..)) ")
    public void updateSubscriptionCounts() {
        List<SubscriptionList> all = subscriptionListRepository.findAll();
        for (SubscriptionList list : all) {
            SubscriptionCount latestSubscriptionCount = subscriptionCountRepository.findTopSubscriptionCount(list);

            // If the count didnt change then don't create a new entry.
            if(latestSubscriptionCount == null || list.count() != latestSubscriptionCount.getCount()){
                SubscriptionCount count = new SubscriptionCount(list, list.count());
                subscriptionCountRepository.save(count);
            }
        }
    }
}
