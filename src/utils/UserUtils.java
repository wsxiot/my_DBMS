package utils;

import java.io.File;
import java.io.IOException;



import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import main.ConstValue;

public class UserUtils {
	public final static File file=new File(ConstValue.userfile);
	/*
	 * 功能：注册
	 * 参数：用户名，密码
	 */
	public static String register(String str1,String str2) {//注册
		String ret="ok";

		try {
			Workbook wb=Workbook.getWorkbook(file);
			WritableWorkbook wwb=Workbook.createWorkbook(file,wb);
			WritableSheet ws=wwb.getSheet(0);
			Sheet sh=wwb.getSheet(0);
			int row=ws.getRows();
			int flag=0;
			for(int i=0;i<row;i++){
				Cell cell = sh.getCell(0,i);
				if(cell.getContents().equals(str1)){
					ret="chongfu";
					flag=1;
					break;
				}
			}
			if(ret.equals("ok")){
			Label label=null;
			label=new Label(0,row,str1);
			ws.addCell(label);
			label=new Label(1,row,str2);
			ws.addCell(label);
			}
			wwb.write();
			wwb.close();
			wb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return ret;
		
	}
	/*
	 *功能：登录
	 *参数：用户名，密码 
	 */
	public static String login(String str1,String str2){//登录
		String ret="cuowu";
		if(!file.exists()){
			try {
				file.createNewFile();
				WritableWorkbook workbook = Workbook.createWorkbook(file);
				Sheet sheet = workbook.createSheet("user", 0);
				workbook.write();
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 0; i < sheet.getRows(); i++) {
					Cell cell0 = sheet.getCell(0,i);
					Cell cell1 = sheet.getCell(1,i);
					if(cell0.getContents().equals(str1)&&cell1.getContents().equals(str2)){
						ret="ok";
						break;
					}
			}
			workbook.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static void main(String[] args) {
		
	}
}
