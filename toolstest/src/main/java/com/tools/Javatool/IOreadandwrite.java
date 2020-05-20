package com.tools.Javatool;

import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by jiangxiaolin on 2019/8/22.
 */

public class IOreadandwrite {



        /**
         *
         * @param fromFile 被复制的文件
         * @param toFile 复制的目录文件
         * @param rewrite 是否重新创建文件
         *
         * <p>文件的复制操作方法
         */
        public static void copyfile(File fromFile, File toFile,Boolean rewrite ){

            if(!fromFile.exists()){
                return;
            }
            if(!fromFile.isFile()){
                return;
            }
            if(!fromFile.canRead()){
                return;
            }
            if(!toFile.getParentFile().exists()){
                toFile.getParentFile().mkdirs();
            }
            if(toFile.exists() && rewrite){
                toFile.delete();
            }
            try {
                FileInputStream fosfrom = new FileInputStream(fromFile);
                FileOutputStream fosto = new FileOutputStream(toFile);
                byte[] bt = new byte[1024];
                int c;
                while((c=fosfrom.read(bt)) > 0){
                    fosto.write(bt,0,c);
                }
                //关闭输入、输出流
                fosfrom.close();
                fosto.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    /**在安卓的跟目录新建一个result的txt文档，然后再把txt文档改为csv格式的就变成了excel格式的
     * @param result   新建的result文档的名字
     * @throws Exception
     */
    public static void creatcsv(String result) throws Exception {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, result + ".txt");
//        if (file.exists()){
//            file.delete();
//            file.createNewFile();
//        }else {
//            file.createNewFile();
//        }
//        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GBK"));
        output.append("测试次数" +","+ "  电池电量"+"\r\n");
        output.close();

    }





    /**
     * 采用channel的方式复制文件内容，大文件的读写上有很大的优势，复制大文件的时候效率会比较高
     *
     * @param starpath 跟文件路径
     * @param edngpath 目标文件路径
     * @throws IOException
     */
    public void channeldoCopy(String starpath, String edngpath) throws IOException {
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
     * 实现用channel的方法实现读取一个文件的内容
     * @param path 需要读取文件的路径
     * @throws Exception
     */
    public static void channeldoread(String path) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile(path, "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(50);
        int bytesRead = inChannel.read(buf);

        while (bytesRead != -1){
            System.out.println("Read " + bytesRead);
            //flip()之后，读/写指针position指到缓冲区头部，并且设置了最多只能读出之前写入的数据长度
            buf.flip();
            //返回剩余的可用长度
            while(buf.hasRemaining()){
                System.out.print("可用长度"+(char) buf.get());
//                inChannel.write(src);

            }
            //读入channel中的数据
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();


    }

    /**
     * 用channel实现写入，如果没有文件就会创建，有的话会把之前的内容覆盖
     * @param filepath      文件路径
     * @param writecontent    写入的内容
     */
    public static void channeldowrite(String filepath , String writecontent) throws Exception {
        // TODO 自动生成的方法存根
        long starttime = System.currentTimeMillis();
        try {
            FileOutputStream f1 = new FileOutputStream(filepath);
            FileChannel fc1 = f1.getChannel();
            ByteBuffer buffer = ByteBuffer.wrap(writecontent.getBytes());
            fc1.write(buffer);

            fc1.close();
            f1.close();
        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            throw new Exception(e.toString());
        } catch (IOException e) {
            // TODO: handle exception
            throw new Exception(e.toString());
        }
        long endtime = System.currentTimeMillis();
        System.out.println("Channel策略写文件花费了：" + (endtime - starttime) + "ms");
    }

    /**从asset里面拷贝一个文件
     * @param contextname
     * @param dest
     * @throws IOException
     */
    private  void copyFileUsingFileStreams( String contextname, File dest)
            throws IOException {
//        InputStream input =getClass().getResourceAsStream("/assets/test.txt");
        InputStream input =getClass().getResourceAsStream("/assets/contextname");
        OutputStream output = null;
        try {
//            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    /**拷贝大文件
     * @param source
     * @param dest
     * @throws IOException
     */
    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /**拷贝文件
     * @param fromFile
     * @param toFile
     */
    public static void copyfile(InputStream fromFile, File toFile){

//        if(!fromFile.exists()){
//            return;
//        }
//        if(!fromFile.isFile()){
//            return;
//        }
//        if(!fromFile.canRead()){
//            return;
//        }
//        if(!toFile.getParentFile().exists()){
//            toFile.getParentFile().mkdirs();
//        }
//        if(toFile.exists() && rewrite){
//            toFile.delete();
//        }
        try {

            FileInputStream fosfrom = (FileInputStream)fromFile;
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte[] bt = new byte[1024];
            int c;
            while((c=fosfrom.read(bt)) > 0){
                fosto.write(bt,0,c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    /**删除整个文件夹里面所有的文件
     * @param SDPATH
     * @param fileName
     * @return
     */
    public boolean delete(String SDPATH,String fileName) {

        //SDPATH目录路径，fileName文件名

        File file = new File(SDPATH + "/" + fileName);
        if (file == null || !file.exists() || file.isDirectory()){
            return false;
        }
        file.delete();

        return true;
    }

    //删除整个文件夹方法
    public boolean deleteSDFile(File file) {

        //file目标文件夹绝对路径

        if (file.exists()) { //指定文件是否存在
            if (file.isFile()) { //该路径名表示的文件是否是一个标准文件
                file.delete(); //删除该文件
            } else if (file.isDirectory()) { //该路径名表示的文件是否是一个目录（文件夹）
                File[] files = file.listFiles(); //列出当前文件夹下的所有文件
                for (File f : files) {
                    deleteSDFile(f); //递归删除
                    //Log.d("fileName", f.getName()); //打印文件名
                }
            }
            file.delete(); //删除文件夹（song,art,lyric）
        }
        return true;
    }




}
