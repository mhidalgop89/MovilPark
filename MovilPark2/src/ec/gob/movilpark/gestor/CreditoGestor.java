package ec.gob.movilpark.gestor;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.EspacioPorParquimetro;
import ec.gob.movilpark.dto.NotaCredito;
import ec.gob.movilpark.dto.Pago;
import ec.gob.movilpark.dto.Parametros;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Tramite;
import ec.gob.movilpark.util.Mail;



public class CreditoGestor implements Runnable {
	
	private Double saldo=0.0;
	private Double valorPagar=0.0;
	private List<NotaCredito> lsnotaCredito;
	private NotaCredito objNotaCredito;
	private boolean bloqueaConfPago=false;
	private java.util.Date fechaActual = new java.util.Date();
	private java.util.Date fhasta;
	private String fechaActualLocal;
	private String horaDesde;
	private String horaHasta;
	
	/*
    "Inicio:  <b>"+objSession.getTramite().getFechaInicial().toLocaleString()+"</b><br>"+
    "Fin:  <b>"+objSession.getTramite().getFechaFinal().toLocaleString()+"</b><br>"+
    "Placa:  <b>"+objSession.getVehiculo().getPlaca()+"</b><br>"+
    "Ciudad:  <b>"+objSession.getCiudad().getNombre()+"</b><br>"+ 
    "Area:  <b>"+objSession.getArea().getNombre()+"</b><br>"+ 
    "Minutos:  <b>"+objSession.getTramite().getMinutos()+"</b><br><br>";
	*/
	
	////////////////////mail
	
	private String destinatario;
	private String fechaInicial;
	private String fechaFinal;
	private String placa;
	private String ciudad;
	private String area;
	private String minutos;
	
	
	public String getHoraDesde() {
		return horaDesde;
	}


	public void setHoraDesde(String horaDesde) {
		this.horaDesde = horaDesde;
	}


	public String getHoraHasta() {
		return horaHasta;
	}


	public void setHoraHasta(String horaHasta) {
		this.horaHasta = horaHasta;
	}


	Window win;
	private Session objSession;



	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		
		System.out.println("Regisro1");
		objSession=(Session)Executions.getCurrent().getSession().getAttribute("session");
		System.out.println("objSession"+objSession);
		if(objSession==null)
			{
			Executions.getCurrent().sendRedirect("../");
				return;
			}
		
		//cargarCredito();
		saldo = objSession.getSaldo();
		valorPagar=objSession.getObjPago().getValorTotal();
		
		if(saldo<valorPagar)
			bloqueaConfPago=true;
	}
	
	
	@NotifyChange("*")
	public void cargarCredito(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		saldo=0.0;
		map = listasDao.consultaNotasCreditosParam(objSession.getUsuario().getUsuarioId());
		
		if(map.get("error") == null){
			lsnotaCredito=(List<NotaCredito> ) map.get("listaNotaCredito");
			for(int i=0;i<lsnotaCredito.size();i++){
				saldo=saldo+lsnotaCredito.get(i).getSaldo();
			}
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public java.util.Date sumaMinutos(java.util.Date fecha,int minsParam){
		
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); // Configuramos la fecha que se recibe
	      calendar.add(Calendar.MINUTE, minsParam);  // numero de horas a añadir, o restar en caso de horas<0

	      return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
	}
	
	
	@NotifyChange("*")
	public void calculaLimite(){
		String minutosFecActual="";
		String minutosHoraActual="";
		fechaActual = new java.util.Date();
		if(objSession.isVehiculoPoseeTramitePrevio()){
			if(objSession.getListaTramite()!=null)
				for(int i=0;i<objSession.getListaTramite().size();i++)
					if(objSession.getListaTramite().get(i).getVehiculoId()==objSession.getVehiculo().getVehiculoId()&&
					   objSession.getListaTramite().get(i).getIdAreas()==objSession.getArea().getIdAreas()){
						if(objSession.getListaTramite().get(i).getFechaFinal().after(fechaActual))
							fechaActual = objSession.getListaTramite().get(i).getFechaFinal();
						
					}
			
			
		}
		else
			fechaActual = new java.util.Date();
		
		
		
		fechaActualLocal = fechaActual.toLocaleString();
		java.util.Date hactualutil = sumaMinutos(fechaActual, objSession.getTramite().getMinutos());
		
		minutosFecActual=String.valueOf(fechaActual.getMinutes());
		minutosHoraActual = String.valueOf(hactualutil.getMinutes());
		
		minutosFecActual =(minutosFecActual.length()==1)?"0"+minutosFecActual:minutosFecActual;
		minutosHoraActual = (minutosHoraActual .length()==1)?"0"+minutosHoraActual :minutosHoraActual ;
		
		horaDesde= fechaActual.getHours()+":"+minutosFecActual;//fechaActual.getMinutes();
		horaHasta= hactualutil.getHours()+":"+minutosHoraActual;//hactualutil.getMinutes();
		fhasta = hactualutil;
		System.out.println("calcula limite"+fechaActual+" -- "+hactualutil+" --minuto: "+objSession.getTramite().getMinutos());
		
		
	}
	
	
	
	@Command 
	public synchronized void confirmaPago2(){
		
		System.out.println("confirma pago 2");
		Tramite tramite =objSession.getTramite();
		tramite.setIdAreas(objSession.getArea().getIdAreas());
		Pago pago = objSession.getObjPago();
		Double valorMin= ((objSession.getParquimetro()!=null)?objSession.getParquimetro().getValorMinimo():objSession.getArea().getValorMinimo());
		if(saldo<valorPagar)
			pago.setValorPagado(saldo);
		else
			pago.setValorPagado(valorPagar);
		
		calculaLimite();
		tramite.setFechaInicial(fechaActual);
		tramite.setFechaFinal(fhasta);
		objSession.setTramite(tramite);
		
		Listas listaDao = new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listaDao.realizaTransaccionPago(objSession.getUsuario().getUsuarioId(), valorPagar, objSession.getUsuario().getUsuario(),tramite,pago); 
		
		if(map.get("error") != null){
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}else
			Messagebox.show("Su transacción se ha realizado satisfactoriamente, se le recuerda que el costo adicional por cada transacción es de $0.15, y la tarifa minima es de $"+valorMin
					+"\n Ciudad: "+objSession.getCiudad().getNombre()+"\n Area: "+objSession.getArea().getNombre(), "Atención", Messagebox.OK, Messagebox.INFORMATION);

		cargarCredito();
		objSession.setSaldo(saldo);
		objSession.setObjPago(pago);		
		////////////////////mail
		
		
		fechaInicial=objSession.getTramite().getFechaInicial().toLocaleString();
		fechaFinal=objSession.getTramite().getFechaFinal().toLocaleString();
		placa=objSession.getVehiculo().getPlaca();
		ciudad=objSession.getCiudad().getNombre();
		area=objSession.getArea().getNombre();
		minutos= String.valueOf(objSession.getTramite().getMinutos());
		destinatario  =objSession.getUsuario().getUsuario();
		
		CreditoGestor credito = new CreditoGestor();
		credito.setFechaFinal(fechaFinal);
		credito.setFechaInicial(fechaInicial);
		credito.setPlaca(placa);
		credito.setCiudad(ciudad);
		credito.setArea(area);
		credito.setMinutos(minutos);
		credito.setDestinatario(destinatario);
		Runnable proceso1 = credito;
		new Thread(proceso1).start();
		
//		(new Thread(new CreditoGestor())).start();
//		enviarCorreo();
		Messagebox.show("Una notificación fué enviada a su correo.",
				"Attention",
				Messagebox.OK,Messagebox.INFORMATION);
		System.out.println("thread2");
		Executions.getCurrent().getSession().setAttribute("session", objSession);
		Executions.getCurrent().sendRedirect("/Aplicacion/pagoCompleto.zul");
		
	}
	
	
	
	
	
	
	
	
	
	
	@Command
	public void enviarCorreo(){
				
		boolean envio=true;
		String subject="Parqueo Positivo";
System.out.println("destinatario:--->"+destinatario+"--"+this.destinatario);
		String to[]= new String[1];
		to[0]=destinatario;
//		to[0]=objSession.getUsuario().getUsuario();

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
                "Usuario:   <b>"+destinatario /*objSession.getUsuario().getUsuario()*/ +"</b><br>"+
                "Inicio:  <b>"+fechaInicial/*objSession.getTramite().getFechaInicial().toLocaleString()*/+"</b><br>"+
                "Fin:  <b>"+fechaFinal/*objSession.getTramite().getFechaFinal().toLocaleString()*/+"</b><br>"+
                "Placa:  <b>"+placa/*objSession.getVehiculo().getPlaca()*/+"</b><br>"+
                "Ciudad:  <b>"+ciudad/*objSession.getCiudad().getNombre()*/+"</b><br>"+ 
                "Area:  <b>"+area/*objSession.getArea().getNombre()*/+"</b><br>"+ 
                /*"Numero(Nombre) del Parquimetro:  <b>"+objSession.getParquimetro().getNombre()+"</b><br>"+
                "Numero de espacio:  <b>"+objSession.getEspacioPorParquimetro().getNumeroEspacio()+"</b><br>"+*/
                "Minutos:  <b>"+minutos/*objSession.getTramite().getMinutos()*/+"</b><br><br>";
                
		msgToSend=msgToSend+"<br><br>Saludos Cordiales,<br>"+
				"Parqueo Positivo"+
                "</div>"+
                "</div>";
		
		Mail mailUtil= new Mail();
//		envio=mailUtil.send("sales@allparts.expert", to, subject,cc , bcc, msgToSend,null);
		envio=mailUtil.enviarMail(msgToSend,destinatario/*objSession.getUsuario().getUsuario()*/);
		
		/*if(envio)
			Messagebox.show("Una notificación fué enviada a su correo.",
					"Attention",
					Messagebox.OK,Messagebox.INFORMATION);
		System.out.println("thread1");*/
		
//		if(!envio)
//			Messagebox.show("Falló el envio de correo.",
//					"Attention",
//					Messagebox.OK,Messagebox.INFORMATION);
//		else{
//			
//			Messagebox.show("Una notificación fué enviada a su correo..",
//					"Attention",
//					Messagebox.OK,Messagebox.INFORMATION);
//		}
			
		
		
	}
	
	
	
	
	
	
	
	
	
	@Command
	public void confirmaPago(){
//		esto se debe de reemplazar por sp en BD
		Listas listaDao = new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		NotaCredito objNotaCredito;
		Double valorPagarAux= valorPagar;
		boolean ocurrioError=false;
		for(int i=0;i<lsnotaCredito.size()&&!ocurrioError;i++){
			if(lsnotaCredito.get(i).getSaldo()>valorPagarAux){
				objNotaCredito=lsnotaCredito.get(i);
				objNotaCredito.setDescuento(objNotaCredito.getDescuento()+ valorPagarAux);
				objNotaCredito.setSaldo(objNotaCredito.getSaldo()-valorPagarAux);
				valorPagarAux=0.0;
				objNotaCredito.setUsuarioActualiza(objSession.getUsuario().getUsuario());
				objNotaCredito.setFechaActualiza(new Date());
				map = listaDao.editaNotaCredito(objNotaCredito);
			}else if(lsnotaCredito.get(i).getSaldo()==valorPagarAux){
				objNotaCredito=lsnotaCredito.get(i);
				objNotaCredito.setDescuento(objNotaCredito.getDescuento()+ valorPagarAux);
				objNotaCredito.setSaldo(objNotaCredito.getSaldo()-valorPagarAux);
				objNotaCredito.setEstado("I");
				valorPagarAux=0.0;
				objNotaCredito.setUsuarioActualiza(objSession.getUsuario().getUsuario());
				objNotaCredito.setFechaActualiza(new Date());
				map = listaDao.editaNotaCredito(objNotaCredito);
			}else if(lsnotaCredito.get(i).getSaldo()<valorPagarAux){
				objNotaCredito=lsnotaCredito.get(i);
				//si el saldo es menor se descuenta solo lo que hay de saldo
				objNotaCredito.setDescuento(objNotaCredito.getDescuento()+ objNotaCredito.getSaldo());
				//el valor a pagar solo se reduce a vp=vp'saldo
				valorPagarAux= valorPagarAux- objNotaCredito.getSaldo();
				//debido a que el saldo es menor, se queda con cero
				objNotaCredito.setSaldo(0.0);
				objNotaCredito.setEstado("I");
				objNotaCredito.setUsuarioActualiza(objSession.getUsuario().getUsuario());
				objNotaCredito.setFechaActualiza(new Date());
				map = listaDao.editaNotaCredito(objNotaCredito);
				
			}
			if(map.get("error") != null){
				ocurrioError= true;
			}
			
		}
		
		
		if(!ocurrioError)
		valorPagar=valorPagarAux;
		else
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		
		
			
	}
	
	@Command
	public void onAtras(){
		Executions.getCurrent().sendRedirect("/Aplicacion/confirmaPago.zul");
	}


	public Double getSaldo() {
		return saldo;
	}


	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}


	public Double getValorPagar() {
		return valorPagar;
	}


	public void setValorPagar(Double valorPagar) {
		this.valorPagar = valorPagar;
	}


	public List<NotaCredito> getLsnotaCredito() {
		return lsnotaCredito;
	}


	public void setLsnotaCredito(List<NotaCredito> lsnotaCredito) {
		this.lsnotaCredito = lsnotaCredito;
	}


	public NotaCredito getObjNotaCredito() {
		return objNotaCredito;
	}


	public void setObjNotaCredito(NotaCredito objNotaCredito) {
		this.objNotaCredito = objNotaCredito;
	}


	public boolean isBloqueaConfPago() {
		return bloqueaConfPago;
	}


	public void setBloqueaConfPago(boolean bloqueaConfPago) {
		this.bloqueaConfPago = bloqueaConfPago;
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


	public java.util.Date getFechaActual() {
		return fechaActual;
	}


	public void setFechaActual(java.util.Date fechaActual) {
		this.fechaActual = fechaActual;
	}


	public java.util.Date getFhasta() {
		return fhasta;
	}


	public void setFhasta(java.util.Date fhasta) {
		this.fhasta = fhasta;
	}


	public String getFechaActualLocal() {
		return fechaActualLocal;
	}


	public void setFechaActualLocal(String fechaActualLocal) {
		this.fechaActualLocal = fechaActualLocal;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		enviarCorreo();
		
	}


	public String getDestinatario() {
		return destinatario;
	}


	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}


	public String getFechaInicial() {
		return fechaInicial;
	}


	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}


	public String getFechaFinal() {
		return fechaFinal;
	}


	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}


	public String getPlaca() {
		return placa;
	}


	public void setPlaca(String placa) {
		this.placa = placa;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getMinutos() {
		return minutos;
	}


	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}



}
