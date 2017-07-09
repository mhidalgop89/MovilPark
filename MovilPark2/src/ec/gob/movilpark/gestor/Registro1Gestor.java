package ec.gob.movilpark.gestor;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dto.Usuario;

public class Registro1Gestor {
	
	
	private Usuario objUsuario= new Usuario();
	Window win;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		
		System.out.println("Regisro1");
		
	}
	
	
	@Command
	public void onAtras(){
		Executions.getCurrent().sendRedirect("../");
	}
	
	@Command
	public void onSiguiente(){
		
		if(objUsuario.getNombre()==null){
			Messagebox.show("Campo nombre obligatorio", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(objUsuario.getApellido()==null){
			Messagebox.show("Campo apellido obligatorio", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(objUsuario.getTlf_movil()==null){
			Messagebox.show("Campo Teléfono Móvil obligatorio", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
			
		Executions.getCurrent().getSession().setAttribute("usuario", objUsuario);
		Executions.getCurrent().sendRedirect("/Aplicacion/registro2.zul");
		
	}


	public Usuario getObjUsuario() {
		return objUsuario;
	}


	public void setObjUsuario(Usuario objUsuario) {
		this.objUsuario = objUsuario;
	}


	public Window getWin() {
		return win;
	}


	public void setWin(Window win) {
		this.win = win;
	}

}
