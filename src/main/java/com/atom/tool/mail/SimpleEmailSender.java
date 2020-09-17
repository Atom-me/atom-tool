package com.atom.tool.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * send simple internet email messages without attachments.
 *
 * @author Atom
 */
public class SimpleEmailSender extends AbstractMailSender {


    public SimpleEmailSender(String subject, String content, String[] to) {
        super(subject, content, to);
    }

    @Override
    protected Email createEmail() {
        return new SimpleEmail();
    }

    @Override
    protected void setContent(Email email, String content) throws EmailException {
        email.setMsg(content);
    }
}
