package com.eastreach.pest.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 *
 **/
public class FileUtil {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + FILE_SEPARATOR + fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
