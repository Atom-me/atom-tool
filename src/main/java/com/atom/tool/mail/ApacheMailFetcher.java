package com.atom.tool.mail;

import com.atom.tool.core.FileUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.mail.util.MimeMessageParser;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;

/**
 * fetch mail use apache common-mail
 *
 * @author Atom
 */
public class ApacheMailFetcher implements MailFetcher {

    /**
     * fetch mail
     */
    @Override
    public void fetch() throws Exception {
        // 创建一个有具体连接信息的Properties对象
        Properties props = new Properties();
        props.put("mail.pop3.ssl.enable", MailConstant.Fetcher.IS_SSL);
        props.put("mail.pop3.host", MailConstant.Fetcher.HOST);
        props.put("mail.pop3.port", MailConstant.Fetcher.PORT);
        props.put("mail.debug", MailConstant.IS_DEBUG);
        //mail.pop3.disabletop	如果设置为true，则不会使用POP3 TOP命令来获取邮件头。 默认为false。
        props.put("mail.pop3.disabletop", MailConstant.Fetcher.DISABLE_TOP);
        // 1、create session
        Session session = Session.getDefaultInstance(props);
        Store store = null;
        Folder folder = null;
        try {
            // 2、get store through session
            store = session.getStore(MailConstant.Fetcher.PROTOCOL);
            // 3、connect mail server
            store.connect(MailConstant.Fetcher.AUTH_USERNAME, MailConstant.Fetcher.AUTH_PASSWORD);
            // 4、get mail inbox
            folder = store.getFolder(MailConstant.Fetcher.FOLDER);
            if (MailConstant.Fetcher.FOLDER_READONLY) {
                folder.open(Folder.READ_ONLY);
            } else {
                folder.open(Folder.READ_WRITE);
            }

            // common info:
            System.err.println("Total messages: " + folder.getMessageCount());
            System.err.println("New messages: " + folder.getNewMessageCount());
            System.err.println("Unread messages: " + folder.getUnreadMessageCount());
            System.err.println("Deleted messages: " + folder.getDeletedMessageCount());
            // process messages
            Message[] messages = folder.getMessages();

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                MimeMessage mimeMessage = (MimeMessage) message;
                MimeMessageParser parser = new MimeMessageParser(mimeMessage).parse();
                System.out.println(i + "   message  getContentType: " + message.getContentType());
                System.out.println(i + "   message  getSubject: " + message.getSubject());
                System.out.println(i + "   message  getSentDate: " + message.getSentDate());
                System.out.println(i + "   parser getSubject: " + parser.getSubject());
                System.out.println(i + "   parser getFrom: " + parser.getFrom());
                System.out.println(i + "   mimeMessage getBody: " + getBody(mimeMessage));

                // process attachments
                List<DataSource> attachmentList = parser.getAttachmentList();
                if (CollectionUtils.isNotEmpty(attachmentList)) {
                    System.out.println("+++++");
                    for (DataSource dataSource : attachmentList) {
                        System.out.println(dataSource.getName());
                        System.out.println(dataSource.getContentType());
                        String destinationFilePath = "/Users/atom/testDir/mail_attachment/";
                        FileUtil.writeToFile(dataSource.getInputStream(), destinationFilePath + dataSource.getName(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                System.err.println("======================================");
            }
        } catch (MessagingException e) {
            throw e;
        } finally {
            try {
                if (folder != null) {
                    folder.close(false);
                }
                if (store != null) {
                    store.close();
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        System.out.println("fetch mail success!");
    }


    /**
     * 获取邮件的正文。一个MimeMessage对象也是一个Part对象，
     * 它可能只包含一个文本，也可能是一个Multipart对象，即由几个Part构成，因此，需要递归地解析出完整的正文：
     *
     * @param part
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    static String getBody(Part part) throws MessagingException, IOException {
        if (part.isMimeType("text/*")) {
            // Part是文本:
            return part.getContent().toString();
        }
        if (part.isMimeType("multipart/*")) {
            // Part是一个Multipart对象:
            Multipart multipart = (Multipart) part.getContent();
            // 循环解析每个子Part:
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String body = getBody(bodyPart);
                if (!body.isEmpty()) {
                    return body;
                }
            }
        }
        return "";
    }
}
