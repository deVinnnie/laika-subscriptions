package be.mira.jongeren.mailinglist.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subscriber extends AbstractEntity{

    @Size(max=255)
    private String voornaam = "";

    @Size(max=255)
    private String achternaam = "";

    @Email
    @NotEmpty
    private String email = "";

    private LocalDateTime subscriptionDate;

    private Boolean active = false;

    @Size(max=16)
    private String token;

    @ManyToMany(mappedBy="subscribers")
    private List<SubscriptionList> subscriptions = new ArrayList<>();

    public Subscriber(){
        this.subscriptionDate = LocalDateTime.now();
    }

    public Subscriber(String email){
        this();
        this.email = email;
    }

    public Subscriber(String voornaam, String achternaam, String email) {
        this(email);
        this.voornaam = voornaam;
        this.achternaam = achternaam;
    }

    public Subscriber(String voornaam, String achternaam, String email, LocalDateTime subscriptionDate) {
        this(voornaam, achternaam, email);
        this.subscriptionDate = subscriptionDate;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isActive() {
        return active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void activate(String token) {
        if(this.getToken() != null && this.getToken().equals(token)) {
            this.setActive(true);
        }
    }

    public List<SubscriptionList> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionList> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + this.getId() +
                ", voornaam='" + voornaam + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", email='" + email + '\'' +
                ", subscriptionDate=" + subscriptionDate +
                ", active=" + active +
                '}';
    }

    public void removeSubscription(SubscriptionList subscriptionList) {
        this.subscriptions.remove(subscriptionList);
    }
}
