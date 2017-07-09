package ec.gob.movilpark.gestor;

import java.util.HashMap;
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

import ec.gob.movilpark.dao.LlamaUsuario;
import ec.gob.movilpark.dto.Session;

public class CambiarPassClientGestor {
	private Session objSession;
	Window win;
	private String password;
	private String confirmaPassword;
	
	

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
	}
	@Command
	public void onAtras(){
		
		/* 
		 if(usuSession.getPerId()==3)//movil
							Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
						else if(usuSession.getPerId()==1)//administrador
							Executions.getCurrent().sendRedirect("/Aplicacion/principal.zul");
						else if(usuSession.getPerId()==4)//operador
							Executions.getCurrent().sendRedirect("/Aplicacion/serviciosOperador.zul"); 
		 
		 */
		if(objSession.getPerfil().getIdPerfil()==3)
			Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
		if(objSession.getPerfil().getIdPerfil()==4)
			Executions.getCurrent().sendRedirect("/Aplicacion/serviciosOperador.zul");
		
		
	}
	
	@Command 
	public void actualizaPass(){
		
		if(password==null || confirmaPassword==null){
			Messagebox.show("Debe llenar todos los campos.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(!password.equals(confirmaPassword))
		{
			Messagebox.show("Campo Confirma Password incorrecto.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		
		
		Map<String,Object> map= new HashMap<String , Object>();
		LlamaUsuario usuarioDao= new LlamaUsuario();
			map = usuarioDao.actualizaPassword(objSession.getUsuario().getUsuarioId(),confirmaPassword);
		
			
		
		if(map.get("error") == null)
			Messagebox.show("Almacenado con exito.", "Atención", Messagebox.OK, Messagebox.INFORMATION);					
		else
			{
				Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		
		Executions.getCurrent().getSession().removeAttribute("session");
		Executions.getCurrent().sendRedirect("../");
	}

	
	public Session getObjSession() {
		return objSession;
	}

	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}

	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmaPassword() {
		return confirmaPassword;
	}

	public void setConfirmaPassword(String confirmaPassword) {
		this.confirmaPassword = confirmaPassword;
	}
}
