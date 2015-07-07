package utilities.mail.verification;

import utilities.mail.verification.UserRegistrationRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Created by marco on 25/06/15.
 */
public class VerificationMailCleanerThread extends Thread {
    private Map<String, UserRegistrationRequest> pendingRequests;
    private Semaphore mutex;
    private Semaphore noRequest;
    private final long CLEANROUTINETIME = 60*10;

    @Override
    public void start()
    {
        pendingRequests=new HashMap<String, UserRegistrationRequest>();
        mutex = new Semaphore(1,true);
        noRequest = new Semaphore(0,true);
    }

    public void add(String verificationCode,UserRegistrationRequest request)
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
                    for (UserRegistrationRequest request : pendingRequests.values()) {
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

    public UserRegistrationRequest getUserRegistrationRequest(String verificationCode) {
        UserRegistrationRequest userRegistrationRequest = null;
        try {
            mutex.acquire();
            userRegistrationRequest = pendingRequests.get(verificationCode);
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userRegistrationRequest;
    }

    public boolean checkUsername(String username) throws InterruptedException {
        mutex.acquire();
        for(UserRegistrationRequest userRegistrationRequest: pendingRequests.values())
        {
            if(userRegistrationRequest.getRegistrationRequest().getUsername().equals(username))
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
        for(UserRegistrationRequest userRegistrationRequest: pendingRequests.values())
        {
            if(userRegistrationRequest.getRegistrationRequest().getEmail().equals(email))
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
