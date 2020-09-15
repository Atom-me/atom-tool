package com.atom.tool.mail;


import com.atom.tool.core.CastUtil;
import com.atom.tool.core.PropertiesUtil;

import java.util.Properties;

/**
 * @author Atom
 */
public interface MailConstant {
    Properties MAIL_CONFIG = PropertiesUtil.loadPropFile("mail-config.properties");
    boolean IS_DEBUG = CastUtil.castBoolean(MAIL_CONFIG.getProperty("mail.is_debug"));

    interface Sender {
        String PROTOCOL = MAIL_CONFIG.getProperty("sender.protocol");
        boolean IS_SSL = CastUtil.castBoolean(MAIL_CONFIG.getProperty("sender.protocol.ssl"));
        String HOST = MAIL_CONFIG.getProperty("sender.protocol.host");
        int PORT = CastUtil.castInt(MAIL_CONFIG.getProperty("sender.protocol.port"));
        String FROM = MAIL_CONFIG.getProperty("sender.from");
        boolean IS_AUTH = CastUtil.castBoolean(MAIL_CONFIG.getProperty("sender.auth"));
        String AUTH_USERNAME = MAIL_CONFIG.getProperty("sender.auth.username");
        String AUTH_PASSWORD = MAIL_CONFIG.getProperty("sender.auth.password");
    }

    interface Fetcher {
        String PROTOCOL = MAIL_CONFIG.getProperty("fetcher.protocol");
        boolean IS_SSL = CastUtil.castBoolean(MAIL_CONFIG.getProperty("fetcher.protocol.ssl"));
        String HOST = MAIL_CONFIG.getProperty("fetcher.protocol.host");
        int PORT = CastUtil.castInt(MAIL_CONFIG.getProperty("fetcher.protocol.port"));
        boolean DISABLE_TOP = CastUtil.castBoolean(MAIL_CONFIG.getProperty("fetcher.protocol.disabletop"));
        String FOLDER = MAIL_CONFIG.getProperty("fetcher.folder");
        boolean FOLDER_READONLY = CastUtil.castBoolean(MAIL_CONFIG.getProperty("fetcher.folder.readonly"));
        String AUTH_USERNAME = MAIL_CONFIG.getProperty("fetcher.auth.username");
        String AUTH_PASSWORD = MAIL_CONFIG.getProperty("fetcher.auth.password");
    }

}
