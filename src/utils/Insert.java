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

public class Insert {

	//是否符合insert语句语法
	public static boolean isinsertsql(String sql){
		boolean is=true;
		String sql1 = sql.substring(0, sql.length() - 2);
		String sql2 = sql.substring(sql.length() - 2);
		sql = sql1 + "," + sql2;
		Pattern p = Pattern.compile("insert into \\w+ values\\(([\\'][\\s|\\S]+[\\']\\,)+\\)\\;");
		Matcher m = p.matcher(sql);
		if (!m.matches()) {
			is = false;
		} 
		return is;
	}
	//获取insert语句的表名
	public static String getinserttablename(String sql){
		int tablef=sql.indexOf(" ");
		tablef=sql.indexOf(" ", tablef+1);
		int tablel=sql.indexOf(" ",tablef+1);
		String tablename=sql.substring(tablef+1,tablel);
		return tablename;
		
	}
	//获取insert语句的信息集
	public static String[] getinsertcontent(String sql){
		int kuohaof=sql.indexOf("(");
		int kuohaol=sql.indexOf(")");
		String sql1=sql.substring(kuohaof+1,kuohaol);
		//System.out.println(sql1);
		String[] conn=sql1.split("(\\'\\,\\')|\\'");
		String[] con=new String[conn.length-1];//长度为3   0,1,2
		for(int i=1;i<conn.length;i++){
			con[i-1]=conn[i];
		}
		return con;
	}
	//执行insert语句 对文件插入操作
	public static boolean insertcontent(String tablename,String[] con){
		boolean is=true;
		File idb=new File(ConstValue.contendir+File.separator+tablename+"idb.xls");
		try {
			Workbook wb=Workbook.getWorkbook(idb);
			WritableWorkbook wwb=Workbook.createWorkbook(idb, wb);
			WritableSheet sh=wwb.getSheet(0);
			int row=sh.getRows();
			Label label=null;
			for(int i=0;i<con.length;i++){
				label=new Label(i,row,con[i]);
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
		
		return is;
	}
	
	
	
	
	
//	public static boolean insertcontent(String tablename,String[] con){
//		boolean is=true;
//		File idb=new File(ConstValue.contendir+File.separator+tablename+"idb.xls");
//		try {
//			Workbook wb=Workbook.getWorkbook(idb);
//			WritableWorkbook wwb=Workbook.createWorkbook(idb, wb);
//			WritableSheet sh=wwb.getSheet(0);
//			Cell cell=null;
//			Label label=null;
//			for(int i=0;i<sh.getRows()+1;i++){
//				int flag=0;
//				for(int j=0;j<sh.getColumns();j++){
//					cell=sh.getCell(j,i);
//					if(!cell.getContents().equals("")){
//						flag=1;
//						break;
//					}
//				}
//				if(flag==0){
//					for(int k=0;k<con.length;k++){
//						label=new Label(k,i,con[k]);
//						sh.addCell(label);
//					}
//					break;
//				}
//			}
//			wwb.write();
//			wwb.close();
//			wb.close();
//		} catch (BiffException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (RowsExceededException e) {
//			e.printStackTrace();
//		} catch (WriteException e) {
//			e.printStackTrace();
//		}
//		
//		return is;
//	}
	
	
	
	
	
	
	public static int gettablerows(String tablename){
		int count=0;
		File frm=new File(ConstValue.contendir+File.separator+tablename+"frm.xls");
		try {
			Workbook workbook = Workbook.getWorkbook(frm);
			Sheet sheet = workbook.getSheet(0);
			count=sheet.getRows()-1;
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
