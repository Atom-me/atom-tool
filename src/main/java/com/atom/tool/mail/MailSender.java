package com.atom.tool.mail;

/**
 * the mail sender
 *
 * @author Atom
 */
public interface MailSender {

    /**
     * CC : carbon copy.
     *
     * @param cc
     */
    void addCc(String[] cc);

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
    void addBcc(String[] bcc);

    /**
     * add the attachment
     * You can also use EmailAttachment to reference any valid URL for files that you do not have locally.
     * When the message is sent, the file will be downloaded and attached to the message automatically.
     *
     * @param path the path of attachment
     */
    void addAttachment(String path);

    /**
     * send the email
     */
    void send();
}
