package com.atom.tool.mail;

import com.atom.tool.core.FreemarkerUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * send  HTML multipart email messages.
 *
 * <p>This {@link HtmlEmail} class also inherits from {@link MultiPartEmail}, so it is easy to
 * add attachments to the email.
 *
 * @author Atom
 */
@Slf4j
public class HtmlEmailSender extends AbstractMailSender {


    public HtmlEmailSender(String subject, String content, String[] to) {
        super(subject, content, to);
    }

    @Override
    protected Email createEmail() {
        return new HtmlEmail();
    }

    @Override
    protected void setContent(Email email, String content) {
        try {
            HtmlEmail htmlEmail = (HtmlEmail) email;
            // html message could contains html tags
            htmlEmail.setHtmlMsg(content);
            // set the alternative message
            htmlEmail.setTextMsg("Your email client does not support HTML messages");
        } catch (Exception e) {
            log.error("set html content error! [{}]", Throwables.getStackTraceAsString(e));
        }
    }
}
