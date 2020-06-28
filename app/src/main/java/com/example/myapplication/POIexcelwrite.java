package com.example.myapplication;

import android.os.Environment;
import android.util.Log;

import com.example.myapplication.inputinfoactivity;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class POIexcelwrite {
	/**
	 * 在安卓端实现创建一个xlsx文件
	 * 第三方jar 包在百度网盘里面 ： android 读取excel
	 *
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void creatdowneexcel() throws FileNotFoundException, IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("信息读取记录");
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		XSSFCell cell1 = row.createCell(1);
		XSSFCell cell2 = row.createCell(2);
//		XSSFCell cell3=row.createCell(3);
//		XSSFCell cell4=row.createCell(4);
//		XSSFCell cell5=row.createCell(5);
//		XSSFCell cell6=row.createCell(6);
		cell.setCellValue("测试次数");
		cell1.setCellValue("测试用例");
		cell2.setCellValue("测试结果");
		FileOutputStream fileout = new FileOutputStream("/mnt/sdcard/result.xlsx");


		workbook.write(fileout);
//		System.out.println("创建");
		Log.v("TAG", "创建成功");

	}


//	public synchronized   void writeExcel3(String testcount, String testname, String result) {
//		try {
//			FileInputStream fileInput = new FileInputStream("/mnt/sdcard/result.xlsx");
//			XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
//			XSSFSheet sh = workbook.getSheetAt(0);
//			//获取最后一行的值
//			int lastRowNum = sh.getLastRowNum();
//			 Log.v("TAG","最后一行"+lastRowNum);
//			XSSFCell createCell1 = sh.createRow(lastRowNum + 1).createCell(0);
//			XSSFCell createCel2 = sh.getRow(lastRowNum + 1).createCell(1);
//			XSSFCell createCel3 = sh.getRow(lastRowNum + 1).createCell(2);
//			createCell1.setCellValue(testcount);
//			createCel2.setCellValue(testname);
//			//有失败项红色显示
//			if(result.contains("失败")){
//				XSSFFont font = workbook.createFont();
//				font.setColor(org.apache.poi.ss.usermodel.Font.COLOR_RED);
//				CellStyle cellStyle = workbook.createCellStyle();
//				cellStyle.setFont(font);
//				createCel3.setCellValue(result);
//				createCel3.setCellStyle(cellStyle);
//			}else {
//				createCel3.setCellValue(result);
//			}
//			FileOutputStream os = new FileOutputStream("/mnt/sdcard/result.xlsx");
//			os.flush();
//			workbook.write(os);
//			fileInput.close();
//			os.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void writeExcel3(String ret, String testname, String result) {
		{
			String path = Environment.getExternalStorageDirectory().getAbsolutePath();
			File file = new File(path, MainActivity.mode + "循环测试记录" + ".txt");
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

