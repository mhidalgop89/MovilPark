package ec.gob.movilpark.tarea;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Messagebox;

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.Tramite;
import ec.gob.movilpark.util.Mail;

public class VerificaTramites {
	
	public static void main(String[] args) {
		VerificaTramites vt = new VerificaTramites();
		List<Tramite> listaTramite;
		Tramite objTramite;
		Listas listaDao = new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		Map<String,Object> mapEsp;
		map = listaDao.consultaTramite();
		long hora=0;
		Date fechaFin;
		
		if(map.get("error") == null){
			
			listaTramite=(List<Tramite>) map.get("listTramite");
			if(listaTramite!=null)
				{
					for(int i=0;i<listaTramite.size();i++){
						hora= vt.diferenciaEnDias2(listaTramite.get(i).getFechaFinal(), new Date());
						System.out.println("hora: "+hora);
						if(hora<=0){
							
							mapEsp= new HashMap<String , Object>();
							mapEsp = listaDao.cierraParquimetrosEnUso(listaTramite.get(i).getIdEspacioPorParquimetro());
							if(map.get("error") != null){
								System.out.println("Error al hacer update en los espacios");
							}
							
							
						}else if(hora>0 && hora<6){
							
							vt.enviarCorreo(listaTramite.get(i).getUsuario());
						}
					}
				}
			
		}else{
			
			System.out.println("No hay tramites aún");
		}
	
	}
	
	public  long diferenciaEnDias2(Date fechaMayor, Date fechaMenor) {
		long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();
		long dias = diferenciaEn_ms / (1000 * 60 );
		return  dias;
		}
	
	
	@Command
	public void enviarCorreo(String paraEmail){
		
		
					
		boolean envio=true;
		String subject="Parqueo Positivo";

		String to[]= new String[1];
		to[0]=paraEmail;//objSession.getUsuario().getUsuario();

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
                
                "<br><br><b>Estimado Usuario usted posee menos de 5 minutos de plazo de parquimetro  </b><br><br>";
                
		msgToSend=msgToSend+"<br><br>Saludos Cordiales,<br>"+
				"Parqueo Positivo"+
                "</div>"+
                "</div>";
		
		Mail mailUtil= new Mail();
//		envio=mailUtil.send("sales@allparts.expert", to, subject,cc , bcc, msgToSend,null);
		envio=mailUtil.enviarMail(msgToSend,paraEmail);
		
		if(!envio)
			{
//				Messagebox.show("Falló el envio de correo.","Attention",Messagebox.OK,Messagebox.INFORMATION);
			System.out.println("No se envió");
			}
		else{
			
//			Messagebox.show("Una notificación fué enviada a su correo..","Attention",Messagebox.OK,Messagebox.INFORMATION);
			System.out.println("se envió");
//			winModel.detach();
		}
			
		
		
	}

}
