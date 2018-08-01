package security.authentication.updatePassword;

import com.hecticus.utils.basic.Notify;
import play.libs.mailer.MailerClient;

import javax.inject.Inject;

/**
 * Created by nisa on 02/11/17.
 */
public class Mail {

    MailerClient mailerClient;

    public String subject;
    public String content;
    public String fromUser;
    public String fromEmail;
    public String toEmail;
    public String toUser;

    public Mail(MailerClient mailerClient, String fromEmail, String fromUser, String toEmail, String toUser, String pin, Integer expiresIn){
        this.mailerClient = mailerClient;
        this.subject = fromUser + " - Password Recovery";
        this.content = "" +
                "<h3>" + fromUser + "</h3>" +
                "<div>Hello! " + toUser + ".</div>" +
                "<div>Your temporal PIN is: <strong>"+ pin + "</strong> (This PIN expires in " + expiresIn + " minutes).</div>" +
                "<div>Thank you for using our services.</div>";
        this.fromEmail = fromEmail;
        this.fromUser = fromUser;
        this.toEmail = toEmail;
        this.toUser = toUser;
    }

    public void send() throws Exception {
        Notify notify = new Notify(mailerClient);
        notify.sendEmail(this.content, this.toEmail, this.fromEmail, this.subject);
    }
}
