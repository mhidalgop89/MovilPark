package ec.gob.movilpark.gestor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import ec.gob.movilpark.dao.LlamaUsuario;
import ec.gob.movilpark.dao.PerfilesDao;
import ec.gob.movilpark.dto.Perfil;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Usuario;

public class UsuarioGestor {
	
	private List<Usuario> lsUsuarios;
	private Usuario objUsuario;
	Window win;
	private Session objSession;	
	

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("perfil") Perfil objPerfil)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
			return;
		}
		
		consultaUsuarios();
	}
	
	@NotifyChange("*")
	public void consultaUsuarios(){
		LlamaUsuario usuariosDao= new LlamaUsuario();
		Map<String,Object> map= new HashMap<String , Object>();
		map = usuariosDao.consultaUsuario();
		
		if(map.get("error") == null){
			lsUsuarios=(List<Usuario>) map.get("usuarios");
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
	}
	
	
	@NotifyChange("lsUsuarios")
	@Command
	public void editaUsuario(){
		
		System.out.println("inicia editaUsuario");
		System.out.println("objUsuario: "+objUsuario);
		if(objUsuario==null)
			objUsuario=new Usuario();
			
			
		Map mapParametros = new HashMap();
		mapParametros.put("usuario",objUsuario);
		Window winUsuario = (Window) Executions.createComponents("agregarUsuario.zul", null, mapParametros);
		winUsuario.doModal();	
		consultaUsuarios();
		System.out.println("sale editaUsuario ");
	}
	
	@NotifyChange("lsUsuarios")
	@Command
	public void nuevoUsuario(){
		
		System.out.println("inicia nuevo usuario");
		Window winUsuario = (Window) Executions.createComponents("agregarUsuario.zul", null, null);
		winUsuario.doModal();	
		consultaUsuarios();
	}
	
	@Command
	public void salir(){
		win.detach();
	}

	public List<Usuario> getLsUsuarios() {
		return lsUsuarios;
	}

	public void setLsUsuarios(List<Usuario> lsUsuarios) {
		this.lsUsuarios = lsUsuarios;
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

	public Session getObjSession() {
		return objSession;
	}

	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}

}
