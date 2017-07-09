package ec.gob.movilpark.gestor;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dto.Perfil;
import ec.gob.movilpark.dto.Session;

public class ParquimetroGestor {
	
	Window win;

	private Session objSession;	


	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
			return;
		}
		
		System.out.println("Regisro1");
		
	}
	
	@Command
	public void onRealizaPago(){
		
		Executions.getCurrent().sendRedirect("/Aplicacion/pagoEstacionamiento.zul");
	}
	
	@Command
	public void servicios(){
		
		Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
	}
	
	@Command
	public void irEstadoCuentas(){
		Executions.getCurrent().sendRedirect("/Aplicacion/estadoCuentas.zul");
	}

}
