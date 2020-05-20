package com.example.myapplication.Javatool;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;

/**
 * Created by jiangxiaolin on 2019/8/22.
 */

public class IOreadandwrite {

    /**
     * 用于复制文件内容，大文件的读写上有很大的优势
     *
     * @param starpath 跟文件路径
     * @param edngpath 目标文件路径
     * @throws IOException
     */
    public void doCopy(String starpath, String edngpath) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile(starpath, "rw");
        FileChannel inChannel = aFile.getChannel();
        RandomAccessFile bFile = new RandomAccessFile(edngpath, "rw");
        FileChannel outChannel = bFile.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        System.out.println("Copy over");
        inChannel.close();
        outChannel.close();
        aFile.close();
        bFile.close();

    }

    /**
     * 读取网络资源，写出代码至本地文件
     *
     * @param URLpath  URL路径
     * @param edngpath 本地路径
     * @throws IOException
     */
    public void doURLtopath(String URLpath, String edngpath) throws IOException {
        URL url = new URL(URLpath);
        InputStream is = url.openStream();//输入流，读取
//        OutputStream os = new FileOutputStream("d:\\sdut.html");//输出流，写入这个文件中
        OutputStream os = new FileOutputStream(edngpath);//输出流，写入这个文件中
        byte[] bytes = new byte[1024];//一个读取10个字符，但是写入不是
        int len;
        while ((len = is.read(bytes)) != -1) {
            os.write(bytes, 0, len);
            is.close();
            os.close();
        }
    }

    /**
     * 写字符到到对应文件
     *
     * @param path 写入的文件路径
     * @param str  写入文件的内容
     */
    public void dobufferedWrite(String path, String str) {
        File f = new File(path);
        BufferedOutputStream bos = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;
        try {
            OutputStream os = new FileOutputStream(f);
            bos = new BufferedOutputStream(os);
            writer = new OutputStreamWriter(bos);
            bw = new BufferedWriter(writer);
            //需要换行就用这个
            bw.write(str + "\r\n");
//            不需要就用这个
//            bw.write(str);
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取path路径下文本的内容
     * @param path    文件路径
     */
    public void dobufferedread(String path) {
        BufferedReader br = null;
        BufferedReader br2 = null;
        try {
            br = new BufferedReader(new FileReader(path));
            // 第一种读取文件方式
            String contentLine;
            while ((contentLine = br.readLine()) != null) {
                contentLine = br.readLine();
                //读取每一行，并输出
                System.out.println(contentLine);
                //将每一行追加到arr1
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doTCPclient(String path) {
        BufferedReader br = null;
        BufferedReader br2 = null;
        try {
            br = new BufferedReader(new FileReader(path));
            // 第一种读取文件方式
            String contentLine;
            while ((contentLine = br.readLine()) != null) {
                contentLine = br.readLine();
                //读取每一行，并输出
                System.out.println(contentLine);
                //将每一行追加到arr1
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
