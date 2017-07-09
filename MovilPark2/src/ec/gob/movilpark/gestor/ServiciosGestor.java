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

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.NotaCredito;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.util.Redondear;

public class ServiciosGestor {
	
	private List<NotaCredito> lsnotaCredito;
	private Double saldo=0.0;
	Window win;
	private Session objSession;
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		
		objSession=(Session)Executions.getCurrent().getSession().getAttribute("session");
		System.out.println("objSession"+objSession);
		if(objSession==null)
			{
			Executions.getCurrent().sendRedirect("../");
				return;
			}
		cargarCredito();
	}
	
	@NotifyChange("*")
	public void cargarCredito(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		
		map = listasDao.consultaNotasCreditosParam(objSession.getUsuario().getUsuarioId());
		
		if(map.get("error") == null){
			lsnotaCredito=(List<NotaCredito> ) map.get("listaNotaCredito");
			for(int i=0;i<lsnotaCredito.size();i++){
				saldo=saldo+lsnotaCredito.get(i).getSaldo();
			}
			Redondear red=new Redondear();
			saldo = red.Redondear2(saldo);
			objSession.setSaldo(saldo);
			Executions.getCurrent().getSession().setAttribute("session", objSession);
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Command
	public void onCambioPass(){
		Executions.getCurrent().sendRedirect("/Aplicacion/cambiarPassClient.zul");
	}
	
	@Command
	public void onParquimetro(){
		Executions.getCurrent().sendRedirect("/Aplicacion/parquimetro.zul");		
	}
	
	
	@Command
	public void salir(){
		Executions.getCurrent().getSession().removeAttribute("session");
		Executions.getCurrent().sendRedirect("../");  
	}

	public List<NotaCredito> getLsnotaCredito() {
		return lsnotaCredito;
	}

	public void setLsnotaCredito(List<NotaCredito> lsnotaCredito) {
		this.lsnotaCredito = lsnotaCredito;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
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
}
