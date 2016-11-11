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

public class Delete {
	//判断符合delete 的哪种语法
	public static int isdelete(String sql) {
		int is = 0;
		Pattern p1 = Pattern.compile("delete from \\w+\\;");
		Matcher m1 = p1.matcher(sql);
		if (m1.matches()) {
			is = 1;
		}
		Pattern p2 = Pattern.compile("delete from \\w+ where \\w+=[']\\w+[']\\;");
		Matcher m2 = p2.matcher(sql);
		if (m2.matches()) {
			is = 2;
		}
		return is;
	}

	//////////// deleteall 1的方法///////////////////////////////////
	public static String getdeletealltablename(String sql) {//获取deleteall的表名
		String tablename = null;
		Pattern p = Pattern.compile("delete from (\\w+)\\;");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			tablename = m.group(1);
		}
		return tablename;
	}
	//在表文件中删除所有记录
	public static boolean writedeleteall(String tablename) {
		try {
			File idb = new File(ConstValue.contendir + File.separator + tablename + "idb.xls");
			Workbook wb = Workbook.getWorkbook(idb);
			WritableWorkbook wwb = Workbook.createWorkbook(idb, wb);
			WritableSheet sh = wwb.getSheet(0);
			Label label = null;
			for (int j = 0; j < sh.getColumns(); j++) {
				for (int i = 1; i < sh.getRows(); i++) {
					label = new Label(j, i, "");
					sh.addCell(label);
				}
			}
			wwb.write();
			wwb.close();
			wb.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	//////// deletewhere 2的方法////////////////////////////
	public static String[] getdeletewhere(String sql) {//获取deletewhere类型语句的属性集
		String[] con = new String[3];
		Pattern p = Pattern.compile("delete from (\\w+) where (\\w+)=[']([\\s\\S]+)[']\\;");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			con[0] = m.group(1);
			con[1] = m.group(2);
			con[2] = m.group(3);
		}
		return con;

	}

	// 判断表项info在表tablename的哪一列位置上
	public static String getinfocolumn(String tablename, String info) {
		String col = null;
		try {
			File idb = new File(ConstValue.contendir + File.separator + tablename + "idb.xls");
			Workbook workbook = Workbook.getWorkbook(idb);
			Sheet sheet = workbook.getSheet(0);
			Cell cell = null;
			for (int i = 0; i < sheet.getColumns(); i++) {
				cell = sheet.getCell(i, 0);
				if (cell.getContents().equals(info)) {
					col = "" + i;
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
		return col;
	}

	// 将deletewhere进行文件操作  删除相应行
	public static boolean writedeletewhere(String tablename, String attrvalue, int column) {
		try {
			File idb = new File(ConstValue.contendir + File.separator + tablename + "idb.xls");
			Workbook wb = Workbook.getWorkbook(idb);
			WritableWorkbook wwb = Workbook.createWorkbook(idb, wb);
			WritableSheet sh = wwb.getSheet(0);
			Cell cell = null;
			Label label = null;
			for (int i = 1; i < sh.getRows(); i++) {
				cell = sh.getCell(column, i);
				if (cell.getContents().equals(attrvalue)) {
					for (int j = 0; j < sh.getColumns(); j++) {
						label = new Label(j, i, null);
						sh.addCell(label);
					}
				}
			}
			wwb.write();
			wwb.close();
			wb.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		String sql1 = "delete from student;";
		String sql2 = "delete from student where Sno='男';";

		// System.out.println(isdeleteall(sql1));
		// String tablename=getdeletealltablename(sql1);
		// System.out.println(tablename);
		// writedeleteall(tablename);

		// System.out.println(isdeletewhere(sql2));
		String[] con = getdeletewhere(sql2);
		System.out.println(Arrays.toString(con));
		// String tablename="student";
		// String info="Sdept";
		// System.out.println(getinfocolumn(tablename,info));
		// writedeletewhere(tablename,"201401061329",0);

		// System.out.println(isdelete(sql1));
		System.out.println(isdelete(sql2));

	}

}
