package com.atom.tool;

import com.atom.tool.mail.MailSender;
import com.atom.tool.mail.MultiPartEmailSender;

/**
 * @author Atom
 */
public class MailSenderTest {
    private static final String subject = "this my subject";
    private static final String content = "this is an MultiPartEmail with attachment";
    private static final String[] to = {"sarming@126.com"};

    public static void main(String[] args) {
        MailSender mailSender = new MultiPartEmailSender(subject, content, to);
        mailSender.addAttachment("https://pic4.zhimg.com/v2-588b5a5dd202c4ec9c7f258df5df2f79_r.jpg");
        mailSender.send();
        System.out.println("send success！");
    }
}
