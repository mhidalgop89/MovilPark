package ec.gob.movilpark.gestor;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
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

public class LoginGestor {
	
	Window win;
	private Usuario usuSession ;
	private Session objSession;
	private String usuario;
	private String pass;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		
		System.out.println("xD");
		
		objSession=(Session)Executions.getCurrent().getSession().getAttribute("session");
		System.out.println("objSession"+objSession);
		if(objSession!=null)
			{
				if(objSession.getPerfil().getIdPerfil()==3)//movil
					Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
				else if(objSession.getPerfil().getIdPerfil()==1)//administrador
					Executions.getCurrent().sendRedirect("/Aplicacion/principal.zul");
				else if(objSession.getPerfil().getIdPerfil()==4)//operador
					Executions.getCurrent().sendRedirect("/Aplicacion/serviciosOperador.zul"); 
			
				//Executions.getCurrent().sendRedirect("/Aplicacion/parquimetro.zul");
				return;
			}
		
	}
	
	@Command
	public void onRegistro(){
		Executions.getCurrent().sendRedirect("/Aplicacion/registro1.zul");		
	}
	
	@Command
	public void onIniciarSesion(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		LlamaUsuario usuarioDao= new LlamaUsuario();
		map = usuarioDao.obtieneUsuario(usuario, pass);
		
		if(map.get("error") == null){
			
			usuSession=(Usuario) map.get("usuario");
			System.out.println("apellido: "+usuSession.getApellido());
				if(usuSession.getUsuarioId()!=0)
					{
						if(objSession==null)objSession=new Session();
						objSession.setUsuario(usuSession);
						
						//trae el perfil
						Perfil perfil = new Perfil();
						PerfilesDao per= new PerfilesDao(); 
						Map<String,Object> map2= new HashMap<String , Object>();
						map2 = per.buscaPerfilPorUsuario(objSession.getUsuario().getUsuarioId());
						if(map2.get("error") == null){
							perfil=(Perfil) map2.get("perfil");
						}else{
							Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
							return;
						}
						
						objSession.setPerfil(perfil);						
						Executions.getCurrent().getSession().setAttribute("session", objSession);
						if(usuSession.getPerId()==3)//movil
							Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
						else if(usuSession.getPerId()==1)//administrador
							Executions.getCurrent().sendRedirect("/Aplicacion/principal.zul");
						else if(usuSession.getPerId()==4)//operador
							Executions.getCurrent().sendRedirect("/Aplicacion/serviciosOperador.zul"); 
					}
				else
					Messagebox.show("Credenciales Incorrectas", "Atención", Messagebox.OK, Messagebox.ERROR);
			
						
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		
		
		
	}

	public Usuario getUsuSession() {
		return usuSession;
	}

	public void setUsuSession(Usuario usuSession) {
		this.usuSession = usuSession;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Session getObjSession() {
		return objSession;
	}

	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}
	
	
}
