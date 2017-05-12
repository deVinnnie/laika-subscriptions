package be.mira.jongeren.mailinglist.service;

public interface MailSender {

    void send(String toMailAddress, String body);
}
