package ec.gob.movilpark.gestor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.Perfil;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Tramite;
import ec.gob.movilpark.dto.Usuario;

public class DetalleEstadoCuentasGestor {

	Window win;
	private Session objSession;	
	private List<Tramite> listaTramite;
	private long horas;
	private Tramite objTramite;
	
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View,@ExecutionArgParam("tramite") Tramite objTramite){
		Selectors.wireComponents(View, this, false);
		win = (Window) View;	
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
			return;
		}
		this.objTramite = new Tramite();
		this.objTramite.setPlaca(objTramite.getPlaca());
		cargaTramite();
	}
	
	

	
	@Command
	public void onSalir(){
		
		win.detach();
	}
	
	
	
	public long diferenciaEnDias2(Date fechaMayor, Date fechaMenor) {
		long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();
		long dias = diferenciaEn_ms / (1000 * 60 );
		return  dias;
		}
	
	@NotifyChange({"listaTramite","horas"})
	public void cargaTramite(){
		Listas listaDao = new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listaDao.consultaTramiteXplacaXusuario(objSession.getUsuario().getUsuarioId(),objTramite.getPlaca());
		System.out.println("usuario y placa: "+objSession.getUsuario().getUsuarioId()+"--"+objTramite.getPlaca());
		listaTramite= new ArrayList<Tramite>();
		if(map.get("error") == null){
			listaTramite=(List<Tramite>) map.get("listTramite");
			if(listaTramite!=null)
				if(listaTramite.size()>0)
					horas = horas+diferenciaEnDias2(listaTramite.get(listaTramite.size()-1).getFechaFinal() ,new java.util.Date());
//			for(int i=0;i<listaTramite.size();i++)
//				horas = horas+diferenciaEnDias2(listaTramite.get(i).getFechaFinal() ,new java.util.Date());
			
		}else
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		System.out.println("----> horas: "+horas);
		
	}



	public Window getWin() {
		return win;
	}



	public void setWin(Window win) {
		this.win = win;
	}



	public Session getObjSession() {
		return objSession;
	}



	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}



	public List<Tramite> getListaTramite() {
		return listaTramite;
	}



	public void setListaTramite(List<Tramite> listaTramite) {
		this.listaTramite = listaTramite;
	}



	public long getHoras() {
		return horas;
	}



	public void setHoras(long horas) {
		this.horas = horas;
	}
	
	
}
