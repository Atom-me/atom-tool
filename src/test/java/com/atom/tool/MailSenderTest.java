package com.atom.tool;

import com.atom.tool.mail.MailSender;
import com.atom.tool.mail.MultiPartEmailSender;
import com.atom.tool.mail.SimpleEmailSender;
import org.junit.Test;

/**
 * @author Atom
 */
public class MailSenderTest {

    @Test
    public void testMultiPartEmailSender() {
        String subject = "this my subject";
        String content = "this is an MultiPartEmail with attachment";
        String[] to = {"xxx@126.com"};
        MailSender multiPartEmailSender = new MultiPartEmailSender(subject, content, to);
        multiPartEmailSender.addAttachment("https://pic4.zhimg.com/v2-588b5a5dd202c4ec9c7f258df5df2f79_r.jpg");
        multiPartEmailSender.addAttachment("/Users/atom/testDir/test.sh");
        multiPartEmailSender.send();
        System.out.println("send success！");
    }

    @Test
    public void testSimpleEmailSender() {
        String subject = "this my subject";
        String content = "this is an simple email with attachment";
        String[] to = {"xxx@126.com"};
        MailSender multiPartEmailSender = new SimpleEmailSender(subject, content, to);
        /**
         * it won't be added,because the simple email sender not support attachment
         * {@link com.atom.tool.mail.AbstractMailSender#addAttachment(String)}
         */
        multiPartEmailSender.addAttachment("https://pic4.zhimg.com/v2-588b5a5dd202c4ec9c7f258df5df2f79_r.jpg");
        multiPartEmailSender.send();
        System.out.println("send success！");
    }
}
