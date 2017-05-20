package be.mira.jongeren.mailinglist.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max=255)
    private String voornaam = "";

    @Size(max=255)
    private String achternaam = "";

    @Email
    private String email = "";

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

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", voornaam='" + voornaam + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", email='" + email + '\'' +
                ", subscriptionDate=" + subscriptionDate +
                ", active=" + active +
                '}';
    }
}
