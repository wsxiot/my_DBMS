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

public class GrantandRevoke {
	/////// �ж��Ƿ�����һ��������ʽ///////////////////////////////////////////////////////
	public static int isgrantsql(String sql) {
		int is = 0;
		Pattern p1 = Pattern.compile("grant all privileges on table \\w+ to \\w+\\;");
		Matcher m1 = p1.matcher(sql);
		if (m1.matches()) {
			is = 1;
		}

		Pattern p2 = Pattern.compile("grant \\w+ on table \\w+ to \\w+\\;");
		Matcher m2 = p2.matcher(sql);
		if (m2.matches()) {
			is = 2;
		}
		return is;
	}

	/////////// ��ȡ����ֵΪ1��������ʽ����Ϣ///////////////////////////////
	//��ȡ����grant all����Ϣ��
	public static String[] getlonggrantinfo(String sql) {
		String[] con = new String[2];
		Pattern p1 = Pattern.compile("grant all privileges on table (\\w+) to (\\w+)\\;");
		Matcher m1 = p1.matcher(sql);
		while (m1.find()) {
			con[0] = m1.group(1);
			con[1] = m1.group(2);
		}
		return con;
	}
	//��grant all����Ϣ��д���ļ�
	public static boolean writeinlongfrm(String[] con) {
		String tablename = con[0];
		String username = con[1];

		try {
			WritableWorkbook wwb;
			File frm = new File(ConstValue.contendir + File.separator + tablename + "frm.xls");
			Workbook wb = Workbook.getWorkbook(frm);
			wwb = Workbook.createWorkbook(frm, wb);
			WritableSheet sh = wwb.getSheet(1);
			int row = sh.getRows();

			Label label = null;
			for (int i = 0; i < 4; i++) {
				label = new Label(i, row, username);
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
		return true;
	}

	/////////// ��ȡ����ֵΪ2��������ʽ����Ϣ///////////////////////////////
//	��ȡgrant on to����Ϣ��
	public static String[] getshortgrantinfo(String sql) {
		String[] con = new String[3];
		Pattern p2 = Pattern.compile("grant (\\w+) on table (\\w+) to (\\w+)\\;");
		Matcher m2 = p2.matcher(sql);
		while (m2.find()) {
			con[0] = m2.group(1);
			con[1] = m2.group(2);
			con[2] = m2.group(3);
		}
		return con;
	}
//��grant on toд���ļ�
	public static boolean writeinshortfrm(String[] con) {
		String type = con[0];
		String tablename = con[1];
		String username = con[2];
		int col = 5;
		switch (type) {
		case "select":
			col = 0;
			break;
		case "insert":
			col = 1;
			break;
		case "delete":
			col = 2;
			break;
		case "update":
			col = 3;
			break;
		default:
			break;
		}
		try {
			File frm = new File(ConstValue.contendir + File.separator + tablename + "frm.xls");
			WritableWorkbook wwb;
			Workbook wb = Workbook.getWorkbook(frm);
			wwb = Workbook.createWorkbook(frm, wb);
			WritableSheet sh = wwb.getSheet(1);
			int row = sh.getRows();
			Label label = null;
			label = new Label(col, row, username);
			sh.addCell(label);
			wwb.write();
			wwb.close();
			wb.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/////// ��ѯ�Ƿ���username�û��Ա�tablename��permȨ��
	public static boolean getisthisperm(String perm, String tablename, String username) {
		boolean is = false;
		int col = 6;
		switch (perm) {
		case "select":
			col = 0;
			break;
		case "insert":
			col = 1;
			break;
		case "delete":
			col = 2;
			break;
		case "update":
			col = 3;
			break;
		default:
			break;
		}
		try {
			File frm = new File(ConstValue.contendir + File.separator + tablename + "frm.xls");
			Workbook workbook = Workbook.getWorkbook(frm);
			Sheet sheet = workbook.getSheet(1);
			for (int i = 0; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(col, i);
				if (cell.getContents().equals(username)) {
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
	//�ж��Ƿ����revoke���﷨Ҫ��
	public static boolean isrevokesql(String sql){
		boolean is= false;
		Pattern p1 = Pattern.compile("revoke (\\w+) on table (\\w+) from (\\w+)\\;");
		Matcher m1 = p1.matcher(sql);
		if (m1.matches()) {
			is = true;
		}
		return is;
	}
//��ȡrevoke����Ϣ��
	public static String[] getrevokeinfo(String sql){
		String[] con= new String[3];
		Pattern p1 = Pattern.compile("revoke (\\w+) on table (\\w+) from (\\w+)\\;");
		Matcher m1 = p1.matcher(sql);
		while(m1.find()){
			con[0]=m1.group(1);
			con[1]=m1.group(2);
			con[2]=m1.group(3);
		}
		return con;
	}
//��revoke�����ļ����� д���ļ�
	public static boolean writeinrevoke(String[] con){
		int col = 7;
		String type = con[0];
		String tablename = con[1];
		String username = con[2];
		switch (type) {
		case "select":
			col = 0;
			break;
		case "insert":
			col = 1;
			break;
		case "delete":
			col = 2;
			break;
		case "update":
			col = 3;
			break;
		default:
			break;
		}
		
		try {
			File frm=new File(ConstValue.contendir+File.separator+tablename+"frm.xls");
			Workbook wb=Workbook.getWorkbook(frm);
			WritableWorkbook wwb=Workbook.createWorkbook(frm, wb);
			WritableSheet sh=wwb.getSheet(1);
			Cell cell=null;
			Label label=null;
			for(int i=0;i<sh.getRows();i++){
				cell=sh.getCell(col,i);
				if(cell.getContents().equals(username)){
					label=new Label(col,i,"");
					sh.addCell(label);
				}
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
	public static void main(String[] args) {
		String sql1 = "grant all privileges on table student to u1;";
		String sql2 = "grant select on table student to u1;";
		String sql3="revoke update on table student from u1;";
		System.out.println(isgrantsql(sql1));

		String[] con = getlonggrantinfo(sql1);
		System.out.println(Arrays.toString(con));
		// writeinlongfrm(con);

		String[] con1 = getshortgrantinfo(sql2);
		System.out.println(Arrays.toString(con1));
		// writeinshortfrm(con1);

		String[] con2 = getrevokeinfo(sql3);
		System.out.println(Arrays.toString(con2));
		writeinrevoke(con2);
		// System.out.println(getisthisperm("select", "student", "u1"));

	}

}
