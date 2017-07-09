package ec.gob.movilpark.gestor;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dao.LlamaUsuario;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Usuario;


public class MantenimientoPlacaGestor {
	private Session objSession;
	Window winModel;
	private Usuario objUsuario;
	private String placa="";
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View,@ExecutionArgParam("usuario") Usuario objUsuario){
		Selectors.wireComponents(View, this, false);
		winModel = (Window) View;	
		
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
				return;
			}
		this.objUsuario = objUsuario;
	}

	@Command
	public void salir(){
		winModel.detach();
	}
	
	@Command
	public void ingresarPlaca(){
		
		if(placa==null){
			Messagebox.show("Por favor Ingresar Placa", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Map<String,Object> mapPlacas= new HashMap<String , Object>();
		LlamaUsuario usuarioDao= new LlamaUsuario();
		
		mapPlacas = usuarioDao.insertaVehiculo(placa.toUpperCase(), objUsuario.getUsuarioId());
		
		if(mapPlacas.get("error") != null){
			Messagebox.show(mapPlacas.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}else{
			winModel.detach();
		}
		
	}
	
	public Window getWinModel() {
		return winModel;
	}

	public void setWinModel(Window winModel) {
		this.winModel = winModel;
	}

	public Usuario getObjUsuario() {
		return objUsuario;
	}

	public void setObjUsuario(Usuario objUsuario) {
		this.objUsuario = objUsuario;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Session getObjSession() {
		return objSession;
	}

	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}
	
	

}
