package utilities.mail;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import org.apache.commons.lang3.RandomStringUtils;
import utilities.mail.request.UserEmailRequest;
import utilities.mail.request.UserRegistrationRequest;

import java.util.Date;

/**
 * Created by marco on 09/07/15.
 */
public class PasswordRecoveryCodeCheck {
    private MailCleanerThread mailCleanerThread;

    public PasswordRecoveryCodeCheck(MailCleanerThread mailCleanerThread)
    {
        this.mailCleanerThread = mailCleanerThread;
    }

    public String verify(String verificationCode)
    {
        UserEmailRequest request = mailCleanerThread.getUserEmailRequest(verificationCode);
        if(request==null)
        {
            return null;
        }
        else if((new Date()).getTime()-request.getExpireDate()>0)
        {
            try {
                mailCleanerThread.remove(verificationCode);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        else
        {
            if(!(request instanceof UserRegistrationRequest)) {
                try {
                    mailCleanerThread.remove(verificationCode);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return request.getUsername();
            }
            else
            {
                return null;
            }
        }
    }
}
