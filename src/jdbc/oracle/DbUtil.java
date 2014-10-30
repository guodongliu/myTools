package jdbc.oracle;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbUtil {
	static private String fileName="src/jdbc/oracle/db.properties";
	static private String driver = "";  
	static private String url = "";  
	static private String username ="";  
	static private String password = "";  
	static Connection conn=null; 
    
	public static Connection getConn(){
		Properties p = new Properties();  
		try{
			FileInputStream  in =  new FileInputStream(fileName);
            p.load(in);
            in.close();  
            driver = p.getProperty("drivers");  
            url = p.getProperty("url");  
            username = p.getProperty("user");  
            password = p.getProperty("password");
        } catch (IOException ex) {  
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);  
        }try {
        	 Class.forName(driver).newInstance();      
             conn = DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			System.out.print("加载驱动出错");  
            e.printStackTrace();
		}
		return conn; 
	}
	
}
