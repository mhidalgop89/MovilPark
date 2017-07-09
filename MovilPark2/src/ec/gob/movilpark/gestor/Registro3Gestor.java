package ec.gob.movilpark.gestor;

import java.util.HashMap;
import java.util.List;
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

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dao.LlamaUsuario;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Usuario;
import ec.gob.movilpark.util.Mail;


public class Registro3Gestor {
	
	private String placa1;
	private String placa2;
	private String placa3;
	private String placa4;
	private boolean aceptaCondiciones=false;

	private Usuario objUsuario;
	Window win;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		
		System.out.println("Regisro3");
		
		objUsuario=(Usuario)Executions.getCurrent().getSession().getAttribute("usuario");
		if(objUsuario==null)
			{
				Executions.getCurrent().sendRedirect("/Aplicacion/registro2.zul");
				return;
			}
		
		
		
	}
	

	
	@Command
	public void onAtras(){
		Executions.getCurrent().sendRedirect("/Aplicacion/registro2.zul");
		
	}
	
	@Command
	public void onSiguiente(){
		
		int idPersona;
		if(placa1==null){
			Messagebox.show("Por favor Ingresar Placa 1", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		
		Map<String,Object> map= new HashMap<String , Object>();
		Map<String,Object> mapPlacas= new HashMap<String , Object>();
		LlamaUsuario usuarioDao= new LlamaUsuario();
		objUsuario.setUsuario(objUsuario.getEmail());
		objUsuario.setIdTipoUsuario(2);
		objUsuario.setPerId(3);//setea como usuario movil
		map = usuarioDao.insertaUsuario2(objUsuario);
		
		
		if(map.get("error") == null){
			
			idPersona=(int) map.get("idPersona");
			System.out.println("idPersona..."+idPersona);
			
			mapPlacas = usuarioDao.insertaVehiculo(placa1.toUpperCase(), idPersona);	
			
			if(mapPlacas.get("error") != null){
				Messagebox.show("Error al ingresar placa: "+placa1+" - "+mapPlacas.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			}else{
				
				if(placa2!=null){
					mapPlacas = usuarioDao.insertaVehiculo(placa2.toUpperCase(), idPersona);
					if(mapPlacas.get("error") != null)
						Messagebox.show("Error al ingresar placa: "+placa2+" - "+mapPlacas.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
					else{
						if(placa3!=null){
							mapPlacas = usuarioDao.insertaVehiculo(placa3.toUpperCase(), idPersona);
							if(mapPlacas.get("error") != null)
								Messagebox.show("Error al ingresar placa: "+placa3+" - "+mapPlacas.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
							else{
								if(placa4!=null){
									mapPlacas = usuarioDao.insertaVehiculo(placa4.toUpperCase(), idPersona);
										if(mapPlacas.get("error") != null)
											Messagebox.show("Error al ingresar placa: "+placa4+" - "+mapPlacas.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
										else
											Messagebox.show("Usuario Ingresado Correctamente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
								}else
									Messagebox.show("Usuario Ingresado Correctamente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
							}		
						}else
							Messagebox.show("Usuario Ingresado Correctamente", "Atención", Messagebox.OK, Messagebox.INFORMATION);					
					}
				}else
					Messagebox.show("Usuario Ingresado Correctamente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
				
					
			}
			
			enviarCorreo();
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		Executions.getCurrent().getSession().removeAttribute("usuario");
		Executions.getCurrent().sendRedirect("../");
		
	}
	
	@Command
	public void enviarCorreo(){
		
		
					System.out.println("to:"+objUsuario.getUsuario());
		boolean envio=true;
		String subject="Parqueo Positivo";

		String to[]= new String[1];
		to[0]=objUsuario.getUsuario();

		String cc[]= new String[1];
		cc[0]="mario.hidalgo89@hotmail.com";
					
		String bcc[]= new String[1];
		bcc[0]="mhidalgop89@gmail.com";

		String msgToSend="<div style='padding:10px;background: url('http://172.16.0.140/portaldeinformacion/images/top.png') repeat-x;'><div width='300px' style='width:500px;margin:0 auto;border:1px solid #6f6f6f;height:auto;padding: 30px;'>"+
                "<img src='http://13.82.51.231:8080/MovilPark/images/main-logo-dark1.png' width='267px' height='80px' /><br><br>"+
                
                "<br><br>Saludos <b>"+objUsuario.getNombre()+" "+objUsuario.getApellido()+"</b><br><br>"+
                "Te damos la mas cordial bienvenida al sistema de SoftMobile,<br> "+
                "los datos registrados son los Siguientes:<br><br>"+
                "Nombres y Apellidos:   <b>"+objUsuario.getNombre()+" "+objUsuario.getApellido() +"</b><br><br>"+
                "Email/Usuario:   <b>"+objUsuario.getUsuario()+"</b><br><br>"+
                "Placa:   <br>"+
                "placa 1: <b>"+placa1.toUpperCase()+"</b><br>"+
                "placa 2: <b>"+((placa2==null)?"No se registra":placa2.toUpperCase())+"</b><br>"+
                "placa 3: <b>"+((placa3==null)?"No se registra":placa3.toUpperCase())+"</b><br>"+
                "placa 4: <b>"+((placa4==null)?"No se registra":placa4.toUpperCase())+"</b><br><br>"+
                "El valor de cada transacci&oacute;n ser&aacute; de $0.15 ctvs   <br><br>"+
                
				"Las presentes condiciones generales de uso del portal regulan el acceso y la utilización del portal, incluyendo los contenidos y los servicios puestos a disposición de los usuarios en y/o a través del portal, bien por el portal, bien por sus usuarios, bien por terceros. No obstante, el acceso y la utilización de ciertos contenidos y/o servicios pueden encontrarse sometido a determinadas condiciones específicas:"
				+"    <br>"
				+"    <b>Vigencia del saldo: </b> El saldo recargado tendrá vigencia de 1 año<br>"
				+"    <b>Costo por transacción: </b>El costo por transacción es de $0,15 ctvs. <br><br>"+
                
				 "<table border='1'> "
				+ " <tr> "
				+ " <th bgcolor='#ccccff' colspan=3 align=center>"
				+ "COSTOS"
				+ "</th> "
				+ " </tr> "
				+ " <tr> "
				+ " <td> OFICINA </td><td>"
				+ "COSTO MINIMO</td><td>"
				+ "COSTO MAXINO"
				+ "</td> "
				+ " </tr> "
				+ " <tr> "
				+ " <td>SAMBORONDON </td><td>"
				+ "$0.25(15 minutos)</td><td>"
				+ "$4.00 (4 horas)"
				+ "</td> "
				+ " </tr> "
				+ " <tr> "
				+ " <td>SANTA ANA</td><td>"
				+ "$0.50 (30 minutos)</td><td>"
				+ "$6.00 (6 horas)"
				+ "</td> "
				+ " <tr> "
				+ " <td>Machala</td><td>"
				+ "$0.25 (15 minutos)</td><td>"
				+ "$4.00 (4 horas)"
				+ "</td> "
				+ " </tr> " + " </table><br><br>"
                
				+"<b>Áreas</b>"
				+"<ul>"
				+"    <li>Puerto Santa Ana (área Puerto Santa Ana).</li>"
				+"    <li>Samborondón (Entreríos, Kennedy. Business, Xima y Pacífico).</li>"
				+"    <li>MACHALA (Aguilar, GPAO, Veinticinco, Centro, IESS).</li>"
				+"</ul><br><br>"
                
                
                +"<table border='1'> "
				+ " <tr> "
				+ " <th bgcolor='#ccccff' colspan=3 align=center>"
				+ "HORARIOS"
				+ "</th> "
				+ " </tr> "
				+ " <tr> "
				+ " <td> OFICINA </td><td>"
				+ "LUNES-VIERNES</td><td>"
				+ "SABADO"
				+ "</td> "
				+ " </tr> "
				+ " <tr> "
				+ " <td>SAMBORONDON </td><td>"
				+ "8:00AM - 8:00PM </td><td>"
				+ "8:00AM - 8:00PM"
				+ "</td> "
				+ " </tr> "
				+ " <tr> "
				+ " <td>SANTA ANA</td><td>"
				+ "24 HORAS </td><td>"
				+ "24 HORAS"
				+ "</td> "
				+ " </tr> " + " </table>";
		
                /*"Inicio:  <b>"+objSession.getTramite().getFechaInicial()+"</b><br>"+
                "Fin:  <b>"+objSession.getTramite().getFechaFinal()+"</b><br>";
                "Numero(Nombre) del Parquimetro:  <b>"+objSession.getParquimetro().getNombre()+"</b><br>"+
                "Numero de espacio:  <b>"+objSession.getEspacioPorParquimetro().getNumeroEspacio()+"</b><br>"+
                "Minutos  <b>"+objSession.getTramite().getMinutos()+"</b><br><br>";*/
                
		msgToSend=msgToSend+"<br><br>Saludos Cordiales,<br>"+
				"Parqueo Positivo"+
                "</div>"+
                "</div>";
		
		Mail mailUtil= new Mail();
//		envio=mailUtil.send("sales@allparts.expert", to, subject,cc , bcc, msgToSend,null);
		envio=mailUtil.enviarMail(msgToSend,to[0]);
		
		if(!envio)
			System.out.println("fallo el envío del correo electronico:"+objUsuario.getUsuario());
//			Messagebox.show("Falló el envio de correo.","Attention",Messagebox.OK,Messagebox.INFORMATION);
		else{
			
			Messagebox.show("Una notificación fué enviada a su correo..",
					"Attention",
					Messagebox.OK,Messagebox.INFORMATION);
//			winModel.detach();
		}
			
		
		
	}
	
	
	/*if(mapPlacas.get("error") == null){
	
	mapPlacas = usuarioDao.insertaVehiculo(placa2, idPersona);
	if(mapPlacas.get("error") == null){
		mapPlacas = usuarioDao.insertaVehiculo(placa3, idPersona);
		
		if(mapPlacas.get("error") == null){
			mapPlacas = usuarioDao.insertaVehiculo(placa4, idPersona);
			if(mapPlacas.get("error") != null){
				Messagebox.show(mapPlacas.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			}
			
		}else{
			Messagebox.show(mapPlacas.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		
	}else{
		Messagebox.show(mapPlacas.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
	}
	
	
}else{
	Messagebox.show(mapPlacas.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
}*/


	public String getPlaca1() {
		return placa1;
	}


	public void setPlaca1(String placa1) {
		this.placa1 = placa1;
	}


	public String getPlaca2() {
		return placa2;
	}


	public void setPlaca2(String placa2) {
		this.placa2 = placa2;
	}


	public String getPlaca3() {
		return placa3;
	}


	public void setPlaca3(String placa3) {
		this.placa3 = placa3;
	}


	public String getPlaca4() {
		return placa4;
	}


	public void setPlaca4(String placa4) {
		this.placa4 = placa4;
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



	public boolean isAceptaCondiciones() {
		return aceptaCondiciones;
	}



	public void setAceptaCondiciones(boolean aceptaCondiciones) {
		this.aceptaCondiciones = aceptaCondiciones;
	}
	
	

}
