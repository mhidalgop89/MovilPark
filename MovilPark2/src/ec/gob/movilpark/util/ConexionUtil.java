package ec.gob.movilpark.util;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ConexionUtil {
	
	
	public Connection getConnection()// throws ClassNotFoundException, SQLException
    {
        //Change these settings according to your local configuration
		Connection conn =null;
        try{

       
		String driver =ReadPropertiesUtil.obtenerProperty("ec.gob.movilpark.util.driver");// "com.mysql.jdbc.Driver";
        String connectString = ReadPropertiesUtil.obtenerProperty("ec.gob.movilpark.util.connString");//"jdbc:mysql://localhost:3306/eforkliftitems";
        String user = ReadPropertiesUtil.obtenerProperty("ec.gob.movilpark.util.user");//"root";
        String password = ReadPropertiesUtil.obtenerProperty("ec.gob.movilpark.util.pass");//"admin";
        System.out.println("driver: "+driver+" connectString: "+connectString+" user: "+user+" password: "+password);
        Class.forName(driver);
        conn = DriverManager.getConnection(connectString, user, password);
      System.out.println("llega mySql");

 //        String driver = "com.mysql.jdbc.Driver";
//            String connectString = "jdbc:mysql://localhost:3306/gmap";
//            String user = "admin";
//            String password = "e7ec7354d5";
//            Class.forName(driver);
//            conn = DriverManager.getConnection(connectString, user, password);
//          System.out.println("llega mySql");
      
        }
        
        
        catch(Exception e)
        {e.printStackTrace();}
          return conn;
    }
	
	public static void main(String args[]){
		ConexionUtil conexion = new ConexionUtil();
		Connection conn= conexion.getConnection();
		String proc= "{call sp_movilpark_login (?,?)}";
		CallableStatement cs;
		ResultSet rs;
			
		try {
			cs = conn.prepareCall(proc);
			cs.setString(1, "mario.hidalgo89@hotmail.com");
			cs.setString(2, "123");
			
			rs=cs.executeQuery();
			
			while(rs.next()){
				
				System.out.println("Usuario: "+rs.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
