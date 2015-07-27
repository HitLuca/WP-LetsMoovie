package utilities.mail;

import utilities.mail.request.UserEmailRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Created by marco on 25/06/15.
 */
public class MailCleanerThread extends Thread {
    private final long CLEANROUTINETIME = 60 * 10;
    private Map<String, UserEmailRequest> pendingRequests;
    private Semaphore mutex;
    private Semaphore noRequest;


    public MailCleanerThread()
    {
        pendingRequests=new HashMap<String, UserEmailRequest>();
        mutex = new Semaphore(1,true);
        noRequest = new Semaphore(0,true);
    }

    public void add(String verificationCode,UserEmailRequest request)
    {
        try {
            mutex.acquire();
            pendingRequests.put(verificationCode, request);
            mutex.release();
            noRequest.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try {
            while (true) {
                noRequest.acquire();
                mutex.acquire();
                while (pendingRequests.size() > 0) {
                    Date currentDate = new Date();
                    for (UserEmailRequest request : pendingRequests.values()) {
                        if (currentDate.getTime() - request.getExpireDate() > 0) {
                            pendingRequests.remove(request);
                        }
                    }
                    mutex.release();
                    sleep(CLEANROUTINETIME * 1000l);
                    mutex.acquire();
                }
                mutex.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public UserEmailRequest getUserEmailRequest(String verificationCode) {
        UserEmailRequest userEmailRequest = null;
        try {
            mutex.acquire();
            userEmailRequest = pendingRequests.get(verificationCode);
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userEmailRequest;
    }

    public boolean checkUsername(String username) throws InterruptedException {
        mutex.acquire();
        for(UserEmailRequest userEmailRequest: pendingRequests.values())
        {
            if(userEmailRequest.getUsername().equals(username))
            {
                mutex.release();
                return false;
            }
        }
        mutex.release();
        return true;
    }

    public boolean checkEmail(String email) throws InterruptedException {
        mutex.acquire();
        for(UserEmailRequest userEmailRequest: pendingRequests.values())
        {
            if(userEmailRequest.getEmail().equals(email))
            {
                mutex.release();
                return false;
            }
        }
        mutex.release();
        return true;
    }

    public void remove(String verificationCode) throws InterruptedException {
        mutex.acquire();
        pendingRequests.remove(verificationCode);
        mutex.release();
    }
}
