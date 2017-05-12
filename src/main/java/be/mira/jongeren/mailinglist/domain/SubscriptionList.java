package be.mira.jongeren.mailinglist.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class SubscriptionList {

    @Id
    private Long id;

    @Column(unique=true)
    private String title;

    @OneToMany
    private List<Subscriber> subscribers;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
