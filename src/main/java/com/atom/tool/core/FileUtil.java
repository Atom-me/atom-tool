package com.atom.tool.core;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author Atom
 */
@Slf4j
public class FileUtil {

    public static void main(String[] args) {
        File root = new File("/Users/atom/logs/metrics-copy");
//        deleteAllFilesAndDirsIncludeRoot(root);
//        deleteAllFileAndDirsIncludeRootRecursively(root);
        deleteAllFilesExcludeDirsRecursively(root);
    }

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


    /**
     * delete all files and dirs include root dir
     *
     * @param root
     */
    public static void deleteAllFilesAndDirsIncludeRoot(File root) {
        if (Objects.nonNull(root) && root.exists()) {
            try {
                Files.walk(root.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
//                        .peek(System.out::println)
                        .forEach(File::delete);
            } catch (IOException e) {
                log.error("FileUtil delete files failure [{}]", Throwables.getStackTraceAsString(e));
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * delete recursively
     *
     * @param root
     * @return
     */
    public static boolean deleteAllFileAndDirsIncludeRootRecursively(File root) {
        if (root != null && root.exists()) {
            if (root.isDirectory()) {
                File[] children = root.listFiles();
                if (children != null) {
                    for (File child : children) {
                        deleteAllFileAndDirsIncludeRootRecursively(child);
                    }
                }
            }
            return root.delete();
        }
        return false;
    }

    /**
     * delete All Files Exclude Dirs Recursively
     *
     * @param root
     */
    public static void deleteAllFilesExcludeDirsRecursively(File root) {
        if (Objects.nonNull(root) && root.exists()) {
            File[] children = root.listFiles();
            if (ArrayUtils.isNotEmpty(children)) {
                for (File child : children) {
                    if (child.isDirectory()) {
                        deleteAllFilesExcludeDirsRecursively(child);
                    } else {
                        child.delete();
                    }
                }
            }
        }
    }


    public static void deleteFile(String fileName) {
        // file
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }


    public static void writeFileContent(File file, byte[] data) {

        // file
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        // append file content
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

    }

    public static byte[] readFileContent(File file) {
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return fileContent;
    }

}
