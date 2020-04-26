
package MailUtility;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtility {
    
    public static void sendMail(String recepient) throws MessagingException{
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccountEmail = "printf.shoaib@gmail.com";
        String password = "123Shahzad123";
        
        Session session = Session.getInstance(properties, new Authenticator(){
           @Override
           protected PasswordAuthentication getPasswordAuthentication(){
               return new PasswordAuthentication(myAccountEmail, password);
           }
        });
        
        Message message = prepareMessage(session, myAccountEmail, recepient);
        
        Transport.send(message);
        System.out.println("Email Sent Successfully");
    }
    
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Approval Message From Tele-Medi-Care System");
            
            String htmlCode = "<h1>Dear Doctor, Welcome to the System.</h1>  <h2>We Hope You the Best Luck</h2>";
            message.setContent(htmlCode, "text/html");
            
            return message;
        }catch(Exception e){
            System.out.println("Problem Occured While Sending the Email");
        }
        return null;
    }
}











