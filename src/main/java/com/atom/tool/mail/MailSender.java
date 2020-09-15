package com.atom.tool.mail;

/**
 * 邮件发送
 *
 * @author Atom
 */
public interface MailSender {

    /**
     * 抄送
     *
     * @param cc
     */
    void addCc(String[] cc);

    /**
     * 暗送
     *
     * @param bcc
     */
    void addBcc(String[] bcc);

    /**
     * 添加附件
     *
     * @param path 附件地址
     */
    void addAttachment(String path);

    /**
     * 发送邮件
     */
    void send();
}
