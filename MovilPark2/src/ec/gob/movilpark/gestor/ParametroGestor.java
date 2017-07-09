package ec.gob.movilpark.gestor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dao.DatosParametros;
import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.Parametros;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Vehiculo;

public class ParametroGestor {

	private Parametros objParam=new Parametros();
	private Session objSession;
	
	Window win;
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null)
			Executions.getCurrent().sendRedirect("../");
		cargar();
	}
	
	@NotifyChange("*")
	public void cargar(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		
		map = listasDao.obtieneParametros();
		
		if(map.get("error") == null){
			
			objParam=(Parametros) map.get("parametro");
			
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Command
	public void onAtras(){
		Executions.getCurrent().sendRedirect("/Aplicacion/parquimetro.zul");
	}
	
	@Command
	public void onGuardar(){
		System.out.println("guardar?");
		Map<String,Object> map= new HashMap<String , Object>();
		DatosParametros datosDao= new DatosParametros();
		
		map = datosDao.actualizaParametros(objParam, objSession.getUsuario().getUsuario());
		
		if(map.get("error") == null)
			Messagebox.show("Almacenado con exito.", "Atención", Messagebox.OK, Messagebox.INFORMATION);					
		else
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		
		
	}

	public Parametros getObjParam() {
		return objParam;
	}

	public void setObjParam(Parametros objParam) {
		this.objParam = objParam;
	}
	
	
}
