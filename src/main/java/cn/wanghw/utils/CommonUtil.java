package cn.wanghw.utils;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CommonUtil {
    public static MontoyaApi montoyaApi;

    public static String getExtensionFileParentDirectory() {
        String path = "";
        int lastIndex = montoyaApi.extension().filename().lastIndexOf(File.separator);
        path = montoyaApi.extension().filename().substring(0, lastIndex) + File.separator;
        return path;
    }

    public static String[] readLines(String filePath) throws IOException {
        Set<String> set = new HashSet<>(); //用HashSet来存储不重复的行
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) { //读取每一行
            line = line.trim();
            if (!line.equals(""))
                set.add(line); //添加到set中
        }
        return set.toArray(new String[set.size()]); //将set转换为String数组
    }

    public static Logging getLogger() {
        return montoyaApi.logging();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
