package com.atom.tool;

import com.atom.tool.mail.ApacheMailFetcher;
import com.atom.tool.mail.JoddMailFetcher;
import com.atom.tool.mail.MailFetcher;
import org.junit.Test;

public class MailFetcherTest {

    @Test
    public void testApacheMailFetcher() throws Exception {
        MailFetcher mailFetcher = new ApacheMailFetcher();
        mailFetcher.fetch();
        System.out.println("收取完毕！");
    }

    @Test
    public void testJoddMailFetcher() throws Exception {
        MailFetcher mailFetcher = new JoddMailFetcher();
        mailFetcher.fetch();
        System.out.println("收取完毕！");
    }
}
