package com.atom.tool.mail;

import com.atom.tool.mail.util.MailUtil;
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
     * 抄送
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
                    log.error("错误：添加 CC 出错！", e);
                }
            }
        }
    }

    /**
     * 暗送
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
                    log.error("错误：添加 BCC 出错！", e);
                }
            }
        }
    }

    /**
     * 添加附件
     *
     * @param path 附件地址
     */
    @Override
    public void addAttachment(String path) {
        try {
            if (email instanceof MultiPartEmail) {
                // create attachment
                if (StringUtils.isNotBlank(path)) {
                    EmailAttachment emailAttachment = new EmailAttachment();
                    if (path.startsWith("http")) {
                        emailAttachment.setURL(new URL(path));
                    } else {
                        emailAttachment.setPath(path);
                    }
                    emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
                    emailAttachment.setDescription("this is just an attachment desc");
                    emailAttachment.setName(path.substring(path.lastIndexOf("/") + 1));
                    //create the email message
                    MultiPartEmail multiPartEmail = (MultiPartEmail) email;
                    multiPartEmail.attach(emailAttachment);
                }
            }
        } catch (MalformedURLException e) {
            log.error("错误：创建 附件URL 出错！", e);
        } catch (EmailException e) {
            log.error("错误：添加附件出错！", e);
        }
    }

    /**
     * send the email
     */
    @Override
    public void send() {
        try {
            // 判断协议名是否为 smtp（暂时仅支持 smtp，未来可考虑扩展）
            if (!"smtp".equalsIgnoreCase(MailConstant.Sender.PROTOCOL)) {
                log.error("错误：不支持该协议！目前仅支持 smtp 协议");
                return;
            }
            // 判断是否支持 SSL 连接
            if (MailConstant.Sender.IS_SSL) {
                email.setSSLOnConnect(true);
            }
            // 设置 主机名 与 端口号
            email.setHostName(MailConstant.Sender.HOST);
            email.setSmtpPort(MailConstant.Sender.PORT);
            // 判断是否进行身份认证
            if (MailConstant.Sender.IS_AUTH) {
                email.setAuthentication(MailConstant.Sender.AUTH_USERNAME, MailConstant.Sender.AUTH_PASSWORD);
            }
            // 判断是否开启 Debug 模式
            if (MailConstant.IS_DEBUG) {
                email.setDebug(true);
            }
            // 设置 From 地址
            if (StringUtils.isNotEmpty(MailConstant.Sender.FROM.trim())) {
                email.setFrom(MailUtil.encodeAddress(MailConstant.Sender.FROM));
            }
            // 设置 To 地址
            for (String address : to) {
                email.addTo(MailUtil.encodeAddress(address));
            }
            // 设置主题
            email.setSubject(subject);
            // 设置内容（在子类中实现）
            setContent(email, content);
            // 发送
            String sendResult = email.send();
            System.err.println(sendResult);
        } catch (EmailException e) {
            log.error("send email failure", e);
        }
    }

    /**
     * 创建具体类型的邮件对象 {@link Email}
     *
     * @return
     * @see org.apache.commons.mail.HtmlEmail
     * @see org.apache.commons.mail.ImageHtmlEmail
     * @see MultiPartEmail
     * @see org.apache.commons.mail.SimpleEmail
     */
    protected abstract Email createEmail();

    /**
     * 设置邮件内容
     *
     * @param email
     * @param content
     * @throws EmailException
     */
    protected abstract void setContent(Email email, String content) throws EmailException;
}
