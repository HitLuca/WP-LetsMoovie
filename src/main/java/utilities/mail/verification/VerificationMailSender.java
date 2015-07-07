package utilities.mail.verification;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import json.register.request.RegistrationRequest;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

/**
 * Created by marco on 25/06/15.
 */
public class VerificationMailSender {
    private final int MAIL_TIME = 120;
    private final int SECURE_CODE_SIZE = 30;
    private VerificationMailCleanerThread verificationMailCleanerThread;
    private SendGrid sendgrid;

    public VerificationMailSender()
    {
        verificationMailCleanerThread = new VerificationMailCleanerThread();
        verificationMailCleanerThread.start();
        String api_user = System.getenv("API_USER");
        String api_key = System.getenv("API_KEY");
        sendgrid = new SendGrid(api_user, api_key);
    }

    public boolean sendEmail(RegistrationRequest registrationRequest, String url)
    {
        long expirationDate = new Date().getTime()+MAIL_TIME*1000;
        String verificationCode = RandomStringUtils.randomAlphanumeric(SECURE_CODE_SIZE);
        SendGrid.Email email = new SendGrid.Email();

        email.addTo(registrationRequest.getEmail());
        email.setFrom("info@letsmoovie.com");
        email.setSubject("Verify your account");
        email.setTemplateId("62710ec1-548f-4b62-a4fa-757187194b9f");
        email.setText("Benvenuto "+registrationRequest.getUsername()+"\nClicca sul link per confermare la registrazione "+url + "?verificationCode=" + verificationCode);

        try {
            System.out.println(sendgrid.send(email).getMessage());
        } catch (SendGridException e) {
            return false;
        }

        verificationMailCleanerThread.add(verificationCode,new UserRegistrationRequest(registrationRequest,expirationDate));
        return true;
    }

    public RegistrationRequest verify(String verificationCode)
    {
        UserRegistrationRequest request = verificationMailCleanerThread.getUserRegistrationRequest(verificationCode);
        if(request==null)
        {
            return null;
        }
        else if((new Date()).getTime()-request.getExpireDate()>0)
        {
            return null;
        }
        else
        {
            return request.getRegistrationRequest();
        }
    }
}
