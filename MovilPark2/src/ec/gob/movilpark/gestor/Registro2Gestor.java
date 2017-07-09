package ec.gob.movilpark.gestor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Usuario;

public class Registro2Gestor {

	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Usuario objUsuario;
	private String retypePass;
	private String retypeMail;
	
	Window win;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		System.out.println("Regisro2");
		
		objUsuario=(Usuario)Executions.getCurrent().getSession().getAttribute("usuario");
		if(objUsuario==null)
			{
				Executions.getCurrent().sendRedirect("/Aplicacion/registro1.zul");
				return;
			}
		
	}
	
	public boolean validateEmail(String email) {
		try{
		    // Compiles the given regular expression into a pattern.
		    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		    // Match the given input against this pattern
		    Matcher matcher = pattern.matcher(email);
		    return matcher.matches();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Command
	public void onAtras(){
		Executions.getCurrent().sendRedirect("/Aplicacion/registro1.zul");
		
	}
	
	@Command
	public void onSiguiente(){
		if(objUsuario.getEmail()==null){
			Messagebox.show("Campo Correo Electrónico obligatorio", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(retypeMail==null){
			Messagebox.show("Por favor llenar campo REPETIR CORREO ELECTRÓNICO", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(objUsuario.getPass()==null){
			Messagebox.show("Campo Contraseña obligatorio", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(retypePass==null){
			Messagebox.show("Por favor llenar campo REPETIR CONTRASEÑA", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if(!objUsuario.getEmail().equals(retypeMail)){
			Messagebox.show("Los Correos Electrónicos ingresados no son iguales.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if(!objUsuario.getPass().equals(retypePass)){
			Messagebox.show("Las Contraseñas ingresados no son iguales.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if(!validateEmail(objUsuario.getEmail())){
			Messagebox.show("El correo posee formato incorrecto.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
			
		
		Map<String,Object> mapConsulta= new HashMap<String , Object>();
		LlamaUsuario usuarioDao= new LlamaUsuario();
		boolean existe=false;
		mapConsulta = usuarioDao.consultaExisteUsuario(objUsuario.getEmail());
		if(mapConsulta.get("error") == null)
			{
				existe = (boolean) mapConsulta.get("existe");
				if(existe){
					Messagebox.show("Correo electrónico ya fue previamente ingresado, por favor ingrese uno diferente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
					return;					
				}
							
			}
		else
			{
				Messagebox.show(mapConsulta.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		
		
		Executions.getCurrent().getSession().setAttribute("usuario", objUsuario);
		Executions.getCurrent().sendRedirect("/Aplicacion/registro3.zul");
		
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


	public String getRetypePass() {
		return retypePass;
	}


	public void setRetypePass(String retypePass) {
		this.retypePass = retypePass;
	}


	public String getRetypeMail() {
		return retypeMail;
	}


	public void setRetypeMail(String retypeMail) {
		this.retypeMail = retypeMail;
	}
	
}
