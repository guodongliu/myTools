import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import jdbc.oracle.DbUtil;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;





public class validCode {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws  
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> bdMap = getDistrictMap();
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("D:\\Sheet1.xls"));
		HSSFSheet sheet = wb.getSheetAt(0);
		String start_place,end_place;
		for(int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++){
			HSSFRow row = sheet.getRow( rowNum); 
			if(row == null){  
		          continue;  
		    }else{
		    	start_place = row.getCell(3).toString();
		    	Cell start_code = row.getCell(4);
		    	end_place = row.getCell(5).toString();
		    	Cell end_code = row.getCell(6);
		    	if(bdMap.get(start_place)!= null && !bdMap.get(start_place).toString().equals("")){
		    		start_code.setCellValue((bdMap.get(start_place).toString()));
		    	}
		    	if(bdMap.get(end_place)!= null && !bdMap.get(end_place).toString().equals("")){
		    		end_code.setCellValue((bdMap.get(end_place).toString()));
		    	}
		    }
		}
		
		wb.write(new FileOutputStream("D:\\Sheet1.xls"));
		System.out.println("完成");
		
	}
	public  static Map<String,String> getDistrictMap() throws SQLException{
		Map<String,String> bd = new HashMap<String,String>();
		Connection connection = DbUtil.getConn();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT   district_short_name, district_code ")
		.append("            FROM bd_cusg_district ")
		.append("        MINUS ")
		.append("        SELECT   district_short_name, district_code ")
		.append("            FROM bd_cusg_district bcd1 ")
		.append("           WHERE EXISTS ( ")
		.append("                    SELECT 1 ")
		.append("                      FROM bd_cusg_district bcd2 ")
		.append("                     WHERE bcd2.district_short_name = bcd1.district_short_name ")
		.append("                       AND SUBSTR (bcd1.district_code, ")
		.append("                                   0, ")
		.append("                                   LENGTH (bcd1.district_code) - 2 ")
		.append("                                  ) = bcd2.district_code) ")
		.append("        ORDER BY district_code ");
		PreparedStatement ps = connection.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			bd.put(rs.getObject(1).toString(), rs.getObject(2).toString());
		}
		return bd;
	}
	public static JFrame getFrame(){
		JFrame winFrame = new JFrame("合同匹配编码");
		return winFrame;
	}
}
