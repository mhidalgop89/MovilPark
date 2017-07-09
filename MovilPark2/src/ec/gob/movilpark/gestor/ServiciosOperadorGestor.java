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

public class ServiciosOperadorGestor {
	
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
	}
	
	@Command
	public void onCambioPass(){
		Executions.getCurrent().sendRedirect("/Aplicacion/cambiarPassClient.zul");
	}
	
	@Command
	public void salir(){
		Executions.getCurrent().getSession().removeAttribute("session");
		Executions.getCurrent().sendRedirect("../");	
	}
	
	@Command
	public void onConsultaPorPlaca(){
		Executions.getCurrent().sendRedirect("/Aplicacion/conVehPorPlacaOperador.zul");
	}
	
	@Command
	public void onReporte(){
		Executions.getCurrent().sendRedirect("/Aplicacion/espacioParqOperador.zul");
	}
	@Command
	public void onAplicaMulta(){
		Executions.getCurrent().sendRedirect("/Aplicacion/multaVehiculo.zul");
	}

}
