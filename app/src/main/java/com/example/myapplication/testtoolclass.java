package com.example.myapplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Locale;




import android.annotation.SuppressLint;
import android.app.Application;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;

public class testtoolclass {
	
	   /**在安卓的跟目录新建一个result的txt文档，然后再把txt文档改为csv格式的就变成了excel格式的
     * @param result   新建的result文档的名字
     * @throws Exception
     */
    public static void creatcsv(int ret,String result) throws Exception {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, inputinfoactivity.mode+"循环测试记录"+ ".txt");
//        if (file.exists()){
//            file.delete();
////            file.createNewFile();
//        }else {
//            file.createNewFile();
//        }
//        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GBK"));
        if (result.contains("test")) {
        	
        	output.append(result+","+","+ret +"\r\n");
        	  output.close();
			
		}else {
			  output.append(","+result+","+ret +"\r\n");
		        output.close();
			
		}
      

    }
    
    @SuppressLint("NewApi")
	public static void creattitle() throws Exception {
    	String path = Environment.getExternalStorageDirectory().getAbsolutePath();

    	File file = new File(path, MainActivity.mode+"重启秘钥测试"+ ".txt");
        if (file.exists()){
//            file.delete();
//            file.createNewFile();
        }else {
            file.createNewFile();
        }
    	BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GBK"));
    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINA);// 日期格式名定义
           String fname =sdf.format(new Date());
    	output.append(fname+","+""+","+"" +"\r\n");
    	output.append("测试次数"+","+"测试项目"+","+"测试结果" +"\r\n");
    	output.close();
    	
    }

	public void writeExcel3(String ret, String testname, String result) {
		{
			String path = Environment.getExternalStorageDirectory().getAbsolutePath();
			File file = new File(path,   MainActivity.mode+"重启秘钥测试" + ".txt");
//        if (file.exists()){
//            file.delete();
////            file.createNewFile();
//        }else {
//            file.createNewFile();
//        }
//        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
			BufferedWriter output = null;
			try {
				output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GBK"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				output.append(ret+"," + testname + "," + result + "\r\n");
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}


		}
	}
	
	
	
	

}
