package utilities.mail;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import json.register.request.RegistrationRequest;
import org.apache.commons.lang3.RandomStringUtils;
import utilities.mail.request.UserRegistrationRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by marco on 25/06/15.
 */
public class VerificationMailSender {
    private final int MAIL_TIME = 120;
    private final int SECURE_CODE_SIZE = 30;
    private MailCleanerThread mailCleanerThread;
    private SendGrid sendgrid;
    private boolean sendEmail = false;

    public VerificationMailSender(MailCleanerThread mailCleanerThread)
    {
        this.mailCleanerThread = mailCleanerThread;
        String api_user = System.getenv("API_USER");
        String api_key = System.getenv("API_KEY");
        sendgrid = new SendGrid(api_user, api_key);
        try {
            sendEmail = System.getenv("SEND_EMAIL").equals("TRUE");
        } catch (Exception e) {}
    }

    public List<String> checkDuplicates(RegistrationRequest registrationRequest)  {
        List<String> duplicates = new ArrayList<>();
        try {
            if (mailCleanerThread.checkUsername(registrationRequest.getUsername())) {
                duplicates.add("username");
            }
            if (mailCleanerThread.checkEmail(registrationRequest.getEmail())) {
                duplicates.add("email");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return duplicates;
    }

    public boolean sendEmail(RegistrationRequest registrationRequest, String url)
    {
        long expirationDate = new Date().getTime()+MAIL_TIME*1000;
        String verificationCode = RandomStringUtils.randomAlphanumeric(SECURE_CODE_SIZE);
        SendGrid.Email email = new SendGrid.Email();

        url+=verificationCode;

        email.addTo(registrationRequest.getEmail());
        email.setFrom("info@letsmoovie.com");
        email.setSubject("Verify your account");
        email.setTemplateId("fa28abaf-6b95-44a2-b4a2-d4f21fe730c8");
        email.setText("Benvenuto "+registrationRequest.getUsername()+"\nClicca "+url+" per confermare la registrazione");

        if(sendEmail) {
            try {
                sendgrid.send(email).getMessage();
            } catch (SendGridException e) {
                return false;
            }
        }

        mailCleanerThread.add(verificationCode,new UserRegistrationRequest(registrationRequest,expirationDate));
        return true;
    }


}
