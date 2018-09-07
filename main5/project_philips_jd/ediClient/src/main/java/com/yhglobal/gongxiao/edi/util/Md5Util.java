package com.yhglobal.gongxiao.edi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 葛灿
 */
public class Md5Util {

    /**
     * 计算文件的md5值,更改文件名无效
     *
     * @param file 需计算的文件路径
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getFileMD5(File file) throws NoSuchAlgorithmException {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        System.out.println(bigInt.toString());
        return bigInt.toString(16);
    }
}
