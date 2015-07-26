package utilities.mail;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import org.apache.commons.lang3.RandomStringUtils;
import utilities.mail.request.UserEmailRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by marco on 09/07/15.
 */
public class TicketMail {
    private SendGrid sendgrid;
    private boolean sendEmail;

    public TicketMail() {
        String api_user = System.getenv("API_USER");
        String api_key = System.getenv("API_KEY");
        sendgrid = new SendGrid(api_user, api_key);
        try {
            sendEmail = System.getenv("SEND_EMAIL").equals("TRUE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean sendEmail(String userEmail, String username, String filmName, InputStream is) throws IOException {
        SendGrid.Email email = new SendGrid.Email();

        email.addTo(userEmail);

        email.setFrom("info@letsmoovie.com");
        email.setSubject("Biglietto Cinema: " + filmName);

        email.setText("Ciao " + username + "\nEcco i tuoi biglietti per lo spettacolo " + filmName + " del cinema Let's Moovie!" +
                "\nRicordati di portare un disposidivo con schermo capace di mostrare codici Qr, oppure di stampare il tuo biglietto!" +
                "\nLet's Moovie Team");

        email.addAttachment("Biglietto.pdf", is);
        if (sendEmail) {
            try {
                sendgrid.send(email).getMessage();
            } catch (SendGridException e) {
                return false;
            }
        }

        return true;
    }
}
