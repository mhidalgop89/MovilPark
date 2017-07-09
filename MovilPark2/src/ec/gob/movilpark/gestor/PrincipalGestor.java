package ec.gob.movilpark.gestor;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
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

import ec.gob.movilpark.dao.LlamaUsuario;
import ec.gob.movilpark.dao.PerfilesDao;
import ec.gob.movilpark.dto.Perfil;
import ec.gob.movilpark.dto.RelacionPerfilPermiso;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Usuario;

public class PrincipalGestor {
	
	
	private Session objSession;
	private String rutaInclude="/Aplicacion/mantenimientoParquimetro.zul";
	
	Window win;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null)
			Executions.getCurrent().sendRedirect("../");
		
		System.out.println("xD");
		
	}
	
	
	
	@Command
	@NotifyChange("rutaInclude")
	public void cargaFrameDinamico(@BindingParam("param1")String ruta,@BindingParam("param2")String prm_valor) throws InterruptedException
	{
		EspacioPorParquimetroGestor espacio = new EspacioPorParquimetroGestor();
		espacio.setRepiteTimer(false);
		
		Perfil perfil = new Perfil();
		perfil = objSession.getPerfil();
		PerfilesDao perDao = new PerfilesDao();
		boolean existeRelacionPerfilPermiso=false;
		RelacionPerfilPermiso objRelPerfilPermiso;
		Map<String,Object> map= new HashMap<String , Object>();
		System.out.println("prm_valor: "+prm_valor);
		map = perDao.buscaRelacionPerfilPermiso(perfil, prm_valor);
		
		if(map.get("error") == null){
			objRelPerfilPermiso=(RelacionPerfilPermiso) map.get("perfilPermiso");
			if(objRelPerfilPermiso.getIdRelacionPerfilPermiso()==0){
				Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				return;
			}
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		existeRelacionPerfilPermiso = (objRelPerfilPermiso==null)?false:true;
		System.out.println("objRelPerfilPermiso"+objRelPerfilPermiso+"ruta:"+ruta+"_prm_valor"+prm_valor);
				
		if(existeRelacionPerfilPermiso)
			{
				//westPrincipal.setOpen(false);// 777
				rutaInclude=ruta;
			
			}
		else
			{
			//westPrincipal.setOpen(true);
			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			//Messagebox.show(ReadPropertiesUtil.obtenerProperty("NoTienePermiso", objUsuarioSistema.getIdioma()),"Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			}
	}
	
	
	@Command
	public void salir(){
		Executions.getCurrent().getSession().removeAttribute("session");
		Executions.getCurrent().sendRedirect("../");
	}



	public Session getObjSession() {
		return objSession;
	}



	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}



	public String getRutaInclude() {
		return rutaInclude;
	}



	public void setRutaInclude(String rutaInclude) {
		this.rutaInclude = rutaInclude;
	}
	
	

}
