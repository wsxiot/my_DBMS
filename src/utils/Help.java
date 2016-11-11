package utils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import main.ConstValue;

public class Help {

	public static boolean ishelptable(String sql){//判断是否符合help table语法
		boolean is=true;
		Pattern p = Pattern.compile("help table [\\s\\S]+\\;");
		Matcher m = p.matcher(sql);
		if (!m.matches()) {
			is = false;
		} 
		return is;
		
	}
	//获取help table语句的表名
	public static String gethelptablename(String sql){
		int namef=sql.indexOf(" ");
		namef=sql.indexOf(" ",namef+1);
		String name=sql.substring(namef+1,sql.length()-1);
		return  name;
	}
	//获取help table的信息集
	public static String gethelptableinfo(String tablename){
		File frm=new File(ConstValue.contendir+File.separator+tablename+"frm.xls");
		String con=null;
		try {
			Workbook workbook = Workbook.getWorkbook(frm);
			Sheet sheet = workbook.getSheet(0);
			con=new String();
			for (int i = 0; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j,i);
					con+=cell.getContents();
					int l=cell.getContents().length();
					for(int s=0;s<15-l;s++){
						con+=" ";
					}
				}
				con+="\n";
			}
			workbook.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}
	//获取help database的信息集
	public static String gethelpdatabaseinfo(){
		String con=null;
		con="table:\n";
		try {
			File tablecon=new File(ConstValue.tablecon);
			Workbook workbook = Workbook.getWorkbook(tablecon);
			Sheet sheet = workbook.getSheet(0);
			for(int i=0;i<sheet.getRows();i++){
				Cell cell=sheet.getCell(0,i);
				con+=cell.getContents();
				con+="\n";
			}
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
		String sql="help table student;";
		System.out.println(ishelptable(sql));
		String name=gethelptablename(sql);
		String con=gethelptableinfo(name);
		System.out.println(con);
	}

}
