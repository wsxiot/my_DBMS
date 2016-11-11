package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import main.ConstValue;

public class Update {
//检查是否符合update语法
	public static boolean isupdatewhere(String sql) {
		boolean is = false;
		Pattern p = Pattern.compile("update (\\w+) set (\\w+)='(\\w+)' where (\\w+)='(\\w+)'\\;");
		Matcher m = p.matcher(sql);
		if (m.matches()) {
			is = true;
		}
		return is;
	}
//获取update语句的信息集
	public static String[] getupdatewhereinfo(String sql) {
		String[] con = null;
		con = new String[5];
		Pattern p = Pattern.compile("update (\\w+) set (\\w+)='(\\w+)' where (\\w+)='(\\w+)'\\;");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			con[0] = m.group(1);
			con[1] = m.group(2);
			con[2] = m.group(3);
			con[3] = m.group(4);
			con[4]=m.group(5);
		}
		return con;
	}
	//将update语句对文件操作  更改相应的行
	public static boolean writeinupdatewhere(String tablename,int col1,String attrvalue1,int col2,String attrvalue2){
		try {
			File idb=new File(ConstValue.contendir+File.separator+tablename+"idb.xls");
			Workbook wb=Workbook.getWorkbook(idb);
			WritableWorkbook wwb=Workbook.createWorkbook(idb, wb);
			WritableSheet sh=wwb.getSheet(0);
			Cell cell=null;
			Label label=null;
			for(int i=0;i<sh.getRows();i++){
				cell=sh.getCell(col2,i);
				if(cell.getContents().equals(attrvalue2)){
					label=new Label(col1,i,attrvalue1);
					sh.addCell(label);
				}
			}
			wwb.write();
			wwb.close();
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
		String sql = "update student set Sage='22' where Sno='201401061423';";
		System.out.println(isupdatewhere(sql));
		System.out.println(Arrays.toString(getupdatewhereinfo(sql)));
		writeinupdatewhere("student",3,"22",0,"201401061423");
		
		
	}

}
