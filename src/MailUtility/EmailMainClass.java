
package MailUtility;

import javax.mail.MessagingException;

public class EmailMainClass {
    public static void main(String[] args) throws MessagingException {
        JavaMailUtility.sendMail("printf.shoaib@gmail.com");
    }
}
