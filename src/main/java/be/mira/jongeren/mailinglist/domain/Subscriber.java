package be.mira.jongeren.mailinglist.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max=255)
    private String voornaam;

    @Size(max=255)
    private String achternaam;

    @Email
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Past
    private Calendar subscriptionDate;

    private Boolean active = false;

    @Size(max=16)
    private String token;

    public Subscriber() {
    }

    public Subscriber(String voornaam, String achternaam, String email, Calendar subscriptionDate) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.subscriptionDate = subscriptionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Calendar getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Calendar subscriptionDate) {
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
}
