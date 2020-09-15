package com.atom.tool.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

/**
 * 发送复杂邮件（带附件的）
 *
 * @author Atom
 */
public class TextMailSender extends AbstractMailSender {


    public TextMailSender(String subject, String content, String[] to) {
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
