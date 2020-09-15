package com.atom.tool.core;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Atom
 */
@Slf4j
public class PropertiesUtil {

    /**
     * 加载 properties 文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadPropFile(String fileName) {
        Properties properties = null;
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            if (Objects.isNull(in)) {
                throw new FileNotFoundException(fileName + " file not found");
            }
            properties = new Properties();
            properties.load(in);
        } catch (Exception e) {
            log.error("load [{}] file failure [{}]", fileName, Throwables.getStackTraceAsString(e));
        }
        return properties;
    }
}
