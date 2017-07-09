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
import ec.gob.movilpark.dto.Perfil;
import ec.gob.movilpark.dto.RelacionPerfilPermiso;
import ec.gob.movilpark.util.ConexionUtil;


public class PerfilesDao extends ConexionUtil {

	
	public Map<String, Object> consultaPerfil()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Perfil>lsPerfil=new ArrayList<Perfil>();
		Perfil perfil;// = new Perfil() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_perfil()}"
	            );			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				perfil  = new Perfil() ;
				perfil.setIdPerfil(rs.getInt(1));
				perfil.setNombre(rs.getString(2));
				perfil.setEstado(rs.getString(3));
				lsPerfil.add(perfil);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("perfiles", lsPerfil);
			
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
	//sp_edita_perfil
	public Map<String, Object> editaPerfil(Perfil objPerfil)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		boolean ejecuta=false;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_edita_perfil(?,?,?)}"
	            );			
			oraCallStmt.setString(1, objPerfil.getNombre());
			oraCallStmt.setString(2, objPerfil.getEstado());
			oraCallStmt.setInt(3, objPerfil.getIdPerfil());
			ejecuta=oraCallStmt.execute();
			System.out.println("ejecuta: "+ejecuta);			
			
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
	
	public Map<String, Object> insertaPerfil(String nombre)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		boolean ejecuta=false;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_perfil(?)}"
	            );			
			oraCallStmt.setString(1, nombre);
			ejecuta=oraCallStmt.execute();
			System.out.println("ejecuta: "+ejecuta);			
			
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
	
	
	public Map<String, Object> buscaPerfilPorUsuario  (int idUsuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		
		Perfil perfil = new Perfil() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_busca_perfil_x_usuario(?)}"
	            );
			
			oraCallStmt.setInt(1, idUsuario);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				
				perfil.setIdPerfil(rs.getInt(1));
				perfil.setNombre(rs.getString(2));
				perfil.setEstado(rs.getString(3));
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("perfil", perfil);
			
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
	
	
	
	
	public Map<String, Object> buscaRelacionPerfilPermiso(Perfil perfil, String prmValor)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		
		RelacionPerfilPermiso perfilPermiso = new RelacionPerfilPermiso() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_busca_relacion_perfil_permiso(?,?)}"
	            );
			
			oraCallStmt.setInt(1, perfil.getIdPerfil());
			oraCallStmt.setString(2, prmValor);
						
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				System.out.println("RS");
				perfilPermiso.setIdRelacionPerfilPermiso(rs.getInt(1));
				perfilPermiso.setIdPerfil(rs.getInt(2));
				perfilPermiso.setIdPermiso(rs.getInt(3));
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			System.out.println("entra a rs xD"+perfilPermiso.getIdRelacionPerfilPermiso()+" perfil.getIdPerfil():"+perfil.getIdPerfil()+
					" valor"+prmValor);
			mapResult.put("perfilPermiso", perfilPermiso);
			
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
