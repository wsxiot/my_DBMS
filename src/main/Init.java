package main;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Init {
	public static void init() {
		File f1=new File(ConstValue.contendir);
		File f2=new File(ConstValue.tablecon);
		File f3=new File(ConstValue.userfile);
		File f4=new File(ConstValue.viewfile);
		if(!f1.exists()){
			f1.mkdirs();
		}
		if(!f2.exists()){//初始化tablecon并且添加一个sheet
			try {
				f2.createNewFile();
				WritableWorkbook workbook = Workbook.createWorkbook(f2);
				Sheet sheet = workbook.createSheet("tablecon", 0);
				workbook.write();
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		if(!f3.exists()){//初始化userfile并且添加一个sheet
			try {
				f3.createNewFile();
				WritableWorkbook workbook = Workbook.createWorkbook(f3);
				Sheet sheet = workbook.createSheet("user", 0);
				workbook.write();
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		if(!f4.exists()){//初始化viewfile并且添加一个sheet
			try {
				f4.createNewFile();
				WritableWorkbook workbook = Workbook.createWorkbook(f4);
				Sheet sheet = workbook.createSheet("view", 0);
				workbook.write();
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}
}
