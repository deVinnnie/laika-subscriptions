package be.mira.jongeren.mailinglist.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
* Collection of all Subscribers interested in this subject.
* 
* In practice there will exist two lists, one for each age category:
* - `main-sequence` : For ages 9 till 14.
* - `supernova` : For ages 14 till 18 and above.
*
* A single Subscriber can appear in multiple SubscriptionLists.
*
*/
@Entity
public class SubscriptionList extends AbstractEntity{

    @Column(unique=true)
    @NotEmpty
    private String title;

    @ManyToMany
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

    public void remove(Subscriber subscriber){
        this.subscribers.remove(subscriber);
    }

    public void add(Subscriber subscriber){
        this.subscribers.add(subscriber);
    }

    /**
     *
     * @return Number of active Subscribers.
     */
    public long count(){
        return this.subscribers
                .stream()
                .filter(s -> s.isActive())
                .count();
    }

    public long getCount(){
        return count();
    }

    @Override
    public String toString() {
        return "SubscriptionList{" +
                "title='" + title + '\'' +
                ", subscribers=" + subscribers +
                '}';
    }
}
