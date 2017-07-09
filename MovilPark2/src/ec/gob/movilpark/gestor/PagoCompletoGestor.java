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

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.Pago;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Tramite;
import ec.gob.movilpark.util.Mail;

public class PagoCompletoGestor {
	
	private Double valorPagar=0.0;
	private Session objSession;
	private String metodoPago;
	Window win;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
			Selectors.wireComponents(view, this, false);
			win =(Window) view;
			
			objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
			if(objSession==null)
				{
				Executions.getCurrent().sendRedirect("../");
					return;
				}
			metodoPago = objSession.getMetodoPago();
			if(metodoPago==null)
			{
				Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
				return;
			}
			
			///si el usuario ya pago????
			valorPagar=objSession.getObjPago().getValorTotal();
			if("PP".equals(objSession.getMetodoPago())){
				confirmaPago2();
			}
		
		}
	
	
	@Command 
	public synchronized void confirmaPago2(){
		System.out.println("confirma pago 2");
		Tramite tramite =objSession.getTramite();
		Pago pago = objSession.getObjPago();
//		if(saldo<valorPagar)
//			pago.setValorPagado(saldo);
//		else
			pago.setValorPagado(valorPagar);
		
		Listas listaDao = new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listaDao.realizaTransaccionPago(objSession.getUsuario().getUsuarioId(), valorPagar, objSession.getUsuario().getUsuario(),tramite,pago); 
		
		if(map.get("error") != null){
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}		
		objSession.setObjPago(pago);
		enviarCorreo();
		Executions.getCurrent().getSession().setAttribute("session", objSession);
		//Executions.getCurrent().sendRedirect("/Aplicacion/pagoCompleto.zul");
		
	}
	
	
	
	@Command
	public void enviarCorreo(){
		
		
					
		boolean envio=true;
		String subject="Parqueo Positivo";

		String to[]= new String[1];
		to[0]=objSession.getUsuario().getUsuario();

		String cc[]= new String[1];
		cc[0]="mario.hidalgo89@hotmail.com";
					
		String bcc[]= new String[1];
		bcc[0]="mhidalgop89@gmail.com";

//		String msgToSend =  "<p>You have a request for a quote on the following part:"+"<br><br>"+
//				"Part Name "+this.item.getName()+"<br>"+
//				"Part Number "+this.item.getPartNumber()+"<br><br>"+
//				"With a Quantity of "+quantity+"<br><br>"+
//				"From: <br>"+
//				"Name: "+((name!=null)?name:"")+"<br>"+
//				"Company: "+((company!=null)?company:"")+"<br>"+
//				"Email: "+email+"<br>"+
//				"City: "+((city!=null)?city:"")+"<br>"+
//				"Country: "+((country!=null)?country:"")+"<br>"+
//				"Phone: "+((phone!=null)?phone:"")+"<br>"+
//				"Postalcode: "+((postalCode!=null)?postalCode:"")+"<br>"+
//				"State/Province: "+((stateProvince!=null)?stateProvince:"")+"<br>"+
//				"Fax: "+((fax!=null)?fax:"")+"<br>"+
//				"Address: "+((address!=null)?address:"")+"<br>"+
//				"</p>";	
		
		String msgToSend="<div style='padding:10px;background: url('http://172.16.0.140/portaldeinformacion/images/top.png') repeat-x;'><div width='300px' style='width:500px;margin:0 auto;border:1px solid #6f6f6f;height:auto;padding: 30px;'>"+
                "<img src='http://40.76.59.162:8080/MovilPark/images/main-logo-dark1.png' width='267px' height='80px' /><br><br>"+
                
                "<br><br><b>Resumen del Tramite:  </b><br><br>"+
                "Usuario:   <b>"+objSession.getUsuario().getUsuario() +"</b><br>"+
                "Inicio:  <b>"+objSession.getTramite().getFechaInicial()+"</b><br>"+
                "Fin:  <b>"+objSession.getTramite().getFechaFinal()+"</b><br>"+
                "Numero(Nombre) del Parquimetro:  <b>"+objSession.getParquimetro().getNombre()+"</b><br>"+
                "Numero de espacio:  <b>"+objSession.getEspacioPorParquimetro().getNumeroEspacio()+"</b><br>"+
                "Minutos  <b>"+objSession.getTramite().getMinutos()+"</b><br><br>";
                
		msgToSend=msgToSend+"<br><br>Saludos Cordiales,<br>"+
				"Parqueo Positivo"+
                "</div>"+
                "</div>";
		
		Mail mailUtil= new Mail();
//		envio=mailUtil.send("sales@allparts.expert", to, subject,cc , bcc, msgToSend,null);
		envio=mailUtil.enviarMail(msgToSend,objSession.getUsuario().getUsuario());
		
		if(!envio)
			{
				//Messagebox.show("Falló el envio de correo.","Attention",Messagebox.OK,Messagebox.INFORMATION);
				System.out.println("se envió");
			}
		else{
			
//			Messagebox.show("Una notificación fué enviada a su correo..","Attention",Messagebox.OK,Messagebox.INFORMATION);
			System.out.println("se envió");
		}
			
		
		
	}
	
	
	@Command
	public void onCheck(){
		
		Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
	}
	
	
	@Command
	public void onServicio(){
		Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
	}


	public Session getObjSession() {
		return objSession;
	}


	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}


	public String getMetodoPago() {
		return metodoPago;
	}


	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}


	public Window getWin() {
		return win;
	}


	public void setWin(Window win) {
		this.win = win;
	}

}
