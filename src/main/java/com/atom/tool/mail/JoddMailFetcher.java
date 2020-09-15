package com.atom.tool.mail;

import com.atom.tool.core.ArrayUtil;
import jodd.mail.*;
import org.apache.commons.collections4.CollectionUtils;

import javax.activation.DataSource;
import java.io.File;
import java.util.List;

/**
 * use JoddMail receive mail
 *
 * @author Atom
 */
public class JoddMailFetcher implements MailFetcher {

    /**
     * fetch mail
     */
    @Override
    public void fetch() {
        Pop3Server popServer = MailServer.create()
                .host(MailConstant.Fetcher.HOST)
                .ssl(true)
                .auth(MailConstant.Fetcher.AUTH_USERNAME, MailConstant.Fetcher.AUTH_PASSWORD)
                .buildPop3MailServer();

        ReceiveMailSession session = popServer.createSession();
        session.open();
        System.out.println(session.getMessageCount());
        ReceivedEmail[] emails = session.receiveEmailAndMarkSeen();
        if (ArrayUtil.isNotEmpty(emails)) {
            for (ReceivedEmail email : emails) {
                System.out.println("\n\n===[" + email.messageNumber() + "]===");

                // common info
                System.out.println("FROM:" + email.from());
                System.out.println("TO:" + email.to()[0]);
                System.out.println("SUBJECT:" + email.subject());
                System.out.println("PRIORITY:" + email.priority());
                System.out.println("SENT DATE:" + email.sentDate());
                System.out.println("RECEIVED DATE: " + email.receivedDate());

                // process messages
                List<EmailMessage> messages = email.messages();
                for (EmailMessage msg : messages) {
                    System.out.println("------");
                    System.out.println(msg.getEncoding());
                    System.out.println(msg.getMimeType());
                    System.out.println(msg.getContent());
                }

                // process attachments
                List<EmailAttachment<? extends DataSource>> attachments = email.attachments();
                if (CollectionUtils.isNotEmpty(attachments)) {
                    System.out.println("+++++");
                    for (EmailAttachment attachment : attachments) {
                        System.out.println("name: " + attachment.getName());
                        System.out.println("cid: " + attachment.getContentId());
                        System.out.println("size: " + attachment.getSize());
                        attachment.writeToFile(new File("/Users/atom/testDir/mail_attachment/", attachment.getName()));
                    }
                }
            }
        }
        session.close();
    }


}
