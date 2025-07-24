package utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
    public static void sendEmail(String to, String subject, String body) {
        // Cấu hình thông tin gmail
        final String username = "datnt0105@gmail.com"; // Gmail của bạn
        final String password = "mhxp bzky nqjg tpki";   // Mật khẩu ứng dụng

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo session
        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            // Tạo message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Sử dụng setContent để gửi HTML
            message.setContent(body, "text/html; charset=UTF-8");

            // Gửi mail
            Transport.send(message);

            System.out.println("Gửi mail thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}