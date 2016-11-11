package utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class Select {

	public static int isselect(String sql) {
		int is = 0;
		Pattern p = Pattern.compile("select \\* from \\w+;");
		Matcher m = p.matcher(sql);
		if (m.matches()) {
			is = 1;
		}
		Pattern p1 = Pattern.compile("select ((((\\w+),)+(\\w)+)|(\\w+)) from (\\w+)\\;");
		Matcher m1 = p1.matcher(sql);
		if (m1.matches()) {
			is = 2;
		}
		Pattern p2 = Pattern.compile("select ((((\\w+),)+(\\w)+)|(\\w+)) from (\\w+) where (\\w+)='\\w+';");
		Matcher m2 = p2.matcher(sql);
		if (m2.matches()) {
			is = 3;
		}
		Pattern p3 = Pattern.compile("select \\* from (\\w+) where (\\w+)='(\\w+)';");
		Matcher m3 = p3.matcher(sql);
		if (m3.matches()) {
			is = 4;
		}
		return is;
	}

	////////////////// 判断是否符合select * from
	////////////////// table语法////////////////////////////////////////
	public static boolean isselectsql(String sql) {
		boolean is = true;
		Pattern p = Pattern.compile("select \\* from \\w+;");
		Matcher m = p.matcher(sql);
		if (!m.matches()) {
			is = false;
		}
		return is;
	}

	// 获取表名
	public static String getselecttablename(String sql) {
		int namef = sql.lastIndexOf(" ");
		int namel = sql.length();
		String name = sql.substring(namef + 1, namel - 1);
		return name;
	}

	// 获取语句执行后的输出内容
	public static String getselectcontent(String tablename) {
		File idb = new File(ConstValue.contendir + File.separator + tablename + "idb.xls");
		String con = "";
		try {
			Workbook workbook = Workbook.getWorkbook(idb);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 0; i < sheet.getRows(); i++) {
				int flag = 0;
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					if (!cell.getContents().equals("")) {
						flag = 1;
						break;
					}

				}
				if (flag == 1) {
					for (int k = 0; k < sheet.getColumns(); k++) {
						Cell cell = sheet.getCell(k, i);
						con = con + cell.getContents() + "\t";
					}
					con += "\n";
				}
			}
			workbook.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}

	//////////////////////////// 判断是否符合select Sno,Sname from
	//////////////////////////// student;////////////////////////
	public static boolean isselectattrsql(String sql) {
		boolean is = true;
		Pattern p = Pattern.compile("select ((((\\w+),)+(\\w)+)|(\\w+)) from (\\w+)\\;");
		Matcher m = p.matcher(sql);
		if (!m.matches()) {
			is = false;
		}
		return is;
	}

	// 获取表名
	public static String getselectattrtablename(String sql) {
		String tablename = null;
		Pattern p = Pattern.compile("select ([\\s\\S]+)from (\\w+);");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			tablename = m.group(2);
		}
		return tablename;
	}

	// 获取属性名数组
	public static String[] getselectattrattrs(String sql) {
		String[] con = null;
		String attrs = null;
		Pattern p = Pattern.compile("select ([\\s\\S]+)from (\\w+);");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			attrs = m.group(1);
		}
		attrs = attrs.substring(0, attrs.length() - 1);
		con = attrs.split(",");
		return con;
	}

	// 从文件获取内容///////////////////////////////
	public static String getselectattrcontent(String tablename, int[] attrs) {
		String con = "";
		try {
			File idb = new File(ConstValue.contendir + File.separator + tablename + "idb.xls");
			Workbook workbook = Workbook.getWorkbook(idb);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 0; i < sheet.getRows(); i++) {
				int flag = 0;
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					if (!cell.getContents().equals("")) {
						flag = 1;
						break;
					}
				}
				if (flag == 1) {
					for (int k : attrs) {
						Cell cell = sheet.getCell(k, i);
						con = con + cell.getContents() + "\t";
					}
					con += "\n";
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
		return con;
	}

	/////////////////////// 判断是否符合select Sno,Sname from student where
	/////////////////////// Sage='20';///////////////////////////
	public static boolean isselectattrwheresql(String sql) {
		boolean is = true;
		Pattern p = Pattern.compile("select ((((\\w+),)+(\\w)+)|(\\w+)) from (\\w+) where (\\w+)='\\w+';");
		Matcher m = p.matcher(sql);
		if (!m.matches()) {
			is = false;
		}
		return is;
	}
///获取表名
	public static String getselectattrwheretablename(String sql) {
		String tablename = null;
		Pattern p = Pattern.compile("select ([\\s\\S]+)from (\\w+) where (\\w+)='(\\w+)';");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			tablename = m.group(2);
		}
		return tablename;
	}
///获取属性名//////////////
	public static String[] getselectattrwhereattrs(String sql) {
		String[] attrs = null;
		String att = null;
		Pattern p = Pattern.compile("select ([\\s\\S]+)from (\\w+) where (\\w+)='(\\w+)';");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			att = m.group(1);
		}
		att = att.substring(0, att.length() - 1);
		attrs = att.split(",");
		return attrs;
	}
////获取后面的依赖条件/////////////////
	public static String[] getselectattrwherecondition(String sql) {
		String[] condi = new String[2];
		Pattern p = Pattern.compile("select ([\\s\\S]+)from (\\w+) where (\\w+)='(\\w+)';");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			condi[0] = m.group(3);
			condi[1] = m.group(4);
		}
		return condi;
	}
////////获取内容///////////////
	public static String writeinselectattrwhere(String tablename, int[] attrs, int col, String data) {
		String con = "";
		try {
			File idb = new File(ConstValue.contendir + File.separator + tablename + "idb.xls");
			Workbook workbook = Workbook.getWorkbook(idb);
			Sheet sheet = workbook.getSheet(0);
			Cell cell = null;
			for (int i = 0; i < sheet.getRows(); i++) {
				cell = sheet.getCell(col, i);
				if (cell.getContents().equals(data)) {
					for (int k : attrs) {
						cell = sheet.getCell(k, i);
						con = con + cell.getContents() + "\t";
					}
					con += "\n";
				}
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
////////////////////获取select * from student where Sname='wsx';/////////////////
	public static String getselectallwhere(String sql){
		String tablename=null;
		Pattern p3 = Pattern.compile("select \\* from (\\w+) where (\\w+)='(\\w+)';");
		Matcher m3 = p3.matcher(sql);
		while(m3.find()){
			tablename=m3.group(1);
		}
		return tablename;
	}
	//获取where后面的条件
	public static String[] getselectallwherecondition(String sql){
		String[] con=new String[2];
		Pattern p3 = Pattern.compile("select \\* from (\\w+) where (\\w+)='(\\w+)';");
		Matcher m3 = p3.matcher(sql);
		while(m3.find()){
			con[0]=m3.group(2);
			con[1]=m3.group(3);
		}
		return con;
	}
//	获取内容
	public static String writeinselectallwhere(String tablename, int col, String data) {
		String con = "";
		try {
			File idb = new File(ConstValue.contendir + File.separator + tablename + "idb.xls");
			Workbook workbook = Workbook.getWorkbook(idb);
			Sheet sheet = workbook.getSheet(0);
			Cell cell = null;
			for(int s=0;s<sheet.getColumns();s++){
				cell=sheet.getCell(s,0);
				con = con + cell.getContents() + "\t";
			}
			con += "\n";
			for (int i = 0; i < sheet.getRows(); i++) {
				cell = sheet.getCell(col, i);
				if (cell.getContents().equals(data)) {
					for (int k=0;k<sheet.getColumns();k++) {
						cell = sheet.getCell(k, i);
						con = con + cell.getContents() + "\t";
					}
					con += "\n";
				}
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
		String sql = "select * from student;";
		String sql1 = "select Sno,Snam from student;";
		String sql2 = "select Sno,Sname from student where Sage='20';";
		String sql4="select * from student where Sage='20';";
		// System.out.println(isselectsql(sql));
		// System.out.println(getselecttablename(sql));
		// System.out.println(getselectcontent(getselecttablename(sql)));
		// System.out.println(isselectattrsql(sql1));
		// System.out.println(isselectattrwheresql(sql2));
		// getselectattrtablename(sql1);
		// String[] con=getselectattrattrs(sql);
		// System.out.println(Arrays.toString(getselectattrattrs(sql1)));
		// System.out.println(isselect(sql));
		// System.out.println(isselect(sql1));
		// System.out.println(isselect(sql2));
		// String tablename=getselectattrtablename(sql1);
		// String[] attrs=getselectattrattrs(sql1);
		// System.out.println(Arrays.toString(attrs));
		// int[] attrint=new int[attrs.length];
		// for(int i=0;i<attrs.length;i++){
		// attrint[i]=Integer.valueOf(Delete.getinfocolumn(tablename,
		// attrs[i])).intValue();
		// }

		// int[] attrs={1,2,0};
		// System.out.println(getselectattrcontent(tablename, attrint));
		// System.out.println(getselectattrwheretablename(sql2));
		// String[] con=getselectattrwhereattrs(sql2);
		// System.out.println(Arrays.toString(con));
		// System.out.println(Arrays.toString(getselectattrwherecondition(sql2)));
//		int[] attrs = { 0, 1, 3 };
//		System.out.println(writeinselectattrwhere("student", attrs, 3, "50"));
		System.out.println(isselect(sql4));
		System.out.println(getselectallwhere(sql4));
	}

}
