package com.rahul.sevices;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Rahul Sawant
 * Class with sendMail server (mail server details are picked from app.prop file)
 */
public class SendMailService {

    protected static MimeMessage mimeMsg;
    private static String senderMailId;
    private static String smtpHostName;
    private static String contentType;
    private static final String hostNameKey="mail.smtp.host";
    private static final String senderKey="mail.smtp.sender";
    private static final String contentTypeKey="mail.smtp.content.type";
    private static final String contentEncodingKey="mail.smtp.content.encoding";

    static{
        //Load values from properties file
        LoadAppPropertiesService propObj=new LoadAppPropertiesService();
        HashMap<String,Object> propMap=propObj.getAppProperties(null);
        smtpHostName=(String) propMap.get(hostNameKey);
        senderMailId=(String) propMap.get(senderKey);
        contentType=(String) propMap.get(contentTypeKey);

        Properties props = System.getProperties();
        props.put(hostNameKey,smtpHostName );
        Session session = Session.getInstance(props, null);

        mimeMsg=new MimeMessage(session);
        try {
            System.out.println(contentType);
            mimeMsg.addHeader("Content-type", contentType);
            mimeMsg.addHeader("format", "flowed");
            mimeMsg.addHeader("Content-Transfer-Encoding", contentEncodingKey);
            mimeMsg.setHeader("X-Priority", "1");
            mimeMsg.setFrom(new InternetAddress(senderMailId, "NoReply-JD"));
            mimeMsg.setReplyTo(InternetAddress.parse(senderMailId, false));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void sendEmail(String toEmail, String subject, String body){

        try {
            mimeMsg.setSubject(subject, "UTF-8");
            mimeMsg.setContent(body,contentType);
            mimeMsg.setSentDate(new Date());
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(mimeMsg);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("EMail Sent Successfully!!");
    }
}
