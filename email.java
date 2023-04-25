import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    
    public static void main(String[] args) {
        String recipient = "sales@cabin4j.com"; // recipient's email address
        String subject = "Challenges Completed"; // email subject
        String name = "Prashant Ojha,IT branch second year student,Roll NO-2100290130131,Email- prashantkumarojha2002@gmail.com"; // name to be included in the email body
        String mobile = "8737982048"; // mobile number to be included in the email body
        String imagePath = "file:///C:/Users/ASUS/Downloads/uploadphoto.pdf"; // path to the passport size image file
        
        // create a Properties object to hold the SMTP server information
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // replace with your SMTP server address
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587"); // replace with your SMTP server port
        
        // create a Session object to authenticate the sender's credentials
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("prashantkumarojha2002@gmail.com", "cakebirthday@321"); // replace with your sender's email and password
            }
        });
        
        try {
            // create a new MimeMessage object
            MimeMessage message = new MimeMessage(session);
            
            // set the sender's email address
            message.setFrom(new InternetAddress("prashantkumarojha2002@gmail.com")); // replace with your sender's email
            
            // set the recipient's email address
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            
            // set the email subject
            message.setSubject(subject);
            
            // create a MimeMultipart object to hold the email body and attachment
            Multipart multipart = new MimeMultipart();
            
            // create a MimeBodyPart object for the email body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            
            // set the email body content
            String body = "Name: " + name + "\nMobile Number: " + mobile;
            messageBodyPart.setText(body);
            
            // add the email body to the MimeMultipart object
            multipart.addBodyPart(messageBodyPart);
            
            // create a MimeBodyPart object for the passport size image attachment
            messageBodyPart = new MimeBodyPart();
            
            // set the passport size image file path
            DataSource source = new FileDataSource(imagePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("passport.jpg"); // set the attachment file name
            
            // add the passport size image attachment to the MimeMultipart object
            multipart.addBodyPart(messageBodyPart);
            
            // set the MimeMultipart object as the email content
            message.setContent(multipart);
            
            // send the email
            Transport.send(message);
            
            System.out.println("Email sent successfully.");
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
