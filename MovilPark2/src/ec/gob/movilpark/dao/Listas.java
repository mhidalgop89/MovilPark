package ec.gob.movilpark.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec.gob.movilpark.dto.Areas;
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.EspacioPorParquimetro;
import ec.gob.movilpark.dto.Firebase;
import ec.gob.movilpark.dto.MultaVehiculo;
import ec.gob.movilpark.dto.NotaCredito;
import ec.gob.movilpark.dto.NotaCreditoParam;
import ec.gob.movilpark.dto.Pago;
import ec.gob.movilpark.dto.Parametros;
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Perfil;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.PushMessage;
import ec.gob.movilpark.dto.Tramite;
import ec.gob.movilpark.dto.Usuario;
import ec.gob.movilpark.dto.Vehiculo;
import ec.gob.movilpark.util.ConexionUtil;
import jdk.nashorn.internal.ir.GetSplitState;

public class Listas extends ConexionUtil {
	//sp_cierra_parquimetros_uso
	
	public Map<String, Object> cierraParquimetrosEnUso(int idEspacio)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;

		try
		{ 
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_cierra_parquimetros_uso(?)}"
	            );
			
			oraCallStmt.setInt(1, idEspacio);
			
			
			oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));


//			mapResult.put("listTramite", listTramite);
			
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
	
	
	public Map<String, Object> consultaTramite()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Tramite>listTramite= new ArrayList<Tramite>();
		Tramite tramite;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_tramites_curdate()}"
	            );
			
			//oraCallStmt.setInt(1, idUsuario);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				tramite = new Tramite() ;
				tramite.setVehiculoId(rs.getInt("vehiculoID"));
				tramite.setIdParquimetro(rs.getInt("id_parquimetro"));
				tramite.setIdUsuario(rs.getInt("usuarioID"));
				tramite.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				tramite.setIdTipoTramite(rs.getInt("id_tipo_tramite"));
				tramite.setObservacion(rs.getString("observacion"));
				tramite.setEstado(rs.getString("estado"));
				
				tramite.setFechaInicial(rs.getTimestamp("fechaInicial"));
				tramite.setFechaFinal(rs.getTimestamp("fechaFinal"));
				
				tramite.setMinutos(rs.getInt("minutos"));
				tramite.setFechaTramite(rs.getTimestamp("fecha_tramite"));
				tramite.setUsuario(rs.getString("usuario"));
				
				listTramite.add(tramite);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listTramite", listTramite);
			
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
	
	
	
	public Map<String, Object> consultaTramiteXespacioParq(int idEspacio)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Tramite>listTramite= new ArrayList<Tramite>();
		Tramite tramite;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_tramite_x_id_espacio_parq(?)}"
	            );
			
			oraCallStmt.setInt(1, idEspacio);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				tramite = new Tramite() ;
				tramite.setVehiculoId(rs.getInt("vehiculoID"));
				tramite.setIdParquimetro(rs.getInt("id_parquimetro"));
				tramite.setIdUsuario(rs.getInt("usuarioID"));
				tramite.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				tramite.setIdTipoTramite(rs.getInt("id_tipo_tramite"));
				tramite.setObservacion(rs.getString("observacion"));
				tramite.setEstado(rs.getString("estado"));
				
				tramite.setFechaInicial(rs.getTimestamp("fechaInicial"));
				tramite.setFechaFinal(rs.getTimestamp("fechaFinal"));
				
				tramite.setMinutos(rs.getInt("minutos"));
				tramite.setFechaTramite(rs.getTimestamp("fecha_tramite"));
				tramite.setUsuario(rs.getString("usuario"));
				tramite.setNombre(rs.getString("nombre"));
				tramite.setApellido(rs.getString("apellido"));
				tramite.setMovil(rs.getString("tlf_movil"));
				
				
				listTramite.add(tramite);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listTramite", listTramite);
			
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
	
	
	
	
	
	public Map<String, Object> consultaExisteTramite(int idParquimetro, int idEspacio)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Tramite>listTramite= new ArrayList<Tramite>();
		Tramite tramite;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_existe_tramite(?,?)}"
	            );
			
			oraCallStmt.setInt(1, idParquimetro);
			oraCallStmt.setInt(2, idEspacio);
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				tramite = new Tramite() ;
				tramite.setVehiculoId(rs.getInt("vehiculoID"));
				tramite.setIdParquimetro(rs.getInt("id_parquimetro"));
				tramite.setIdUsuario(rs.getInt("usuarioID"));
				tramite.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				tramite.setIdTipoTramite(rs.getInt("id_tipo_tramite"));
				tramite.setObservacion(rs.getString("observacion"));
				tramite.setEstado(rs.getString("estado"));
				
				tramite.setFechaInicial(rs.getTimestamp("fechaInicial"));
				tramite.setFechaFinal(rs.getTimestamp("fechaFinal"));
				
				tramite.setMinutos(rs.getInt("minutos"));
				tramite.setFechaTramite(rs.getTimestamp("fecha_tramite"));
				
				
				listTramite.add(tramite);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listTramite", listTramite);
			
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
	
	
	
	
	public Map<String, Object> consultaTramiteXplacaXusuario(int idUsuario, String placa)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Tramite>listTramite= new ArrayList<Tramite>();
		Tramite tramite;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_tramite_x_placa_y_usuario(?,?)}"
	            );
			
			oraCallStmt.setInt(1, idUsuario);
			oraCallStmt.setString(2, placa);
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				tramite = new Tramite() ;
				tramite.setVehiculoId(rs.getInt("vehiculoID"));
				tramite.setIdParquimetro(rs.getInt("id_parquimetro"));
				tramite.setIdUsuario(rs.getInt("usuarioID"));
				tramite.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				tramite.setIdTipoTramite(rs.getInt("id_tipo_tramite"));
				tramite.setObservacion(rs.getString("observacion"));
				tramite.setEstado(rs.getString("estado"));
				
				tramite.setFechaInicial(rs.getTimestamp("fechaInicial"));
				tramite.setFechaFinal(rs.getTimestamp("fechaFinal"));
				
				tramite.setMinutos(rs.getInt("minutos"));
				tramite.setFechaTramite(rs.getTimestamp("fecha_tramite"));
				tramite.setIdAreas(rs.getInt("id_areas"));
				tramite.setPlaca(rs.getString("placa"));
				
				listTramite.add(tramite);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listTramite", listTramite);
			
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
	
	
	
	
	public Map<String, Object> consultaPlacaXusuario(int idUsuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Tramite>listTramite= new ArrayList<Tramite>();
		Tramite tramite;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_placa_x_usuario(?)}"
	            );
			
			oraCallStmt.setInt(1, idUsuario);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				tramite = new Tramite() ;
				tramite.setPlaca(rs.getString("placa"));
				tramite.setFechaFinal(rs.getTimestamp("fechaFinal"));
				
				listTramite.add(tramite);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listTramite", listTramite);
			
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
	
	
	
	public Map<String, Object> consultaTramiteXusuarioPlaca(int idUsuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Tramite>listTramite= new ArrayList<Tramite>();
		Tramite tramite;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_tramite_x_usuario(?)}"
	            );
			
			oraCallStmt.setInt(1, idUsuario);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				tramite = new Tramite() ;
				tramite.setVehiculoId(rs.getInt("vehiculoID"));
				tramite.setIdParquimetro(rs.getInt("id_parquimetro"));
				tramite.setIdUsuario(rs.getInt("usuarioID"));
				tramite.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				tramite.setIdTipoTramite(rs.getInt("id_tipo_tramite"));
				tramite.setObservacion(rs.getString("observacion"));
				tramite.setEstado(rs.getString("estado"));
				
				tramite.setFechaInicial(rs.getTimestamp("fechaInicial"));
				tramite.setFechaFinal(rs.getTimestamp("fechaFinal"));
				
				tramite.setMinutos(rs.getInt("minutos"));
				tramite.setFechaTramite(rs.getTimestamp("fecha_tramite"));
				tramite.setIdAreas(rs.getInt("id_areas"));
				tramite.setPlaca(rs.getString("placa"));
				
				listTramite.add(tramite);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listTramite", listTramite);
			
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
	
	
	public Map<String, Object> inactivaVehiculo(int idVehiculo)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Tramite>listTramite= new ArrayList<Tramite>();
		Tramite tramite;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inactiva_vehiculo(?)}"
	            );
			
			oraCallStmt.setInt(1, idVehiculo);
			
			
			oraCallStmt.execute();
			
		}catch(Exception e)
		{   mapResult.put("error", 999);
			mapResult.put("mensajeError", e.getMessage());
			e.printStackTrace();
		}finally
		{
			
			try {
				oraCallStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}
	
	public Map<String, Object> consultaTramiteXvehiculo(int idVehiculo)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Tramite>listTramite= new ArrayList<Tramite>();
		Tramite tramite;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_tramite_x_veh(?)}"
	            );
			
			oraCallStmt.setInt(1, idVehiculo);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				tramite = new Tramite() ;
				tramite.setVehiculoId(rs.getInt("vehiculoID"));
				tramite.setIdParquimetro(rs.getInt("id_parquimetro"));
				tramite.setIdUsuario(rs.getInt("usuarioID"));
				tramite.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				tramite.setIdTipoTramite(rs.getInt("id_tipo_tramite"));
				tramite.setObservacion(rs.getString("observacion"));
				tramite.setEstado(rs.getString("estado"));
				
				tramite.setFechaInicial(rs.getTimestamp("fechaInicial"));
				tramite.setFechaFinal(rs.getTimestamp("fechaFinal"));
				
				tramite.setMinutos(rs.getInt("minutos"));
				tramite.setFechaTramite(rs.getTimestamp("fecha_tramite"));
				tramite.setIdAreas(rs.getInt("id_areas"));
				
				
				listTramite.add(tramite);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listTramite", listTramite);
			
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
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}
	
	
	
	public Map<String, Object> consultaTramiteXusuario(int idUsuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Tramite>listTramite= new ArrayList<Tramite>();
		Tramite tramite;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_tramite(?)}"
	            );
			
			oraCallStmt.setInt(1, idUsuario);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				tramite = new Tramite() ;
				tramite.setVehiculoId(rs.getInt("vehiculoID"));
				tramite.setIdParquimetro(rs.getInt("id_parquimetro"));
				tramite.setIdUsuario(rs.getInt("usuarioID"));
				tramite.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				tramite.setIdTipoTramite(rs.getInt("id_tipo_tramite"));
				tramite.setObservacion(rs.getString("observacion"));
				tramite.setEstado(rs.getString("estado"));
				
				tramite.setFechaInicial(rs.getTimestamp("fechaInicial"));
				tramite.setFechaFinal(rs.getTimestamp("fechaFinal"));
				
				tramite.setMinutos(rs.getInt("minutos"));
				tramite.setFechaTramite(rs.getTimestamp("fecha_tramite"));
				tramite.setIdAreas(rs.getInt("id_areas"));
				
				
				listTramite.add(tramite);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listTramite", listTramite);
			
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
	
	
	public Map<String, Object> consultaFirebase  (int codCia, String codProdFirebase)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Firebase>listaFirebase= new ArrayList<Firebase>();
		Firebase objFirebase ;
		ResultSet rs;
		String error;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_firebase(?,?,?)}"
	            );
			oraCallStmt.setInt(1, codCia);
			oraCallStmt.setString(2, codProdFirebase);
			oraCallStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			
			
			rs=oraCallStmt.executeQuery();
			error = oraCallStmt.getString(3);
			if(error!=null){
				mapResult.put("error", 998);
				mapResult.put("mensajeError", error);
			}
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				objFirebase  = new Firebase();
				objFirebase.setCodigoCompania(rs.getInt("codigo_compania"));
				objFirebase.setCfirebase(rs.getInt("cfirebase"));
				objFirebase.setCproductoFirebase(rs.getString("cproducto_firebase"));
				objFirebase.setProductoFirebase(rs.getString("producto_firebase"));
				objFirebase.setRutaClavePrivada(rs.getString("ruta_clave_privada"));
				objFirebase.setObservacion(rs.getString("observacion"));
				objFirebase.setFdesde(rs.getDate("fdesde"));
				objFirebase.setFhasta(rs.getDate("fhasta"));
				
				listaFirebase.add(objFirebase);
				
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("firebase", listaFirebase);
			
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
	
	
	public Map<String, Object> ingresaFirebase(Firebase firebase)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		String error;
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_datos_firebasae(?,?,?,?,?,?,?)}"
	            );
			
			oraCallStmt.setInt(1, firebase.getCodigoCompania());
			oraCallStmt.setInt(2, firebase.getCfirebase());
			oraCallStmt.setString(3, firebase.getCproductoFirebase());
			
			oraCallStmt.setString(4, firebase.getProductoFirebase());
			oraCallStmt.setString(5, firebase.getRutaClavePrivada());
			oraCallStmt.setString(6, firebase.getObservacion());
			
			oraCallStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			
			oraCallStmt.execute();
			error = oraCallStmt.getString(7);
			if(error!=null){
				mapResult.put("error", 998);
				mapResult.put("mensajeError", error);
			}
				
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
	
	public Map<String, Object> ingresaPushMessage(PushMessage push)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		String error;
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_datos_push(?,?,?,?,?,?,?,?,?,?,?,?)}"
	            );
			
			oraCallStmt.setInt(1, push.getCodigoCompania());
			oraCallStmt.setString(2, push.getRequest());
			oraCallStmt.setString(3, push.getResponse());
						
			oraCallStmt.setString(4, push.getMensaje());
			oraCallStmt.setString(5, push.getCuerpo());
			oraCallStmt.setString(6, push.getMetodo());
			oraCallStmt.setString(7, push.getToken());
			oraCallStmt.setString(8, push.getEstado());
			oraCallStmt.setString(9, push.getRutaClavePrivada());
			oraCallStmt.setString(10, push.getUsuarioIngresa());
			oraCallStmt.setString(11, push.getUsuarioActualiza());
			
			oraCallStmt.registerOutParameter(12, java.sql.Types.VARCHAR);
			
			
			oraCallStmt.execute();
			error = oraCallStmt.getString(12);
			if(error!=null){
				mapResult.put("error", 998);
				mapResult.put("mensajeError", error);
			}
				
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
	
	
	public Map<String, Object> realizaTransaccionPago(int idUsuario, Double valorPagar,String usuarioActualiza,Tramite objTramite, Pago pago)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		String error;
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_realiza_transaccion_pago_nc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
	            );
			
			oraCallStmt.setInt(1, idUsuario);
			oraCallStmt.setDouble(2, valorPagar);
			oraCallStmt.setString(3, usuarioActualiza);
			
			
			
			oraCallStmt.setInt(4, objTramite.getVehiculoId());
			oraCallStmt.setInt(5, objTramite.getIdParquimetro());
			oraCallStmt.setInt(6, objTramite.getIdUsuario());
			
			oraCallStmt.setInt(7, objTramite.getIdEspacioPorParquimetro());
//			oraCallStmt.setDate(8, new java.sql.Date(objTramite.getFechaInicial().getTime()));
			oraCallStmt.setTimestamp(8,  new java.sql.Timestamp(objTramite.getFechaInicial().getTime())); 
			oraCallStmt.setTimestamp(9, new java.sql.Timestamp(objTramite.getFechaFinal().getTime()));
			oraCallStmt.setInt(10, objTramite.getMinutos());
			oraCallStmt.setTimestamp(11, new java.sql.Timestamp(objTramite.getFechaTramite().getTime()));
			//oraCallStmt.setString(9, objParquimetro.getEstado());
			oraCallStmt.setString(12, objTramite.getUsuarioIngresa());
			oraCallStmt.setTimestamp(13, new java.sql.Timestamp(objTramite.getFechaIngresa().getTime()));
			
			oraCallStmt.setDouble(14, pago.getValorTotal());
			oraCallStmt.setDouble(15, pago.getValorPagado());
			oraCallStmt.setTimestamp(16,  new java.sql.Timestamp(pago.getFechaPago().getTime()));
			oraCallStmt.setInt(17,  objTramite.getIdAreas());
			oraCallStmt.registerOutParameter(18, java.sql.Types.VARCHAR);
			
			oraCallStmt.execute();
			error = oraCallStmt.getString(18);
			if(error!=null){
				mapResult.put("error", 998);
				mapResult.put("mensajeError", error);
			}
				
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
	
	
	
	public Map<String, Object> editaNotaCredito(NotaCredito objNotaCredito)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		
		

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_edita_nota_credito(?,?,?,?,?,?,?,?,?,?)}"
	            );
			
			oraCallStmt.setInt(1, objNotaCredito.getIdNotaCredito());
			oraCallStmt.setDouble(2, objNotaCredito.getValor());
			oraCallStmt.setDouble(3, objNotaCredito.getDescuento());
			oraCallStmt.setDouble(4, objNotaCredito.getSaldo());
			oraCallStmt.setString(5, objNotaCredito.getEstado());
			oraCallStmt.setInt(6, objNotaCredito.getUsuarioId());
			oraCallStmt.setString(7, objNotaCredito.getObservacion());
			
			oraCallStmt.setString(8, objNotaCredito.getUsuarioActualiza());
			oraCallStmt.setDate(9, new java.sql.Date(objNotaCredito.getFechaActualiza().getTime()));
			oraCallStmt.setString(10, objNotaCredito.getNumeroFactura());
			
			oraCallStmt.execute();

			
		}catch(Exception e)
		{   mapResult.put("error", 999);
			mapResult.put("mensajeError", e.getMessage());
			e.printStackTrace();
		}finally
		{
			
			try {
				oraCallStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}
	
	
	public Map<String, Object> inactivaMultaVeh(MultaVehiculo objMultaVeh)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		
		

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inactiva_multa(?,?,?)}"
	            );
			
			oraCallStmt.setInt(1, objMultaVeh.getIdMultaVehiculo());
			oraCallStmt.setTimestamp(2, new java.sql.Timestamp( objMultaVeh.getFechaActualiza().getTime()));
			oraCallStmt.setString(3, objMultaVeh.getUsuarioActualiza());
			
			// oraCallStmt.setTimestamp(7, new java.sql.Timestamp(objNotaCredito.getFechaIngresa().getTime()));
			oraCallStmt.execute();

			
		}catch(Exception e)
		{   mapResult.put("error", 999);
			mapResult.put("mensajeError", e.getMessage());
			e.printStackTrace();
		}finally
		{
			
			try {
				oraCallStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}
	
	
	
	
	public Map<String, Object> insertaMultaVeh(MultaVehiculo objMultaVeh)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		
		

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_multa_vehiculos(?,?,?,?)}"
	            );
			
			oraCallStmt.setInt(1, objMultaVeh.getVehiculoId());
			oraCallStmt.setString(2, objMultaVeh.getEstado());
			oraCallStmt.setTimestamp(3, new java.sql.Timestamp( objMultaVeh.getFechaIngresa().getTime()));
			oraCallStmt.setString(4, objMultaVeh.getUsuarioIngresa());
			
			// oraCallStmt.setTimestamp(7, new java.sql.Timestamp(objNotaCredito.getFechaIngresa().getTime()));
			oraCallStmt.execute();

			
		}catch(Exception e)
		{   mapResult.put("error", 999);
			mapResult.put("mensajeError", e.getMessage());
			e.printStackTrace();
		}finally
		{
			
			try {
				oraCallStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}
	
	
	public Map<String, Object> insertaNotaCredito(NotaCredito objNotaCredito)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		
		

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_nota_credito(?,?,?,?,?,?,?,?,?)}"
	            );
			
			oraCallStmt.setDouble(1, objNotaCredito.getValor());
			oraCallStmt.setDouble(2, objNotaCredito.getDescuento());
			oraCallStmt.setDouble(3, objNotaCredito.getSaldo());
			oraCallStmt.setInt(4, objNotaCredito.getUsuarioId());
			oraCallStmt.setString(5, objNotaCredito.getObservacion());
			oraCallStmt.setString(6, objNotaCredito.getUsuarioIngresa());
			oraCallStmt.setTimestamp(7, new java.sql.Timestamp(objNotaCredito.getFechaIngresa().getTime()));
			oraCallStmt.setString(8, objNotaCredito.getNumeroFactura());
			oraCallStmt.setTimestamp(9, new java.sql.Timestamp(objNotaCredito.getFhasta().getTime()));
			
			oraCallStmt.execute();

			
		}catch(Exception e)
		{   mapResult.put("error", 999);
			mapResult.put("mensajeError", e.getMessage());
			e.printStackTrace();
		}finally
		{
			
			try {
				oraCallStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}

	public Map<String, Object> editaNotasCreditosParametros(NotaCreditoParam objNotaCredito)
	{		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_edita_nc_param(?,?,?,?)}"
	            );
			
			oraCallStmt.setInt(1, objNotaCredito.getIdNcParam());
			oraCallStmt.setInt(2, objNotaCredito.getTiempoMaximoVigencia());
			oraCallStmt.setDouble(3, objNotaCredito.getValorMinimo());
			oraCallStmt.setDouble(4, objNotaCredito.getValormaximo());
			
			oraCallStmt.execute();

			
		}catch(Exception e)
		{   mapResult.put("error", 999);
			mapResult.put("mensajeError", e.getMessage());
			e.printStackTrace();
		}finally
		{
			
			try {
				oraCallStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			try {
				if (conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		return mapResult; 
		
	}
	
	
	public Map<String, Object> consultaNotasCreditosParametros()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<NotaCreditoParam>listaNotaCreditoParam= new ArrayList<NotaCreditoParam>();
		NotaCreditoParam notaCreditoParam;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_nc_param()}"
	            );
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				notaCreditoParam = new NotaCreditoParam() ;
				notaCreditoParam.setIdNcParam(rs.getInt("id_nc_param"));
				notaCreditoParam.setTiempoMaximoVigencia(rs.getInt("tiempo_max_vigencia"));
				notaCreditoParam.setValorMinimo(rs.getDouble("valor_minimo"));
				notaCreditoParam.setValormaximo(rs.getDouble("valor_maximo"));
				notaCreditoParam.setEstado(rs.getString("estado"));
				listaNotaCreditoParam.add(notaCreditoParam);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaNotaCreditoParam", listaNotaCreditoParam);
			
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

	public Map<String, Object> consultaNotasCreditosParam(int idUsuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<NotaCredito>listaNotaCredito= new ArrayList<NotaCredito>();
		NotaCredito notaCredito;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_nota_credito_x_persona(?)}"
	            );
			
			oraCallStmt.setInt(1, idUsuario);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				notaCredito = new NotaCredito() ;
				notaCredito.setIdNotaCredito(rs.getInt("id_nota_credito"));
				notaCredito.setValor(rs.getDouble("valor"));
				notaCredito.setDescuento(rs.getDouble("descuento"));
				notaCredito.setSaldo(rs.getDouble("saldo"));
				notaCredito.setEstado(rs.getString("estado"));
				notaCredito.setUsuarioId(rs.getInt("usuarioID"));
				notaCredito.setObservacion(rs.getString("observacion"));
				notaCredito.setUsuarioIngresa(rs.getString("usuario_ingresa"));
				notaCredito.setFechaIngresa(rs.getDate("fecha_ingreso"));
				notaCredito.setUsuarioActualiza(rs.getString("usuario_actualiza"));
				notaCredito.setFechaActualiza(rs.getDate("fecha_actualiza"));
				
				listaNotaCredito.add(notaCredito);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaNotaCredito", listaNotaCredito);
			
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
	
	public Map<String, Object> consultaNotasCreditosUsadasDuranteDia()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<NotaCredito>listaNotaCredito= new ArrayList<NotaCredito>();
		NotaCredito notaCredito;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_nota_credito_usadas_dia()}"
	            );
			
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				notaCredito = new NotaCredito() ;
				notaCredito.setIdNotaCredito(rs.getInt("id_nota_credito"));
				notaCredito.setValor(rs.getDouble("valor"));
				notaCredito.setDescuento(rs.getDouble("descuento"));
				notaCredito.setSaldo(rs.getDouble("saldo"));
				notaCredito.setEstado(rs.getString("estado"));
				notaCredito.setUsuarioId(rs.getInt("usuarioID"));
				notaCredito.setObservacion(rs.getString("observacion"));
				notaCredito.setUsuarioIngresa(rs.getString("usuario_ingresa"));
				notaCredito.setFechaIngresa(rs.getDate("fecha_ingreso"));
				notaCredito.setUsuarioActualiza(rs.getString("usuario_actualiza"));
				notaCredito.setFechaActualiza(rs.getDate("fecha_actualiza"));
				notaCredito.setNumeroFactura(rs.getString("numero_factura"));
				notaCredito.setFhasta(rs.getTimestamp("fhasta"));
				
				listaNotaCredito.add(notaCredito);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaNotaCredito", listaNotaCredito);
			
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
	
	public Map<String, Object> consultaNotasCreditosCompDuranteDia()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<NotaCredito>listaNotaCredito= new ArrayList<NotaCredito>();
		NotaCredito notaCredito;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_nota_credito_compradas_dia()}"
	            );
			
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				notaCredito = new NotaCredito() ;
				notaCredito.setIdNotaCredito(rs.getInt("id_nota_credito"));
				notaCredito.setValor(rs.getDouble("valor"));
				notaCredito.setDescuento(rs.getDouble("descuento"));
				notaCredito.setSaldo(rs.getDouble("saldo"));
				notaCredito.setEstado(rs.getString("estado"));
				notaCredito.setUsuarioId(rs.getInt("usuarioID"));
				notaCredito.setObservacion(rs.getString("observacion"));
				notaCredito.setUsuarioIngresa(rs.getString("usuario_ingresa"));
				notaCredito.setFechaIngresa(rs.getDate("fecha_ingreso"));
				notaCredito.setUsuarioActualiza(rs.getString("usuario_actualiza"));
				notaCredito.setFechaActualiza(rs.getDate("fecha_actualiza"));
				notaCredito.setNumeroFactura(rs.getString("numero_factura"));
				notaCredito.setFhasta(rs.getTimestamp("fhasta"));
				
				listaNotaCredito.add(notaCredito);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaNotaCredito", listaNotaCredito);
			
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
	
	
	
	
	public Map<String, Object> consultaNotasCreditos()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<NotaCredito>listaNotaCredito= new ArrayList<NotaCredito>();
		NotaCredito notaCredito;// = new NotaCredito() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_nota_credito()}"
	            );
			
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				notaCredito = new NotaCredito() ;
				notaCredito.setIdNotaCredito(rs.getInt("id_nota_credito"));
				notaCredito.setValor(rs.getDouble("valor"));
				notaCredito.setDescuento(rs.getDouble("descuento"));
				notaCredito.setSaldo(rs.getDouble("saldo"));
				notaCredito.setEstado(rs.getString("estado"));
				notaCredito.setUsuarioId(rs.getInt("usuarioID"));
				notaCredito.setObservacion(rs.getString("observacion"));
				notaCredito.setUsuarioIngresa(rs.getString("usuario_ingresa"));
				notaCredito.setFechaIngresa(rs.getDate("fecha_ingreso"));
				notaCredito.setUsuarioActualiza(rs.getString("usuario_actualiza"));
				notaCredito.setFechaActualiza(rs.getDate("fecha_actualiza"));
				notaCredito.setNumeroFactura(rs.getString("numero_factura"));
				notaCredito.setFhasta(rs.getTimestamp("fhasta"));
				
				listaNotaCredito.add(notaCredito);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaNotaCredito", listaNotaCredito);
			
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
	
	
	public Map<String, Object> obtieneParametros  ()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Parametros>listaPar= new ArrayList<Parametros>();
		Parametros par = new Parametros() ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_parametros()}"
	            );
			
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				
				par.setParametrosID(rs.getInt(1));
				par.setValorPorMinuto(rs.getDouble(2));
				par.setTiempoMaxEnMinutos(rs.getInt(3));
				par.setMaximoDolares(rs.getDouble(4));
				par.setValorMinimoDolares(rs.getDouble(5));
				par.setEstado(rs.getString(6));
				par.setUsuarioIngresa(rs.getString(7));
				par.setFechaIngresa(rs.getDate(8));
				par.setUsuarioModifica(rs.getString(9));
				par.setFechaModifica(rs.getDate(10));
				
				listaPar.add(par);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("parametro", par);
			
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
	
	
	
	public Map<String, Object> editaParquimetro(Parquimetro objParquimetro)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Parquimetro>listaParquimetro= new ArrayList<Parquimetro>();
		Parquimetro parq ;
		

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_edita_parquimetro(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
	            );
			System.out.println("objParquimetro.getIdParquimetro(): "+objParquimetro.getIdParquimetro());
			oraCallStmt.setInt(1, objParquimetro.getIdParquimetro());
			oraCallStmt.setString(2, objParquimetro.getProvinciaCodigo());
			oraCallStmt.setString(3, objParquimetro.getCiudadCodigo());
			oraCallStmt.setInt(4, objParquimetro.getIdAreas());
			oraCallStmt.setString(5, objParquimetro.getCalle());
			oraCallStmt.setString(6, objParquimetro.getPoste());
			oraCallStmt.setInt(7, objParquimetro.getNumeroEspacios());
			oraCallStmt.setDouble(8, objParquimetro.getLatitud());
			oraCallStmt.setDouble(9, objParquimetro.getLongitud());
			oraCallStmt.setString(10, objParquimetro.getEstado());
			oraCallStmt.setString(11, objParquimetro.getNombre());
			oraCallStmt.setDouble(12, objParquimetro.getValorMinimo());
			oraCallStmt.setDouble(13, objParquimetro.getValorMaximo());
			oraCallStmt.setDouble(14, objParquimetro.getValorPorHora());
			
			oraCallStmt.execute();

			
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
	
	
	public Map<String, Object> insertaTramite(Tramite objTramite)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_tramite(?,?,?,?,?,?,?,?,?,?)}"
	            );
			
			oraCallStmt.setInt(1, objTramite.getVehiculoId());
			oraCallStmt.setInt(2, objTramite.getIdParquimetro());
			oraCallStmt.setInt(3, objTramite.getIdUsuario());
			
			oraCallStmt.setInt(4, objTramite.getIdEspacioPorParquimetro());
			oraCallStmt.setDate(5, new java.sql.Date(objTramite.getFechaInicial().getTime()));
			oraCallStmt.setDate(6, new java.sql.Date(objTramite.getFechaFinal().getTime()));
			oraCallStmt.setInt(7, objTramite.getMinutos());
			oraCallStmt.setDate(8, new java.sql.Date(objTramite.getFechaTramite().getTime()));
			//oraCallStmt.setString(9, objParquimetro.getEstado());
			oraCallStmt.setString(9, objTramite.getUsuarioIngresa());
			oraCallStmt.setDate(10, new java.sql.Date(objTramite.getFechaIngresa().getTime()));
			
			
			
			oraCallStmt.execute();

			
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
	
	
	
	
	public Map<String, Object> editaAreas(Areas objAreas)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Parquimetro>listaParquimetro= new ArrayList<Parquimetro>();
		Parquimetro parq ;
		

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_edita_areas(?,?,?,?,?,?,?,?,?)}"
	            );
			
			oraCallStmt.setString(1, objAreas.getCodigoProvincia());
			oraCallStmt.setString(2, objAreas.getCodigoCiudad());
			oraCallStmt.setString(3, objAreas.getNombre());
			oraCallStmt.setInt(4, objAreas.getIdAreas());
			oraCallStmt.setString(5, objAreas.getEstado());
			
			oraCallStmt.setDouble(6, objAreas.getValorMinimo());
			oraCallStmt.setDouble(7, objAreas.getValorMaximo());
			oraCallStmt.setDouble(8, objAreas.getValorPorHora());
			
			oraCallStmt.setString(9, objAreas.getRutaImagen());
			
			oraCallStmt.execute();

			
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
	
	
	
	
	public Map<String, Object> insertaAreas(Areas objAreas)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Parquimetro>listaParquimetro= new ArrayList<Parquimetro>();
		Parquimetro parq ;
		

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_areas(?,?,?,?,?,?)}"
	            );
			
			oraCallStmt.setString(1, objAreas.getCodigoProvincia());
			oraCallStmt.setString(2, objAreas.getCodigoCiudad());
			oraCallStmt.setString(3, objAreas.getNombre());
			
			oraCallStmt.setDouble(4, objAreas.getValorMinimo());
			oraCallStmt.setDouble(5, objAreas.getValorMaximo());
			oraCallStmt.setDouble(6, objAreas.getValorPorHora());
		
			
			
			oraCallStmt.execute();

			
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
	
	
	
	
	public Map<String, Object> insertaParquimetro(Parquimetro objParquimetro)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Parquimetro>listaParquimetro= new ArrayList<Parquimetro>();
		Parquimetro parq ;
		

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_inserta_parquimetro(?,?,?,?,?,?,?,?,?,?,?,?)}"
	            );
			
			oraCallStmt.setString(1, objParquimetro.getProvinciaCodigo());
			oraCallStmt.setString(2, objParquimetro.getCiudadCodigo());
			oraCallStmt.setInt(3, objParquimetro.getIdAreas());
			oraCallStmt.setString(4, objParquimetro.getCalle());
			oraCallStmt.setString(5, objParquimetro.getPoste());
			oraCallStmt.setInt(6, objParquimetro.getNumeroEspacios());
			oraCallStmt.setDouble(7, objParquimetro.getLatitud());
			oraCallStmt.setDouble(8, objParquimetro.getLongitud());
			//oraCallStmt.setString(9, objParquimetro.getEstado());
			oraCallStmt.setString(9, objParquimetro.getNombre());
			oraCallStmt.setDouble(10, objParquimetro.getValorMinimo());
			oraCallStmt.setDouble(11, objParquimetro.getValorMaximo());
			oraCallStmt.setDouble(12, objParquimetro.getValorPorHora());
			
			
			oraCallStmt.execute();

			
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
	
	
	
	
	public Map<String, Object> consultaParquimetroParam(String prov, String ciudad, int area)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Parquimetro>listaParquimetro= new ArrayList<Parquimetro>();
		Parquimetro parq ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_parquimetro_param(?,?,?)}"
	            );
			
			oraCallStmt.setString(1, prov);
			oraCallStmt.setString(2, ciudad);
			oraCallStmt.setInt(3, area);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				parq = new Parquimetro();
				parq.setIdParquimetro(rs.getInt("id_parquimetro"));
				parq.setProvinciaCodigo(rs.getString("pro_codigo"));
				parq.setCiudadCodigo(rs.getString("ciu_codigo"));
				parq.setIdAreas(rs.getInt("id_areas"));
				parq.setCalle(rs.getString("calle"));
				parq.setPoste(rs.getString("poste"));
				parq.setNumeroEspacios(rs.getInt("num_espacio"));
				parq.setLatitud(rs.getDouble("lat"));
				parq.setLongitud(rs.getDouble("lng"));
				parq.setEstado(rs.getString("estado"));
				parq.setNombre(rs.getString("nombre"));
				parq.setValorMinimo(rs.getDouble("valor_minimo"));
				parq.setValorMaximo(rs.getDouble("valor_maximo"));
				parq.setValorPorHora(rs.getDouble("valor_x_hora"));
				
				listaParquimetro.add(parq);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaParquimetro", listaParquimetro);
			
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
	
	
	
	
	
	public Map<String, Object> consultaParquimetro()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Parquimetro>listaParquimetro= new ArrayList<Parquimetro>();
		Parquimetro parq ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_parquimetro()}"
	            );
			
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				parq = new Parquimetro();
				parq.setIdParquimetro(rs.getInt("id_parquimetro"));
				parq.setProvinciaCodigo(rs.getString("pro_codigo"));
				parq.setCiudadCodigo(rs.getString("ciu_codigo"));
				parq.setIdAreas(rs.getInt("id_areas"));
				parq.setCalle(rs.getString("calle"));
				parq.setPoste(rs.getString("poste"));
				parq.setNumeroEspacios(rs.getInt("num_espacio"));
				parq.setLatitud(rs.getDouble("lat"));
				parq.setLongitud(rs.getDouble("lng"));
				parq.setEstado(rs.getString("estado"));
				parq.setNombre(rs.getString("nombre"));
				parq.setValorMinimo(rs.getDouble("valor_minimo"));
				parq.setValorMaximo(rs.getDouble("valor_maximo"));
				parq.setValorPorHora(rs.getDouble("valor_x_hora"));
				
				listaParquimetro.add(parq);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaParquimetro", listaParquimetro);
			
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
	
	
	
	public Map<String, Object> consultaPagoPorFecha(java.util.Date finicio, java.util.Date ffin,int idArea, String placa)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Pago>listaPago= new ArrayList<Pago>();
		Pago pago ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_nc_rango_fecha(?,?,?,?)}"
	            );	
			System.out.println("finicial: "+new java.sql.Timestamp(finicio.getTime()));
			oraCallStmt.setTimestamp(1, new java.sql.Timestamp(finicio.getTime()));
			oraCallStmt.setTimestamp(2, new java.sql.Timestamp(ffin.getTime()));
			oraCallStmt.setInt(3, idArea);
			oraCallStmt.setString(4, placa);
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				pago = new Pago();
				pago.setIdPago(rs.getInt("id_pago"));
				pago.setIdTramite(rs.getInt("tramiteID"));
				pago.setValorTotal(rs.getDouble("valor_total"));
				pago.setValorPagado(rs.getDouble("valor_pagado"));
				pago.setSaldo(rs.getDouble("saldo"));
				pago.setFechaPago(rs.getTimestamp("fecha_pago"));//rs.getTimestamp("fechaFinal")
				pago.setMinutos(rs.getInt("minutos"));
				pago.setAreas(rs.getString("areas"));
				pago.setCiudad(rs.getString("ciudad"));
				pago.setPlaca(rs.getString("placa"));
				listaPago.add(pago);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaPago", listaPago);
			
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

	
	public Map<String, Object> consultaParquimetroPorPlaca(String placa)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<EspacioPorParquimetro>listaEspacio= new ArrayList<EspacioPorParquimetro>();
		EspacioPorParquimetro espacio ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_parquimetro_x_placa(?)}"
	            );	
			oraCallStmt.setString(1, placa);
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				espacio = new EspacioPorParquimetro();
				//espacio.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				//espacio.setIdParquimetro(rs.getInt("id_parquimetro"));
				//espacio.setNumeroEspacio(rs.getInt("numero_espacio"));
				//espacio.setEnUso(rs.getString("en_uso"));
				//espacio.setEstado(rs.getString("estado"));
				espacio.setCodigoProvincia(rs.getString("pro_codigo"));
				espacio.setProvincia(rs.getString("provincia"));
				espacio.setCodigoCiudad(rs.getString("ciu_codigo"));
				espacio.setCiudad(rs.getString("ciudad"));
				espacio.setIdAreas(rs.getInt("id_areas"));
				espacio.setAreas(rs.getString("areas"));
				//espacio.setNombreParquimetro(rs.getString("parquimetro"));
				espacio.setFechaFinal(rs.getTimestamp("fechaFinal"));
				espacio.setPlaca(rs.getString("placa"));
				listaEspacio.add(espacio);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaEspacio", listaEspacio);
			
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
	
	
	public Map<String, Object> consultaEspacioPorParquimetroEnUsoXarea(int idArea)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<EspacioPorParquimetro>listaEspacio= new ArrayList<EspacioPorParquimetro>();
		EspacioPorParquimetro espacio ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_espacio_x_parquimetro_en_uso_x_area(?)}"
	            );		
			
			oraCallStmt.setInt(1, idArea);
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				espacio = new EspacioPorParquimetro();
				//espacio.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				//espacio.setIdParquimetro(rs.getInt("id_parquimetro"));
				//espacio.setNumeroEspacio(rs.getInt("numero_espacio"));
				//espacio.setEnUso(rs.getString("en_uso"));
				//espacio.setEstado(rs.getString("estado"));
				espacio.setCodigoProvincia(rs.getString("pro_codigo"));
				espacio.setProvincia(rs.getString("provincia"));
				espacio.setCodigoCiudad(rs.getString("ciu_codigo"));
				espacio.setCiudad(rs.getString("ciudad"));
				espacio.setIdAreas(rs.getInt("id_areas"));
				espacio.setAreas(rs.getString("areas"));
				//espacio.setNombreParquimetro(rs.getString("parquimetro"));
				espacio.setFechaFinal(rs.getTimestamp("fechaFinal"));
				espacio.setPlaca(rs.getString("placa"));
				espacio.setIdMultaVehiculo(rs.getInt("id_multa_vehiculo"));
				espacio.setVehiculoId(rs.getInt("vehiculoID"));
				listaEspacio.add(espacio);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaEspacio", listaEspacio);
			
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
	
	
	
	
	public Map<String, Object> consultaEspacioPorParquimetroEnUsoPorUsuario(int idUsuario)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<EspacioPorParquimetro>listaEspacio= new ArrayList<EspacioPorParquimetro>();
		EspacioPorParquimetro espacio ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_espacio_x_parquimetro_en_uso_x_usuario(?)}"
	            );		
			
			oraCallStmt.setInt(1, idUsuario);
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				espacio = new EspacioPorParquimetro();
				//espacio.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				//espacio.setIdParquimetro(rs.getInt("id_parquimetro"));
				//espacio.setNumeroEspacio(rs.getInt("numero_espacio"));
				//espacio.setEnUso(rs.getString("en_uso"));
				//espacio.setEstado(rs.getString("estado"));
				espacio.setCodigoProvincia(rs.getString("pro_codigo"));
				espacio.setProvincia(rs.getString("provincia"));
				espacio.setCodigoCiudad(rs.getString("ciu_codigo"));
				espacio.setCiudad(rs.getString("ciudad"));
				espacio.setIdAreas(rs.getInt("id_areas"));
				espacio.setAreas(rs.getString("areas"));
				//espacio.setNombreParquimetro(rs.getString("parquimetro"));
				espacio.setFechaFinal(rs.getTimestamp("fechaFinal"));
				espacio.setPlaca(rs.getString("placa"));
				espacio.setIdMultaVehiculo(rs.getInt("id_multa_vehiculo"));
				espacio.setVehiculoId(rs.getInt("vehiculoID"));
				listaEspacio.add(espacio);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaEspacio", listaEspacio);
			
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
	
	
	
	
	
	
	
	
	public Map<String, Object> consultaEspacioPorParquimetroEnUso()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<EspacioPorParquimetro>listaEspacio= new ArrayList<EspacioPorParquimetro>();
		EspacioPorParquimetro espacio ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_espacio_x_parquimetro_en_uso()}"
	            );		
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				espacio = new EspacioPorParquimetro();
				//espacio.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				//espacio.setIdParquimetro(rs.getInt("id_parquimetro"));
				//espacio.setNumeroEspacio(rs.getInt("numero_espacio"));
				//espacio.setEnUso(rs.getString("en_uso"));
				//espacio.setEstado(rs.getString("estado"));
				espacio.setCodigoProvincia(rs.getString("pro_codigo"));
				espacio.setProvincia(rs.getString("provincia"));
				espacio.setCodigoCiudad(rs.getString("ciu_codigo"));
				espacio.setCiudad(rs.getString("ciudad"));
				espacio.setIdAreas(rs.getInt("id_areas"));
				espacio.setAreas(rs.getString("areas"));
				//espacio.setNombreParquimetro(rs.getString("parquimetro"));
				espacio.setFechaFinal(rs.getTimestamp("fechaFinal"));
				espacio.setPlaca(rs.getString("placa"));
				espacio.setIdMultaVehiculo(rs.getInt("id_multa_vehiculo"));
				espacio.setVehiculoId(rs.getInt("vehiculoID"));
				listaEspacio.add(espacio);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaEspacio", listaEspacio);
			
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
	
	
	
	
	
	
	
	public Map<String, Object> consultaEspacioPorParquimetro(int idParquimetro)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<EspacioPorParquimetro>listaEspacio= new ArrayList<EspacioPorParquimetro>();
		EspacioPorParquimetro espacio ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_espacio_x_parquimetro(?)}"
	            );
			oraCallStmt.setInt(1, idParquimetro);
			
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				espacio = new EspacioPorParquimetro();
				espacio.setIdEspacioPorParquimetro(rs.getInt("id_espacio_x_parquimetro"));
				espacio.setIdParquimetro(rs.getInt("id_parquimetro"));
				espacio.setNumeroEspacio(rs.getInt("numero_espacio"));
				espacio.setEnUso(rs.getString("en_uso"));
				espacio.setEstado(rs.getString("estado"));
				espacio.setEspacio(rs.getString("espacio"));
				listaEspacio.add(espacio);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("listaEspacio", listaEspacio);
			
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
	
	
	public Map<String, Object> obtieneVehiculosMulta(int usuarioId, int vehiculoId)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Vehiculo>listaVeh= new ArrayList<Vehiculo>();
		Vehiculo veh ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_vehiculos_multa(?,?)}"
	            );
			oraCallStmt.setInt(1, vehiculoId);
			oraCallStmt.setInt(2, usuarioId);
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				veh = new Vehiculo();
				veh.setVehiculoId(rs.getInt("vehiculoID"));
				veh.setUsuarioId(rs.getInt("usuarioID"));
				veh.setEstado(rs.getString("estado"));
				veh.setPlaca(rs.getString("placa"));
				listaVeh.add(veh);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("vehiculo", listaVeh);
			
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
	
	public Map<String, Object> obtieneVehiculos(int usuarioId, int vehiculoId)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Vehiculo>listaVeh= new ArrayList<Vehiculo>();
		Vehiculo veh ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_vehiculos(?,?)}"
	            );
			oraCallStmt.setInt(1, vehiculoId);
			oraCallStmt.setInt(2, usuarioId);
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				veh = new Vehiculo();
				veh.setVehiculoId(rs.getInt("vehiculoID"));
				veh.setUsuarioId(rs.getInt("usuarioID"));
				veh.setEstado(rs.getString("estado"));
				veh.setPlaca(rs.getString("placa"));
				listaVeh.add(veh);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("vehiculo", listaVeh);
			
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

	
	public Map<String, Object> consultaPerfiles  ()
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Perfil>listaPerf= new ArrayList<Perfil>();
		Perfil perf ;
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
				perf = new Perfil();
				perf.setIdPerfil(rs.getInt("per_id"));
				perf.setNombre(rs.getString("nombre"));
				perf.setEstado(rs.getString("estado"));
				
				listaPerf.add(perf);
				
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("perfil", listaPerf);
			
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
	
	
	public Map<String, Object> obtieneProvincias  (int codProv)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Provincia>listaProv= new ArrayList<Provincia>();
		Provincia prov ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_provincia(?)}"
	            );
			oraCallStmt.setInt(1, codProv);
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				prov = new Provincia();
				prov.setProCodigo(rs.getString("pro_codigo"));
				prov.setNombre(rs.getString("nombre"));
				prov.setEstado(rs.getString("estado"));
				listaProv.add(prov);
				
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("provincia", listaProv);
			
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

	
	public Map<String, Object> obtieneCiudadesParquimetro(String codCiu, String codProv)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Ciudad>listaCiudad= new ArrayList<Ciudad>();
		Ciudad ciudad ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_ciudades_parquimetros(?,?)}"
	            );
			oraCallStmt.setString(1, codCiu);
			oraCallStmt.setString(2, codProv);
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				ciudad = new Ciudad();
				ciudad.setProCodigo(rs.getString("pro_codigo"));
				ciudad.setCiuCodigo(rs.getString("ciu_codigo"));
				ciudad.setNombre(rs.getString("nombre"));
				ciudad.setEstado(rs.getString("estado"));
				System.out.println("llega?");
				listaCiudad.add(ciudad);
				
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("ciudad", listaCiudad);
			
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
	
	
	
	
	public Map<String, Object> obtieneCiudades  (String codCiu, String codProv)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Ciudad>listaCiudad= new ArrayList<Ciudad>();
		Ciudad ciudad ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_ciudades(?,?)}"
	            );
			oraCallStmt.setString(1, codCiu);
			oraCallStmt.setString(2, codProv);
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				ciudad = new Ciudad();
				ciudad.setProCodigo(rs.getString("pro_codigo"));
				ciudad.setCiuCodigo(rs.getString("ciu_codigo"));
				ciudad.setNombre(rs.getString("nombre"));
				ciudad.setEstado(rs.getString("estado"));
				listaCiudad.add(ciudad);
				
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("ciudad", listaCiudad);
			
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
	
	
	public Map<String, Object> obtieneAreas  (String codCiu, String codProv)
	{
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		CallableStatement oraCallStmt   = null;
		Connection conn=null;
		List<Areas>listaAreas= new ArrayList<Areas>();
		Areas area ;
		ResultSet rs;

		
		try
		{ 
			
			System.out.println("######################llamando procesure");

			conn=getConnection();
			System.out.println(conn);
			oraCallStmt = (CallableStatement) conn.prepareCall(
	                "{call sp_consulta_areas(?,?)}"
	            );
			oraCallStmt.setString(1, codProv);
			oraCallStmt.setString(2, codCiu);
			
			
			
			rs=oraCallStmt.executeQuery();
//			System.out.println("oraCallStmt.getInt(1): "+oraCallStmt.getInt(1));
			while(rs.next()){
				area= new Areas();
				area.setIdAreas(rs.getInt("id_areas"));
				area.setCodigoProvincia(rs.getString("pro_codigo"));
				area.setCodigoCiudad(rs.getString("ciu_codigo"));
				area.setNombre(rs.getString("nombre"));
				area.setEstado(rs.getString("estado"));
				area.setNombreProvincia(rs.getString("provincia"));
				area.setNombreCiudad(rs.getString("ciudad"));
				//valor_minimo, valor_maximo, valor_x_hora
				area.setValorMaximo(rs.getDouble("valor_maximo"));
				area.setValorMinimo(rs.getDouble("valor_minimo"));
				area.setValorPorHora(rs.getDouble("valor_x_hora"));
				area.setRutaImagen(rs.getString("ruta_imagen"));
				
				listaAreas.add(area);
			}

			
//			mapResult.put("numCodigoErr", oraCallStmt.getInt(1));
			mapResult.put("areas", listaAreas);
			
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
