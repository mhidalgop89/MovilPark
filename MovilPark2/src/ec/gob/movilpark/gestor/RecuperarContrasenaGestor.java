package ec.gob.movilpark.gestor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import ec.gob.movilpark.util.Mail;

public class RecuperarContrasenaGestor {
	
	private String mail="";
	private String newPass;
	Window win;
	private Session objSession;



	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		
	}
	
	
	public String getCadenaAlfanumAleatoria (int longitud){
			String cadenaAleatoria = "";
			long milis = new java.util.GregorianCalendar().getTimeInMillis();
			Random r = new Random(milis);
			int i = 0;
			while ( i < longitud){
				char c = (char)r.nextInt(255);
				if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
					cadenaAleatoria += c;
					i ++;
				}
			}
			return cadenaAleatoria;
		}

	
	@Command
	public void onEnviarCorreo(){
		newPass= getCadenaAlfanumAleatoria(4);
		if(mail!=null){
			
			
			Map<String,Object> map= new HashMap<String , Object>();
			LlamaUsuario usuarioDao= new LlamaUsuario();
				map = usuarioDao.actualizaPasswordPorUsuario(mail,newPass);
			
			
			if(map.get("error") != null){
					Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			enviarCorreo();
			Executions.getCurrent().sendRedirect("../");
		}else{
			Messagebox.show("Campo Correo Electrónico obligatorio.", "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	@Command
	public void onAtras(){
		Executions.getCurrent().sendRedirect("../");
	}
	@Command
	public void enviarCorreo(){
		
		
					
		boolean envio=true;
		String subject="Parqueo Positivo";

		String to[]= new String[1];
		to[0]=mail;

		String cc[]= new String[1];
		cc[0]="mario.hidalgo89@hotmail.com";
					
		String bcc[]= new String[1];
		bcc[0]="mhidalgop89@gmail.com";

		
		String msgToSend="<div style='padding:10px;background: url('http://172.16.0.140/portaldeinformacion/images/top.png') repeat-x;'><div width='300px' style='width:500px;margin:0 auto;border:1px solid #6f6f6f;height:auto;padding: 30px;'>"+
                "<img src='http://40.76.59.162:8080/MovilPark/images/main-logo-dark1.png' width='267px' height='80px' /><br><br>"+
                
                "<br><br><b>Nuevo Password:  </b><br><br>"+
                "Password:   <b>"+newPass+"</b><br>"+
                "Porfavor no olvide actualizar su password  <b></b><br><br>";
                
		msgToSend=msgToSend+"<br><br>Saludos Cordiales,<br>"+
				"Parqueo Positivo"+
                "</div>"+
                "</div>";
		
		Mail mailUtil= new Mail();
//		envio=mailUtil.send("sales@allparts.expert", to, subject,cc , bcc, msgToSend,null);
		envio=mailUtil.enviarMail(msgToSend,mail);
		
		if(!envio)
			Messagebox.show("Falló el envio de correo.",
					"Attention",
					Messagebox.OK,Messagebox.INFORMATION);
		else{
			
			Messagebox.show("Una notificación fué enviada a su correo.",
					"Attention",
					Messagebox.OK,Messagebox.INFORMATION);
//			winModel.detach();
		}
			
		
		
	}

	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
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


	public String getNewPass() {
		return newPass;
	}


	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

}
