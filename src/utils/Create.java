package utils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import main.ConstValue;

public class Create {

	// �Ƿ����sql create table�﷨
	public static boolean iscreatesql(String sql) {
		boolean is = true;
		String sql1 = sql.substring(0, sql.length() - 2);
		String sql2 = sql.substring(sql.length() - 2);
		sql = sql1 + "," + sql2;
		Pattern p = Pattern.compile(
				"(create table [a-zA-Z]+\\()((([a-zA-Z]+ [a-zA-Z]+\\,)|(([a-zA-Z]+ [a-zA-Z]+)\\(\\d+\\)\\,))+)(\\)\\;)");
		Matcher m = p.matcher(sql);
		if (!m.matches()) {
			is = false;
		}
		return is;
	}

	// create table��ñ���
	public static String getcreatetablename(String sql) {
		int tablef = sql.indexOf(" ");
		tablef = sql.indexOf(" ", tablef + 1);
		int tablel = sql.indexOf("(");
		String table = sql.substring(tablef + 1, tablel);
		return table;
	}

	// create table��������ַ���
	public static String[] getcreatenature(String sql) {

		int first = sql.indexOf("(");
		int last = sql.lastIndexOf(")");
		String child = sql.substring(first + 1, last);
		String[] sp = child.split("[\\,| ]");// ��������ַ���
		return sp;
	}

	public static boolean inittable(String tablename, String[] info) {// ��ʼ�����������������ļ�
		File frm = new File(ConstValue.contendir + File.separator + tablename + "frm.xls");
		File idb = new File(ConstValue.contendir + File.separator + tablename + "idb.xls");
		//// �������������ļ�/////////////////////////////////////////////////
		try {
			if (!frm.exists()) {// ��ʼ��frm�������һ��sheet
				try {
					frm.createNewFile();
					WritableWorkbook workbook = Workbook.createWorkbook(frm);
					Sheet sheet = workbook.createSheet("frm", 0);
					Sheet sheet1 = workbook.createSheet("perm", 1);
					workbook.write();
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
			if (!idb.exists()) {// ��ʼ��idb�������һ��sheet
				try {
					idb.createNewFile();
					WritableWorkbook workbook = Workbook.createWorkbook(idb);
					Sheet sheet = workbook.createSheet("idb", 0);
					workbook.write();
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		///// ����ṹд��frm�ļ�////////////////////////////////////////
		try {
			Workbook wb = Workbook.getWorkbook(frm);
			WritableWorkbook wwb = Workbook.createWorkbook(frm, wb);
			WritableSheet sh = wwb.getSheet(0);
			String[] title = { "field", "type", "cannull", "key", "unique" };
			Label label = null;
			for (int i = 0; i < title.length; i++) {
				label = new Label(i, 0, title[i]);
				sh.addCell(label);
			}
			int row = 0;
			for (int i = 0; i < info.length / 2; i++) {
				row = sh.getRows();
				label = new Label(0, row, info[i * 2]);
				sh.addCell(label);
				label = new Label(1, row, info[i * 2 + 1]);
				sh.addCell(label);
			}
			wwb.write();
			wwb.close();
			wb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		/// ��perm�ı�ͷд��perm��sheetҳ////////////////////////////
		try {
			Workbook wb = Workbook.getWorkbook(frm);
			WritableWorkbook wwb = Workbook.createWorkbook(frm, wb);
			WritableSheet sh = wwb.getSheet(1);
			String[] title = { "select", "insert", "delete", "update" };
			Label label = null;
			for (int i = 0; i < title.length; i++) {
				label = new Label(i, 0, title[i]);
				sh.addCell(label);
			}
			wwb.write();
			wwb.close();
			wb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		// ����ͷд��idb���ļ�///////////////////////////////////////////
		try {
			Workbook wb = Workbook.getWorkbook(idb);
			WritableWorkbook wwb = Workbook.createWorkbook(idb, wb);
			WritableSheet sh = wwb.getSheet(0);
			Label label = null;
			for (int i = 0; i < info.length / 2; i++) {
				label = new Label(i, 0, info[i * 2]);
				sh.addCell(label);
			}
			wwb.write();
			wwb.close();
			wb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean writeintablecon(String tablename) {// ������д��tablecon�ļ�
		boolean is = true;
		try {
			File tablecon = new File(ConstValue.tablecon);
			Workbook wb = Workbook.getWorkbook(tablecon);
			WritableWorkbook wwb = Workbook.createWorkbook(tablecon, wb);
			WritableSheet sh = wwb.getSheet(0);
			int row = sh.getRows();
			Label label = null;
			label = new Label(0, row, tablename);
			sh.addCell(label);

			wwb.write();
			wwb.close();
			wb.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return is;
	}

	///////////////////////////////////////////////////
	public static boolean isview(String sql) {//�ж��Ƿ���Ͻ�����ͼ���﷨
		boolean is = true;
		String viewname = null;
		String child = null;
		Pattern p = Pattern.compile("create view (\\w+) as ([\\s\\S]+)");
		Matcher m = p.matcher(sql);
		while (!m.matches()) {
			is = false;
		}
		if (is != false) {
			Pattern p1 = Pattern.compile("create view (\\w+) as ([\\s\\S]+)");
			Matcher m1 = p1.matcher(sql);
			while (m1.find()) {
				viewname = m1.group(1);
				child = m1.group(2);
			}
			if (Select.isselect(child) == 0) {
				is = false;
			}
		}
		return is;
	}
	//�ڴ�����ͼ������л�ȡ��ͼ��
	public static String getviewname(String sql) {
		String viewname = null;
		Pattern p = Pattern.compile("create view (\\w+) as ([\\s\\S]+)");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			viewname = m.group(1);
		}
		return viewname;
	}

	public static String getviewcontent(String sql) {
		String con = null;
		Pattern p = Pattern.compile("create view (\\w+) as ([\\s\\S]+)");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			con = m.group(2);
		}
		return con;
	}
	//����ͼ������ͼ��Ϣд����ͼȫ���ļ�
	public static boolean writeinview(String viewname, String con) {

		try {
			File view = new File(ConstValue.contendir + File.separator + "view.xls");
			Workbook wb = Workbook.getWorkbook(view);
			WritableWorkbook wwb = Workbook.createWorkbook(view, wb);
			WritableSheet sh = wwb.getSheet(0);
			int row = sh.getRows();
			Label label = null;
			label = new Label(0, row, viewname);
			sh.addCell(label);
			label = new Label(1, row, con);
			sh.addCell(label);
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
		String sql = "create view name as select Sno from student;";
		System.out.println(isview(sql));
		System.out.println(getviewname(sql));
		System.out.println(getviewcontent(sql));
		writeinview(getviewname(sql),getviewcontent(sql) );
	}

}
