package utils;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import main.ConstValue;

public class View {
//获取视图所有名称
	public static String getview(){
		String con="";
		
		try {
			File view=new File(ConstValue.viewfile);
			Workbook workbook = Workbook.getWorkbook(view);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 0; i < sheet.getRows(); i++) {
					Cell cell = sheet.getCell(0,i);
					con=con+cell.getContents()+"\n";
			}
			workbook.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}
	public static void main(String[] args) {
		
	}

}
