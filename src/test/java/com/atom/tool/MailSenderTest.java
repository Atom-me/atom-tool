package com.atom.tool;

import com.atom.tool.mail.MailSender;
import com.atom.tool.mail.TextMailSender;

public class MailSenderTest {
    private static final String subject = "subject主题";
    private static final String content = "mail_apache text 邮件内容323";
    private static final String[] to = {"sarming@126.com"};

    public static void main(String[] args) {
        MailSender mailSender = new TextMailSender(subject, content, to);
        mailSender.addAttachment("https://pic4.zhimg.com/v2-588b5a5dd202c4ec9c7f258df5df2f79_r.jpg");
        mailSender.send();
        System.out.println("发送完毕！");
    }
}
