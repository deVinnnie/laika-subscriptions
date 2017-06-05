package be.mira.jongeren.mailinglist.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
public class SubscriptionCount extends AbstractEntity{

    @ManyToOne
    private SubscriptionList subscriptionList;

    @Min(0)
    private long count;

    private LocalDateTime timestamp;

    public SubscriptionCount() {}

    public SubscriptionCount(SubscriptionList subscriptionList, long count) {
        this.count = count;
        this.subscriptionList = subscriptionList;
        this.timestamp = LocalDateTime.now();
    }

    public SubscriptionList getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(SubscriptionList subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    public long getCount() {
        return count;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
