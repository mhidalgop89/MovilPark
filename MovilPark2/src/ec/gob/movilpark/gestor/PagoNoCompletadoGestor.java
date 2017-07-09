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

public class PagoNoCompletadoGestor {
	
	private Session objSession;
	Window win;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null)
			Executions.getCurrent().sendRedirect("../");
		}
	
	
	@Command
	public void onCross(){
		
		Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
	}
	
	
	@Command
	public void onServicio(){
		Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
	}

}
