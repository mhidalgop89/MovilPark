package ec.gob.movilpark.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec.gob.movilpark.dto.Parametros;
import ec.gob.movilpark.util.ConexionUtil;

public class DatosParametros extends ConexionUtil {

	
	public Map<String, Object> actualizaParametros (Parametros param, String usuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		
		ResultSet rs;

		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_actualiza_parametros(?,?,?,?,?,?,?,?,?,?)}"
	            );
			
			
			oraCallStmt.setDouble(1, param.getValorPorMinuto());
			oraCallStmt.setDouble(2, param.getTiempoMaxEnMinutos());
			oraCallStmt.setDouble(3, param.getMaximoDolares());
			oraCallStmt.setDouble(4, param.getValorMinimoDolares());
			oraCallStmt.setString(5, param.getEstado());
			oraCallStmt.setString(6, usuario);
			oraCallStmt.setDate(7,new java.sql.Date(param.getFechaIngresa().getTime()));
			oraCallStmt.setString(8, param.getUsuarioModifica());
			oraCallStmt.setDate(9, new java.sql.Date(new java.util.Date().getTime()));
			oraCallStmt.setInt(10, param.getParametrosID());
			
			rs=oraCallStmt.executeQuery();			

			
			//id_tramiteCosntitucion= oraCallStmt.getString(6);
			
		}catch(Exception e)
		{   mapResult.put("error", 999);
			mapResult.put("mensajeError", e.getMessage());
			e.printStackTrace();
		}finally
		{
			
			try {
				oraCallStmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
		}
		return mapResult; 
		
	}
	
}
