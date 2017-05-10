package be.mira.jongeren.mailinglist.domain;

import be.mira.jongeren.mailinglist.util.date.Past;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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

    private LocalDateTime subscriptionDate;

    private Boolean active = false;

    @Size(max=16)
    private String token;

    public Subscriber(){
        this.subscriptionDate = LocalDateTime.now();
    }

    public Subscriber(String voornaam, String achternaam, String email) {
        this();
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
    }

    public Subscriber(String voornaam, String achternaam, String email, LocalDateTime subscriptionDate) {
        this(voornaam, achternaam, email);
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
}
