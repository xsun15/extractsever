package com.seaboat.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class ByteCodeEncryptor {
    //  加载库（com_seaboat_bytecode_ByteCodeEncryptor.cpp），向 JVM 注册本地方法
    static{
        System.loadLibrary("ByteCodeEncryptor");
    }

    public native static byte[] encrypt(byte[] text);   //表示这个方法的具体实现在本地方法中

    public static void main(String[] args){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            String fileName = "plotextract.jar";

            File srcFile = new File(fileName);
            File dstFile = new File(fileName.substring(0, fileName.indexOf("."))+"_encrypted.jar");
            FileOutputStream dstFos = new FileOutputStream(dstFile);
            JarOutputStream dstJar = new JarOutputStream(dstFos);
            JarFile srcJar = new JarFile(srcFile);
            for (Enumeration<JarEntry> enumeration = srcJar.entries(); enumeration.hasMoreElements();) {
                JarEntry entry = enumeration.nextElement();
                InputStream is = srcJar.getInputStream(entry);
                int len;
                while ((len = is.read(buf, 0, buf.length)) != -1) {
                    baos.write(buf, 0, len);
                }
                byte[] bytes = baos.toByteArray();
                String name = entry.getName();
                if(name.startsWith("com/cjbdi/core")){   //对 cn.zzp 包下面的 所有 class 文件加密
                    try {
                        bytes = ByteCodeEncryptor.encrypt(bytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                JarEntry ne = new JarEntry(name);
                dstJar.putNextEntry(ne);
                dstJar.write(bytes);
                baos.reset();
            }
            srcJar.close();
            dstJar.close();
            dstFos.close();
            System.out.println("encrypt finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}