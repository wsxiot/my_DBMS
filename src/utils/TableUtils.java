package utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import main.ConstValue;
import main.Init;

public class TableUtils {

	public static boolean isrepeat(String tablename) {// 检验表名是否重复
		boolean is = false;
		try {
			File tablecon = new File(ConstValue.tablecon);
			Workbook workbook = Workbook.getWorkbook(tablecon);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 0; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(0, i);
				if (cell.getContents().equals(tablename)) {
					is = true;
					break;
				}
			}
			workbook.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}
	//检查用户名是否重复
	public static boolean isrepeatusername(String username) {
		boolean is = false;
		try {
			File userfile = new File(ConstValue.userfile);
			Workbook workbook = Workbook.getWorkbook(userfile);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 0; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(0, i);
				if (cell.getContents().equals(username)) {
					is = true;
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
		return is;
	}
//检查输入的权限名是否正确
	public static boolean isperm(String perm) {
		boolean is = false;
		String[] allperm = { "select", "insert", "delete", "update" };
		for (String str : allperm) {
			if (perm.equals(str)) {
				is = true;
				break;
			}
		}
		return is;
	}
	//检查视图名是否重复
	public static boolean isrepeatview(String viewname){
		boolean is=false;
		try {
			File view=new File(ConstValue.contendir+File.separator+"view.xls");
			Workbook workbook = Workbook.getWorkbook(view);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 0; i < sheet.getRows(); i++) {
					Cell cell = sheet.getCell(0,i);
					if(cell.getContents().equals(viewname)){
						is=true;
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
		return is;
	}
	public static void main(String[] args) {
		// Init.init();
		// String sql="create table hello(a int,b char(8),c string);";
		// System.out.println(ParsersqlUtils.iscreatesql(sql));
		// String tablename=ParsersqlUtils.gettablename(sql);
		// System.out.println(tablename);
		// String[] info=ParsersqlUtils.getnature(sql);
		// System.out.println(Arrays.toString(info));
		// System.out.println(isrepeat(tablename));
		// System.out.println(writeintablecon(tablename));
		// inittable(tablename,info);
		// System.out.println(isrepeatusername("roo"));
		System.out.println(isperm("delete"));

	}

}
