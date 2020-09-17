package com.atom.tool.mail;

import com.atom.tool.mail.util.MailUtil;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Atom
 */
@Slf4j
public abstract class AbstractMailSender implements MailSender {

    private final Email email = createEmail();

    private final String subject;
    private final String content;
    private final String[] to;

    public AbstractMailSender(String subject, String content, String[] to) {
        this.subject = subject;
        this.content = content;
        this.to = to;
    }

    /**
     * CC : carbon copy.
     *
     * @param cc
     */
    @Override
    public void addCc(String[] cc) {
        for (String address : cc) {
            if (ArrayUtils.isNotEmpty(cc)) {
                try {
                    email.addCc(MailUtil.encodeAddress(address));
                } catch (EmailException e) {
                    log.error("add cc error! [{}]", Throwables.getStackTraceAsString(e));
                }
            }
        }
    }

    /**
     * BCC : blind carbon copy.
     * <p>
     * Just like CC, BCC is a way of sending copies of an email to other people.
     * The difference between the two is that, while you can see a list of recipients when CC is used,
     * that’s not the case with BCC.
     * It’s called blind carbon copy because the other recipients won’t be able to see that someone else has been sent a copy of the email.
     *
     * @param bcc
     */
    @Override
    public void addBcc(String[] bcc) {
        for (String address : bcc) {
            if (ArrayUtils.isNotEmpty(bcc)) {
                try {
                    email.addBcc(MailUtil.encodeAddress(address));
                } catch (EmailException e) {
                    log.error("add bcc error！[{}]", Throwables.getStackTraceAsString(e));
                }
            }
        }
    }

    /**
     * add the attachment
     * You can also use EmailAttachment to reference any valid URL for files that you do not have locally.
     * When the message is sent, the file will be downloaded and attached to the message automatically.
     *
     * @param path the path of attachment
     */
    @Override
    public void addAttachment(String path) {
        try {
            if (email instanceof MultiPartEmail) {
                // create attachment
                if (StringUtils.isNotBlank(path)) {
                    //Create the attachment
                    EmailAttachment emailAttachment = new EmailAttachment();
                    if (path.startsWith("http")) {
                        emailAttachment.setURL(new URL(path));
                    } else {
                        emailAttachment.setPath(path);
                    }
                    emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
                    emailAttachment.setDescription("this is just an attachment desc");
                    emailAttachment.setName(path.substring(path.lastIndexOf("/") + 1));

                    MultiPartEmail multiPartEmail = (MultiPartEmail) email;
                    //add the attachment
                    multiPartEmail.attach(emailAttachment);
                }
            }
        } catch (MalformedURLException e) {
            log.error("create attachment URL  error！[{}]", Throwables.getStackTraceAsString(e));
        } catch (EmailException e) {
            log.error("add attachment error！[{}]", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * send the email
     */
    @Override
    public void send() {
        try {
            // check protocol
            if (!"smtp".equalsIgnoreCase(MailConstant.Sender.PROTOCOL)) {
                log.error("not support this protocol [{}], now only support smtp protocol！", MailConstant.Sender.PROTOCOL);
                return;
            }
            // set SSL
            if (MailConstant.Sender.IS_SSL) {
                email.setSSLOnConnect(true);
            }
            // set hostname and port
            email.setHostName(MailConstant.Sender.HOST);
            email.setSmtpPort(MailConstant.Sender.PORT);
            // set authentication
            if (MailConstant.Sender.IS_AUTH) {
                email.setAuthentication(MailConstant.Sender.AUTH_USERNAME, MailConstant.Sender.AUTH_PASSWORD);
            }
            // open debug mode for display debug information
            if (MailConstant.IS_DEBUG) {
                email.setDebug(true);
            }
            // set sender from
            if (StringUtils.isNotEmpty(MailConstant.Sender.FROM.trim())) {
                email.setFrom(MailUtil.encodeAddress(MailConstant.Sender.FROM));
            }
            // set send to
            for (String address : to) {
                email.addTo(MailUtil.encodeAddress(address));
            }
            // set subject
            email.setSubject(subject);
            // set content ,implement in subClass
            setContent(email, content);
            // send email
            String sendResult = email.send();
            System.err.println(sendResult);
        } catch (EmailException e) {
            log.error("send email error! [{}]", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * create special email instance {@link Email}
     *
     * @return
     * @see org.apache.commons.mail.HtmlEmail
     * @see org.apache.commons.mail.ImageHtmlEmail
     * @see MultiPartEmail
     * @see org.apache.commons.mail.SimpleEmail
     */
    protected abstract Email createEmail();

    /**
     * set email content
     *
     * @param email
     * @param content
     * @throws EmailException
     */
    protected abstract void setContent(Email email, String content) throws EmailException;
}
