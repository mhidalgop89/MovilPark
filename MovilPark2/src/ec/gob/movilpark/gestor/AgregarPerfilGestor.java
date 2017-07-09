package ec.gob.movilpark.gestor;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dao.DatosParametros;
import ec.gob.movilpark.dao.PerfilesDao;
import ec.gob.movilpark.dto.Perfil;
import ec.gob.movilpark.dto.Session;

public class AgregarPerfilGestor {
	
	private String nombrePerfil;
	private Perfil objPerfil;
	private Session objSession;
	private String estado;
	private boolean disabled=false;
	Window win;
	

	
	

	@Init
	@NotifyChange("*")
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("perfil") Perfil objPerfil)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null)
			Executions.getCurrent().sendRedirect("../");
		
		System.out.println("objPerfil: "+objPerfil);
		if(objPerfil!=null){
			this.objPerfil=objPerfil;
//			cmbEstadoPerfil.setDisabled(false);
			nombrePerfil=this.objPerfil.getNombre();
			if("A".equals(objPerfil.getEstado()))
				estado="A";
			else
				estado="I";
		}
		else{
			
			estado="A";
			disabled=true;
//			cmbEstadoPerfil.setSelectedIndex(0);
//			cmbEstadoPerfil.setDisabled(true);
			
		}
		
		
			
		
	}
	
	@Command
	public void guardarPerfil(){
		
		if(nombrePerfil==null){
			Messagebox.show("Debe llenar todos los campos.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(estado==null){
			Messagebox.show("Debe llenar todos los campos.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		System.out.println("inicia guarda perfil");
		Map<String,Object> map= new HashMap<String , Object>();
		PerfilesDao perfilDao= new PerfilesDao();
		if(this.objPerfil==null)
			map = perfilDao.insertaPerfil(nombrePerfil.toUpperCase());
		else{
			objPerfil.setNombre(nombrePerfil);
			
			objPerfil.setEstado(estado);
			map = perfilDao.editaPerfil(objPerfil);
		}
			
		
		if(map.get("error") == null)
			Messagebox.show("Almacenado con exito.", "Atención", Messagebox.OK, Messagebox.INFORMATION);					
		else
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		
		win.detach();	
	}
	
	@Command
	public void salir(){
		win.detach();		
	}

	public String getNombrePerfil() {
		return nombrePerfil;
	}

	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}

	public Perfil getObjPerfil() {
		return objPerfil;
	}

	public void setObjPerfil(Perfil objPerfil) {
		this.objPerfil = objPerfil;
	}


	public Session getObjSession() {
		return objSession;
	}

	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}
