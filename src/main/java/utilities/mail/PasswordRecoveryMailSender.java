package utilities.mail;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import org.apache.commons.lang3.RandomStringUtils;
import utilities.mail.request.UserEmailRequest;

import java.util.Date;

/**
 * Created by marco on 09/07/15.
 */
public class PasswordRecoveryMailSender {
    private final int MAIL_TIME = 600;
    private final int SECURE_CODE_SIZE = 30;
    private MailCleanerThread mailCleanerThread;
    private SendGrid sendgrid;
    private boolean sendEmail;

    public PasswordRecoveryMailSender(MailCleanerThread mailCleanerThread)
    {
        this.mailCleanerThread = mailCleanerThread;
        String api_user = System.getenv("API_USER");
        String api_key = System.getenv("API_KEY");
        sendgrid = new SendGrid(api_user, api_key);
        try {
            sendEmail = System.getenv("SEND_EMAIL").equals("TRUE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean sendEmail(String userEmail, String username, String url)
    {
        long expirationDate = new Date().getTime()+MAIL_TIME*1000;
        String verificationCode = RandomStringUtils.randomAlphanumeric(SECURE_CODE_SIZE);
        SendGrid.Email email = new SendGrid.Email();

        url+=verificationCode;

        email.addTo(userEmail);
        email.setFrom("info@letsmoovie.com");
        email.setSubject("Password Recovery");
        email.setTemplateId("fa28abaf-6b95-44a2-b4a2-d4f21fe730c8");

        email.setText("Ciao "+username+" Abbiamo ricevuto una richiesta di cambio password, clicca Clicca "+url+" per procedere con l'operazione");

        if(sendEmail) {
            try {
                sendgrid.send(email).getMessage();
            } catch (SendGridException e) {
                return false;
            }
        }

        mailCleanerThread.add(verificationCode,new UserEmailRequest(expirationDate,username,userEmail));
        return true;
    }
}
