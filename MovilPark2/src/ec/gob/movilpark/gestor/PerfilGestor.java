package ec.gob.movilpark.gestor;

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

import ec.gob.movilpark.dao.PerfilesDao;
import ec.gob.movilpark.dto.Perfil;
import ec.gob.movilpark.dto.RelacionPerfilPermiso;
import ec.gob.movilpark.dto.Session;

public class PerfilGestor {

	private List<Perfil> lsPerfiles;
	private Perfil objPerfil;
	private Session objSession;
	Window win;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null)
			Executions.getCurrent().sendRedirect("../");
		consultaPerfil();
		
	}
	
	@NotifyChange("lsPerfiles")
	public void consultaPerfil(){
		PerfilesDao perfilesDao= new PerfilesDao();
		Map<String,Object> map= new HashMap<String , Object>();
		map = perfilesDao.consultaPerfil();
		
		if(map.get("error") == null){
			lsPerfiles=(List<Perfil>) map.get("perfiles");
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
	}
	
	@NotifyChange("lsPerfiles")
	@Command
	public void nuevoPerfil(){
		
		System.out.println("inicia edita perfil");
		Window winAgregarPerfil = (Window) Executions.createComponents("agregarPerfil.zul", null, null);
		winAgregarPerfil.doModal();	
		consultaPerfil();
		
	}

	@Command
	@NotifyChange("lsPerfiles")
	public void editaPerfil(){
		
		System.out.println("objPerfil: "+objPerfil);
		if(objPerfil==null)
			objPerfil=new Perfil();
			
			
		Map mapParametros = new HashMap();
		mapParametros.put("perfil",objPerfil);
		Window winAgregarPerfil = (Window) Executions.createComponents("agregarPerfil.zul", null, mapParametros);
		winAgregarPerfil.doModal();	
		consultaPerfil();
		System.out.println("sale edita perfil ");
	}
	

	public List<Perfil> getLsPerfiles() {
		return lsPerfiles;
	}


	public void setLsPerfiles(List<Perfil> lsPerfiles) {
		this.lsPerfiles = lsPerfiles;
	}

	public Perfil getObjPerfil() {
		return objPerfil;
	}

	public void setObjPerfil(Perfil objPerfil) {
		this.objPerfil = objPerfil;
	}
	
}
