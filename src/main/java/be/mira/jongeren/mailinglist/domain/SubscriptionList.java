package be.mira.jongeren.mailinglist.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SubscriptionList extends AbstractEntity{

    @Column(unique=true)
    @NotEmpty
    private String title;

    @OneToMany
    private List<Subscriber> subscribers = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public void removeSubscriber(Subscriber subscriber){
        this.subscribers.remove(subscriber);
    }

}
