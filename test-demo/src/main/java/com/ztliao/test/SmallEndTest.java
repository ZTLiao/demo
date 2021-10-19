package com.ztliao.test;

import java.io.*;

public class SmallEndTest {

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(new File("/Users/liaozetao/Downloads/p1"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fos = new FileOutputStream("/Users/liaozetao/Downloads/232-p1.bin");
        int ch;
        while ((ch = fis.read()) != -1) {
            baos.write(ch);
        }
        byte[] byteArray = baos.toByteArray();
        for (int i = 0; i < byteArray.length; i += 2) {
            byte temp = byteArray[i];
            byteArray[i] = byteArray[i + 1];
            byteArray[i + 1] = temp;
        }
        fos.write(byteArray);
        fos.flush();
        baos.close();
        fos.close();
        fis.close();
    }

}