package com.atom.tool.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

/**
 * send multi part internet email messages with attachments.
 *
 * @author Atom
 */
public class MultiPartEmailSender extends AbstractMailSender {


    public MultiPartEmailSender(String subject, String content, String[] to) {
        super(subject, content, to);
    }

    @Override
    protected Email createEmail() {
        return new MultiPartEmail();
    }

    @Override
    protected void setContent(Email email, String content) throws EmailException {
        email.setMsg(content);
    }
}
