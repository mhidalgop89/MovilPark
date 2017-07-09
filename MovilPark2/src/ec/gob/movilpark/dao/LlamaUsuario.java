package ec.gob.movilpark.dao;

import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ec.gob.movilpark.dto.Usuario;
import ec.gob.movilpark.util.ConexionUtil;

public class LlamaUsuario extends ConexionUtil{
	
	public Map<String, Object> obtieneUsuario  (String usuario, String pass)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		Usuario usu= new Usuario();
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_movilpark_login(?,?)}"
	            );
//			oraCallStmt.registerOutParameter(1, Types.INTEGER);
			oraCallStmt.setString(1, usuario);
			oraCallStmt.setString(2, pass);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				usu.setUsuarioId(rs.getInt("usuarioID"));
				usu.setUsuario(rs.getString("usuario"));
				usu.setIdentificacion(rs.getString("identificacion"));
				usu.setNombre(rs.getString("nombre"));
				usu.setPerId(rs.getInt("per_id"));
				usu.setTlf_movil(rs.getString("tlf_movil"));
				usu.setCodigoPostal(rs.getString("codigo_postal"));
				usu.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
				usu.setUsuarioIngresa(rs.getString("usuario_ingresa"));
				usu.setFechaIngresa(rs.getDate("fecha_ingresa"));
				usu.setFechaModifica(rs.getDate("fecha_modifica"));
				
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("usuario", usu);
			
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
	
	
	public Map<String, Object> consultaExisteUsuario(String usuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		boolean existe=false;
		ResultSet rs;
		Usuario usu= new Usuario();

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_existe_usuario(?)}"
	            );
//			oraCallStmt.registerOutParameter(1, Types.INTEGER);
			oraCallStmt.setString(1, usuario);

			
			rs=oraCallStmt.executeQuery();
			while(rs.next()){
				usu.setUsuarioId(rs.getInt("usuarioID"));
				usu.setUsuario(rs.getString("usuario"));
				usu.setIdentificacion(rs.getString("identificacion"));
				usu.setNombre(rs.getString("nombre"));
				usu.setApellido(rs.getString("apellido"));
				usu.setPerId(rs.getInt("per_id"));
				usu.setTlf_movil(rs.getString("tlf_movil"));
				usu.setCodigoPostal(rs.getString("codigo_postal"));
				usu.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
				usu.setUsuarioIngresa(rs.getString("usuario_ingresa"));
				usu.setFechaIngresa(rs.getDate("fecha_ingresa"));
				usu.setFechaModifica(rs.getDate("fecha_modifica"));
				usu.setEstado(rs.getString("estado"));
				usu.setEmail(rs.getString("email"));
				usu.setPass(rs.getString("pass"));
				existe=true;
			}
			mapResult.put("existe", existe);
			
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
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}	
	
	
	
	public Map<String, Object> actualizaPasswordPorUsuario(String usuario,String newPassword)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		boolean ejecutado;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_actualiza_pass_x_usuario(?,?)}"
	            );
//			oraCallStmt.registerOutParameter(1, Types.INTEGER);
			oraCallStmt.setString(1, usuario);
			oraCallStmt.setString(2, newPassword);

			
			ejecutado=oraCallStmt.execute();
			System.out.println("ejecutado: "+ejecutado);
			
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
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}
	
	
	public Map<String, Object> actualizaPassword(int idUsuario,String newPassword)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		boolean ejecutado;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_actualiza_password(?,?)}"
	            );
//			oraCallStmt.registerOutParameter(1, Types.INTEGER);
			oraCallStmt.setInt(1, idUsuario);
			oraCallStmt.setString(2, newPassword);

			
			ejecutado=oraCallStmt.execute();
			System.out.println("ejecutado: "+ejecutado);
			
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
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}
	
	
	

	
	
	public Map<String, Object> insertaVehiculo(String placa, int idUsuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		boolean ejecutado;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_vehiculos(?,?)}"
	            );
//			oraCallStmt.registerOutParameter(1, Types.INTEGER);
			oraCallStmt.setString(1, placa);
			oraCallStmt.setInt(2, idUsuario);
			
			
			ejecutado=oraCallStmt.execute();
			System.out.println("ejecutado: "+ejecutado);
			
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
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}
	
	

	
	
	public Map<String, Object> editaUsuario  (Usuario objUsuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		boolean ejecutado;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_edita_usuario(?,?,?,?,?,?,?,?,?,?,?,?)}"
	            );
//			oraCallStmt.registerOutParameter(1, Types.INTEGER);
			oraCallStmt.setInt(1, objUsuario.getUsuarioId());
			oraCallStmt.setString(2, objUsuario.getPass());
			oraCallStmt.setString(3, objUsuario.getIdentificacion());
			oraCallStmt.setString(4, objUsuario.getNombre());
			oraCallStmt.setString(5, objUsuario.getApellido());
			oraCallStmt.setString(6, objUsuario.getEmail());
			oraCallStmt.setString(7, objUsuario.getTlf_movil());
			oraCallStmt.setString(8, objUsuario.getUsuarioModifica());
			oraCallStmt.setDate(9, new java.sql.Date(objUsuario.getFechaModifica().getTime()));
			oraCallStmt.setString(10, objUsuario.getEstado());
			
			oraCallStmt.setString(11, objUsuario.getCodigoProvincia());
			oraCallStmt.setString(12, objUsuario.getCodigpCiudad());
			
			
			ejecutado=oraCallStmt.execute();
			System.out.println("ejecutado: "+ejecutado);
			
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
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}
	
	
	
	
	public Map<String, Object> insertaUsuario2  (Usuario objUsuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		boolean ejecutado;
		int idPersona;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_usuarios_ret_id(?,?,?,?,?,?,?,?,?,?,?)}"
	            );
//			oraCallStmt.registerOutParameter(1, Types.INTEGER);
			oraCallStmt.setString(1, objUsuario.getUsuario());
			oraCallStmt.setString(2, objUsuario.getPass());
			oraCallStmt.setString(3, objUsuario.getIdentificacion());
			oraCallStmt.setString(4, objUsuario.getNombre());
			oraCallStmt.setString(5, objUsuario.getApellido());
			oraCallStmt.setString(6, objUsuario.getEmail());
			oraCallStmt.setString(7, objUsuario.getTlf_movil());
			oraCallStmt.setString(8, objUsuario.getUsuarioIngresa());
			oraCallStmt.setDate(9, new java.sql.Date( new java.util.Date().getTime()  ));
			oraCallStmt.setInt(10, objUsuario.getPerId());
			oraCallStmt.registerOutParameter(11, Types.INTEGER);
			
			ejecutado=oraCallStmt.execute();
			System.out.println("ejecutado: "+ejecutado);
			
			idPersona = oraCallStmt.getInt(11);
			 mapResult.put("idPersona", idPersona);
			
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
	
	
	
	
	
	public Map<String, Object> insertaUsuario  (Usuario objUsuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		boolean ejecutado;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_usuarios(?,?,?,?,?,?,?,?,?,?,?,?,?)}"
	            );
//			oraCallStmt.registerOutParameter(1, Types.INTEGER);
			oraCallStmt.setString(1, objUsuario.getUsuario());
			oraCallStmt.setString(2, objUsuario.getPass());
			oraCallStmt.setString(3, objUsuario.getIdentificacion());
			oraCallStmt.setString(4, objUsuario.getNombre());
			oraCallStmt.setString(5, objUsuario.getApellido());
			oraCallStmt.setString(6, objUsuario.getEmail());
			oraCallStmt.setString(7, objUsuario.getTlf_movil());
			oraCallStmt.setString(8, objUsuario.getUsuarioIngresa());
			oraCallStmt.setDate(9, new java.sql.Date(objUsuario.getFechaIngresa().getTime()));
			oraCallStmt.setInt(10, objUsuario.getIdTipoUsuario());
			oraCallStmt.setString(11,objUsuario.getCodigoProvincia());
			oraCallStmt.setString(12,objUsuario.getCodigpCiudad());
			oraCallStmt.setInt(13, objUsuario.getPerId());
			ejecutado=oraCallStmt.execute();
			System.out.println("ejecutado: "+ejecutado);
			
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
	
	public Map<String, Object> consultaUsuario  ()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		Usuario usu;//= new Usuario();
		List<Usuario> listUsuario= new ArrayList<Usuario>();
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_usuarios()}"
	            );

			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				usu= new Usuario();
				usu.setUsuarioId(rs.getInt("usuarioID"));
				usu.setUsuario(rs.getString("usuario"));
				usu.setIdentificacion(rs.getString("identificacion"));
				usu.setNombre(rs.getString("nombre"));
				usu.setApellido(rs.getString("apellido"));
				usu.setPerId(rs.getInt("per_id"));
				usu.setTlf_movil(rs.getString("tlf_movil"));
				usu.setCodigoPostal(rs.getString("codigo_postal"));
				usu.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
				usu.setUsuarioIngresa(rs.getString("usuario_ingresa"));
				usu.setFechaIngresa(rs.getDate("fecha_ingresa"));
				usu.setFechaModifica(rs.getDate("fecha_modifica"));
				usu.setEstado(rs.getString("estado"));
				usu.setEmail(rs.getString("email"));
				usu.setPass(rs.getString("pass"));
				usu.setCodigoProvincia(rs.getString("pro_codigo"));
				usu.setCodigpCiudad(rs.getString("ciu_codigo"));
				usu.setProvincia(rs.getString("provincia"));
				usu.setCiudad(rs.getString("ciudad"));
				
				listUsuario.add(usu);
				
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("usuarios", listUsuario);
			
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
	
	public Map<String, Object> consultaUsuarioNc()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		Usuario usu;//= new Usuario();
		List<Usuario> listUsuario= new ArrayList<Usuario>();
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_usuarios_nc()}"
	            );

			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				usu= new Usuario();
				usu.setUsuarioId(rs.getInt("usuarioID"));
				usu.setUsuario(rs.getString("usuario"));
				usu.setIdentificacion(rs.getString("identificacion"));
				usu.setNombre(rs.getString("nombre"));
				usu.setApellido(rs.getString("apellido"));
				usu.setPerId(rs.getInt("per_id"));
				usu.setTlf_movil(rs.getString("tlf_movil"));
				usu.setCodigoPostal(rs.getString("codigo_postal"));
				usu.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
				usu.setUsuarioIngresa(rs.getString("usuario_ingresa"));
				usu.setFechaIngresa(rs.getDate("fecha_ingresa"));
				usu.setFechaModifica(rs.getDate("fecha_modifica"));
				usu.setEstado(rs.getString("estado"));
				usu.setEmail(rs.getString("email"));
				usu.setPass(rs.getString("pass"));
				usu.setCodigoProvincia(rs.getString("pro_codigo"));
				usu.setCodigpCiudad(rs.getString("ciu_codigo"));
				usu.setProvincia(rs.getString("provincia"));
				usu.setCiudad(rs.getString("ciudad"));
				
				listUsuario.add(usu);
				
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("usuarios", listUsuario);
			
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
