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

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.NotaCredito;
import ec.gob.movilpark.dto.NotaCreditoParam;
import ec.gob.movilpark.dto.Session;

public class NotaCreditoParamGestor {

	private List<NotaCreditoParam> listaNotaCreditoParam= new ArrayList<NotaCreditoParam>();
	private NotaCreditoParam objNcParam;
	private String estado;
	private boolean disabled;
	Window win;
	private Session objSession;



	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false); 
		win =(Window) view;
		
		System.out.println("Regisro1");
		objSession=(Session)Executions.getCurrent().getSession().getAttribute("session");
		System.out.println("objSession"+objSession);
		if(objSession==null)
			{
			Executions.getCurrent().sendRedirect("../");
				return;
			}
		//objNcParam= new NotaCreditoParam();
		cargarCreditoParam();
	}
	
	@Command
	public void actualizaParam(){	
		
		if(objNcParam==null){
			Messagebox.show("Todos los campos son obigatorios", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if(objNcParam.getTiempoMaximoVigencia()<=0){
			Messagebox.show("El campo Tiempo máximo de vigencia no puede tener valores menores o igual a cero", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if(objNcParam.getValorMinimo()<=0){
			Messagebox.show("El campo Valor Minimo no puede tener valores menores o igual a cero", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if(objNcParam.getValormaximo()<=0){
			Messagebox.show("El campo valor máximo no puede tener valores menores o igual a cero", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		
		map = listasDao.editaNotasCreditosParametros(objNcParam);
		
		if(map.get("error") == null){
			Messagebox.show("Actualizado con exito.", "Atención", Messagebox.OK, Messagebox.INFORMATION);	
					
		}else
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		
	}

	
	@NotifyChange("*")
	public void cargarCreditoParam(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		
		map = listasDao.consultaNotasCreditosParametros();
		
		if(map.get("error") == null){
			listaNotaCreditoParam=(List<NotaCreditoParam> ) map.get("listaNotaCreditoParam");
				if(listaNotaCreditoParam!=null)
					if(listaNotaCreditoParam.size()>0){
						objNcParam=listaNotaCreditoParam.get(0);
						if("A".equals(objNcParam.getEstado()))
							estado="A";
						else
							estado="I";
					}
						
					
		}else
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		
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



	public NotaCreditoParam getObjNcParam() {
		return objNcParam;
	}



	public void setObjNcParam(NotaCreditoParam objNcParam) {
		this.objNcParam = objNcParam;
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


	public List<NotaCreditoParam> getListaNotaCreditoParam() {
		return listaNotaCreditoParam;
	}


	public void setListaNotaCreditoParam(List<NotaCreditoParam> listaNotaCreditoParam) {
		this.listaNotaCreditoParam = listaNotaCreditoParam;
	}
	
}
