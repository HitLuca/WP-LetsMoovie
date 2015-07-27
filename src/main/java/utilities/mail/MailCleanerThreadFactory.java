package utilities.mail;

/**
 * Created by marco on 08/07/15.
 */
public class MailCleanerThreadFactory {
    private static MailCleanerThread mailCleanerThread;
    public static MailCleanerThread getMailCleanerThread()
    {
        if (mailCleanerThread == null)
        {
            mailCleanerThread = new MailCleanerThread();
            mailCleanerThread.run();
        }
        return  mailCleanerThread;
    }
}
