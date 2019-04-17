package com.eastreach.pest.util;

import java.io.*;

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

    /**
     * 读取本地文本文件
     */
    public static String ReadFile(String Path) {
        BufferedReader reader = null;
        String result = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                result += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
