package be.mira.jongeren.mailinglist.mail;

public class ActivationMailTemplate {

    private String template = "Beste, \n" +
            "Je hebt je ingeschreven op onze mailinglijst. (Als je dit niet gedaan hebt, dan kan je deze mail negeren.)\n" +
            "\n" +
            "Om je inschrijving te voltooien moet je volgende activatiecode: %s\n" +
            "kopiëren op deze pagina: %s\n" +
            "\n" +
            "Met Vriendelijke Groeten,\n" +
            "Laika.\n" +
            "\n" +
            "--\n" +
            "Laika is de automatische mailer van de MIRA Jeugdkern. Laika is heel verlegen en antwoord daarom niet op mails.\n";

    public String format(String token, String activationPageLink){
        if(token == null || activationPageLink == null){
            return null;
        }
        return String.format(template, token, activationPageLink);
    }
}
