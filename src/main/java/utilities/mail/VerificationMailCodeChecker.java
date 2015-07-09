package utilities.mail;

import json.register.request.RegistrationRequest;
import utilities.mail.request.UserEmailRequest;
import utilities.mail.request.UserRegistrationRequest;

import java.util.Date;

/**
 * Created by marco on 09/07/15.
 */
public class VerificationMailCodeChecker {
    private MailCleanerThread mailCleanerThread;

    public VerificationMailCodeChecker(MailCleanerThread mailCleanerThread)
    {
        this.mailCleanerThread = mailCleanerThread;
    }

    public RegistrationRequest verify(String verificationCode)
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
            if(request instanceof UserRegistrationRequest) {
                try {
                    mailCleanerThread.remove(verificationCode);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ((UserRegistrationRequest)request).getRegistrationRequest();
            }
            else
            {
                return null;
            }
        }
    }
}
