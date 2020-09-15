package com.atom.tool.core;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Atom
 */
@Slf4j
public class FileUtil {


    /**
     * copy inputStream to a file.
     *
     * @param inputStream
     * @param destinationFilePath The destination file to be written.
     * @param copyOption
     */
    public static void writeToFile(InputStream inputStream, String destinationFilePath, StandardCopyOption copyOption) {
        try {
            Path attachmentSavePath = Paths.get(destinationFilePath);
            Files.copy(inputStream, attachmentSavePath, copyOption);
        } catch (Exception e) {
            log.error("FileUtil writeToFile failure [{}]", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * copy inputStream to a file.
     *
     * @param inputStream
     * @param destinationFilePath The destination file to be written.
     */
    public static void writeToFile(InputStream inputStream, String destinationFilePath) {
        writeToFile(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
    }

}
