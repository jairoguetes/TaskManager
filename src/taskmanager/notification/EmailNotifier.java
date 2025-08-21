package taskmanager.notification;

public class EmailNotifier implements Notifier {
    private String emailAddress;

    public EmailNotifier(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void send(String message) {
        System.out.println("Sending email to " + emailAddress + ": " + message);
    }
}