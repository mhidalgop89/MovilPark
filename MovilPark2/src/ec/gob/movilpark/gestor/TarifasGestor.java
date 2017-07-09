package ec.gob.movilpark.gestor;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dto.Session;

public class TarifasGestor {
	
	Window win;
	private Session objSession;
	private double tarifaMinima=0.25;//parametrizable
	private double tarifaMaxima=3.75;//parametrizable
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session)Executions.getCurrent().getSession().getAttribute("session");
		System.out.println("objSession"+objSession);
		if(objSession==null)
			{
				Executions.getCurrent().sendRedirect("../");
				return;
			}
		System.out.println("TarifasGestor");
	
		
	}
	
	@Command
	public void salir(){
		win.detach();		
	}

	public double getTarifaMinima() {
		return tarifaMinima;
	}

	public void setTarifaMinima(double tarifaMinima) {
		this.tarifaMinima = tarifaMinima;
	}

	public double getTarifaMaxima() {
		return tarifaMaxima;
	}

	public void setTarifaMaxima(double tarifaMaxima) {
		this.tarifaMaxima = tarifaMaxima;
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
