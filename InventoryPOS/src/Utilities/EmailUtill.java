/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Desktop;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import restaurantpos.InventoryPOS;

import java.net.URI;
import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.net.*;
import java.net.URL;

/**
 *
 * @author Hajjaz
 */
public class EmailUtill {

    //EmailUtill.sendMail("hajjaz_aust@yahoo.com", "Invoice Created", "Auto generated email");
    // sends mail
    public static void sendMail(String to, String subject, String email_body) {

        Properties props = new Properties();
        props.put("mail.smtp.host", InventoryPOS.smtpHost);
        props.put("mail.smtp.socketFactory.port", InventoryPOS.smtpPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", InventoryPOS.smtpPort);

        final String username = InventoryPOS.emailUserName;
        final String password = InventoryPOS.emailPassword;

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            //message.setText(email_body);
            message.setContent(email_body, "text/html; charset=utf-8");
            Transport.send(message);
            System.out.println("message sent");
            //JOptionPane.showMessageDialog(null, "Email Sent!", "Sent", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println(e);
            //JOptionPane.showMessageDialog(null, e.toString());
        }
    }
}
