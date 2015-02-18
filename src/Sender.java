/**
 * Created by xhplogvievg_ on 04.02.2015.
 */
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sender {
    private Properties props;

    public Sender(String smtp, String smtpPort) {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
    }

    public void send(final String username, final String password, ArrayList<String> st, String fromEmail, String toEmail){

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("aka47Hitman@gmail.com", "sisi47sisi");
            }
        });
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aka47Hitman@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("aka47Hitman@mail.ru"));
            message.setSubject("Error on server!!!");
            message.setText(String.valueOf(st));

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}