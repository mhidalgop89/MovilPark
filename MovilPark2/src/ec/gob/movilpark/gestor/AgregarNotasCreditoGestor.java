package ec.gob.movilpark.gestor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dao.LlamaUsuario;
import ec.gob.movilpark.dto.NotaCredito;
import ec.gob.movilpark.dto.NotaCreditoParam;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Usuario;
import ec.gob.movilpark.util.Mail;

public class AgregarNotasCreditoGestor {

	private List<NotaCreditoParam> listaNotaCreditoParam= new ArrayList<NotaCreditoParam>();
	private NotaCreditoParam objNcParam;
	
	private List<Usuario>lsUsuarios;
	private NotaCredito objNotaCredito;
	private NotaCredito objNotaCreditoParam;
	private Usuario objUsuario;
	private String estado;
	public boolean disabled=false;
	private Session objSession;
	Window win;

	@Init
	@NotifyChange("*")
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("notaCredito") NotaCredito objNotaCreditoParam)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
				return;
			}
		consultaUsuarios();
		if(objNotaCreditoParam!=null){
			this.objNotaCreditoParam=objNotaCreditoParam;
			this.objNotaCredito=objNotaCreditoParam;
//			habilitaUsuario=true;
			if("A".equals(objNotaCreditoParam.getEstado()))
				estado="A";
			else
				estado="I";
			
			for(int i=0;i<lsUsuarios.size();i++)
				if(objNotaCredito.getUsuarioId()==lsUsuarios.get(i).getUsuarioId())
					objUsuario=lsUsuarios.get(i);
			
		}
		else{
			this.objNotaCreditoParam=objNotaCreditoParam;
			objNotaCredito= new NotaCredito();
			estado="A";
			disabled=true;

			
		}
		cargarCreditoParam();
	}
	
	
	public void cargarCreditoParam(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		
		map = listasDao.consultaNotasCreditosParametros();
		
		if(map.get("error") == null){
			listaNotaCreditoParam=(List<NotaCreditoParam> ) map.get("listaNotaCreditoParam");
				if(listaNotaCreditoParam!=null)
					if(listaNotaCreditoParam.size()>0)
						objNcParam=listaNotaCreditoParam.get(0);
					
						
					
		}else
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		
	}
	
	
	public void consultaUsuarios(){
		LlamaUsuario usuariosDao= new LlamaUsuario();
		Map<String,Object> map= new HashMap<String , Object>();
		map = usuariosDao.consultaUsuarioNc();
		
		if(map.get("error") == null){
			lsUsuarios=(List<Usuario>) map.get("usuarios");
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
	}
	
	@NotifyChange("objNotaCredito")
	@Command
	public void actualizaSaldo(){
		
		if(objNotaCredito.getValor()==null){
			objNotaCredito.setValor(0.0);
		}
		if(objNotaCredito.getDescuento()==null){
			objNotaCredito.setDescuento(0.0);
		}
		
		Double saldoActual=objNotaCredito.getValor()-objNotaCredito.getDescuento();
		objNotaCredito.setSaldo(objNotaCredito.getValor()-objNotaCredito.getDescuento());
	}
	
	@Command
	public void guardarNotaCredito(){
		
		System.out.println("inicia guardarUsuario");
		
		
		if(objNotaCredito.getValor()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		

		
		if(objNotaCredito.getNumeroFactura()==null){
			Messagebox.show("Campo Número de Factura obligatorio.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objUsuario==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}

		if(objNcParam==null){
			Messagebox.show("Primero debe ingresar los parametros de las Notas de Crédito", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objNotaCredito.getValor()>objNcParam.getValormaximo()){
			Messagebox.show("Campo valor no puede ser mayor a "+objNcParam.getValormaximo(), "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}

		if(objNotaCredito.getValor()<objNcParam.getValorMinimo()){
			Messagebox.show("Campo valor no puede ser menor a "+objNcParam.getValorMinimo(), "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		
		
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listaDao= new Listas();
		boolean existe=false;
				
		if(this.objNotaCreditoParam==null){
			objNotaCredito.setUsuarioIngresa(objSession.getUsuario().getUsuario());
			objNotaCredito.setFechaIngresa(new Date());
			objNotaCredito.setUsuarioId(objUsuario.getUsuarioId());
			//objNotaCredito.setFhasta(sumaAnios(objNotaCredito.getFechaIngresa(), 1));
			objNotaCredito.setFhasta(sumaAnios(objNotaCredito.getFechaIngresa(), (objNcParam.getTiempoMaximoVigencia()/12)));
			System.out.println("ingresa");
			map = listaDao.insertaNotaCredito(objNotaCredito);
		}
		else{
			objNotaCredito.setUsuarioActualiza(objSession.getUsuario().getUsuario());
			objNotaCredito.setFechaActualiza(new Date());
			objNotaCredito.setEstado(estado);
			objNotaCredito.setSaldo(objNotaCredito.getValor()-objNotaCredito.getDescuento());
			objNotaCredito.setUsuarioId(objUsuario.getUsuarioId());
			
			System.out.println("actualiza"+objUsuario.getUsuarioId());
			map = listaDao.editaNotaCredito(objNotaCredito);
		}
			
		
		if(map.get("error") == null){
			Messagebox.show("Almacenado con exito.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			enviarCorreo();
		}
		else
			{
				Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		
		win.detach();	
		
	}
	
	
	public java.util.Date sumaAnios(java.util.Date fecha,int aniosParam){
		
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); // Configuramos la fecha que se recibe
	      calendar.add(Calendar.YEAR, aniosParam);  // numero de horas a añadir, o restar en caso de horas<0

	      return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
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
                "<img src='http://13.82.51.231:8080/MovilPark/images/main-logo-dark1.png' width='267px' height='80px' /><br><br>"+
                
                "<br><br><b>Nota de Crédito:  </b><br><br>"+
                "Valor de la Recarga:   <b>"+"$"+objNotaCredito.getValor() +"</b><br><br>";
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
	
	
	@Command 
	public void salir(){
		
		win.detach();
	}

	public NotaCredito getObjNotaCredito() {
		return objNotaCredito;
	}

	public void setObjNotaCredito(NotaCredito objNotaCredito) {
		this.objNotaCredito = objNotaCredito;
	}

	public NotaCredito getObjNotaCreditoParam() {
		return objNotaCreditoParam;
	}

	public void setObjNotaCreditoParam(NotaCredito objNotaCreditoParam) {
		this.objNotaCreditoParam = objNotaCreditoParam;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
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

	public Usuario getObjUsuario() {
		return objUsuario;
	}

	public void setObjUsuario(Usuario objUsuario) {
		this.objUsuario = objUsuario;
	}


	public List<Usuario> getLsUsuarios() {
		return lsUsuarios;
	}


	public void setLsUsuarios(List<Usuario> lsUsuarios) {
		this.lsUsuarios = lsUsuarios;
	}
}
