package com.atom.tool;

import com.atom.tool.core.FreemarkerUtil;
import com.atom.tool.mail.HtmlEmailSender;
import com.atom.tool.mail.MailSender;
import com.atom.tool.mail.MultiPartEmailSender;
import com.atom.tool.mail.SimpleEmailSender;
import com.google.common.collect.Maps;
import freemarker.template.TemplateException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

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
        MailSender simpleEmailSender = new SimpleEmailSender(subject, content, to);
        /**
         * it won't be added,because the simple email sender not support attachment
         * {@link com.atom.tool.mail.AbstractMailSender#addAttachment(String)}
         */
        simpleEmailSender.addAttachment("https://pic4.zhimg.com/v2-588b5a5dd202c4ec9c7f258df5df2f79_r.jpg");
        simpleEmailSender.send();
        System.out.println("send success！");
    }


    @Test
    public void testHtmlEmailSender() throws IOException, TemplateException {
        String subject = "this my subject";
        String[] to = {"xxx@126.com"};

        //build html messages
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("messageCode", 500);
        params.put("messageStatus", "failed");
        params.put("cause", "请求参数异常");
        String contentWithHtml = FreemarkerUtil.processString("msg1.ftl", params);
        System.err.println(contentWithHtml);

        MailSender htmlEmailSender = new HtmlEmailSender(subject, contentWithHtml, to);
        /**
         * <p>This {@link HtmlEmail} class also inherits from {@link MultiPartEmail}, so it is easy to
         * add attachments to the email.
         */
        htmlEmailSender.addAttachment("/Users/atom/testDir/test.sh");

        htmlEmailSender.send();
        System.out.println("send success！");
    }
}
