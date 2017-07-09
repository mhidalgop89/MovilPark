package ec.gob.movilpark.gestor;

import java.util.Date;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dto.Session;

public class ConfirmaPagoGestor {

	private Session objSession;
	private String placa;
	private String nombreParquimetro;
	private int numeroParquimetro;
	private String espacioParq;
	private String fecha;
	private String horaInicio;
	private String horaFin;
	private Double valorSubTotal;
	private Double valorTotal;
	private int numeroHoras=1;
	private static final Double TASA_SM=0.15;
	
	private boolean nombreParquimetroVisible=false;
	private boolean espacioParquimetroVisible=false;
	Window win;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		String minutosInicio="";
		String minutosFin="";
		
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null)
			{
			Executions.getCurrent().sendRedirect("../");
				return;
			}
		
		
		placa= objSession.getVehiculo().getPlaca();
		if(objSession.getParquimetro()!=null){
			nombreParquimetro = objSession.getParquimetro().getNombre();
			nombreParquimetroVisible=true;
		}
			
		
		if(objSession.getEspacioPorParquimetro()!=null){
			numeroParquimetro=objSession.getEspacioPorParquimetro().getNumeroEspacio();
			espacioParq = objSession.getEspacioPorParquimetro().getEspacio();
			espacioParquimetroVisible=true;
		}
		
		fecha=objSession.getTramite().getFechaTramite().toLocaleString();
		
		
		minutosInicio = String.valueOf(objSession.getTramite().getFechaInicial().getMinutes());
		minutosFin = String.valueOf(objSession.getTramite().getFechaFinal().getMinutes());
		
		minutosInicio = (minutosInicio.length()==1)?"0"+minutosInicio:minutosInicio;
		minutosFin = (minutosFin.length()==1)?"0"+minutosFin:minutosFin;
		
		horaInicio=objSession.getTramite().getFechaInicial().getHours()+":"+minutosInicio;
		horaFin=objSession.getTramite().getFechaFinal().getHours()+":"+minutosFin;
							
//		valorTotal=objSession.getObjPago().getValorTotal();
//		valorSubTotal = new Double(valorTotal);
//		valorTotal = valorTotal+TASA_SM;
		if((objSession.getTramite().getMinutos()/60)<1)
			numeroHoras=1;
		else
			numeroHoras=(objSession.getTramite().getMinutos()/60);
		
		Clients.evalJavaScript("setAttPaypal("+numeroHoras+","+objSession.getObjPago().getValorTotal()+")");
		System.out.println("valorTotal"+objSession.getObjPago().getValorTotal()+" -->"+"setAttPaypal("+numeroHoras+","+objSession.getObjPago().getValorTotal()+")");
	}
	
	@Command
	public void onAtras(){
		Executions.getCurrent().sendRedirect("/Aplicacion/seleccionaTarifa.zul");
	}
	
	@Command
	public void onConfirmarPago(){
		System.out.println("confirmar pago");
		
		Messagebox.show("Pago realizado con exito ", "Atención",
				Messagebox.OK, Messagebox.INFORMATION);
		
		Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
		
	}
	
	@Command
	public void credito(){
		
		objSession.setMetodoPago("NC");
		//objSession.getObjPago().setValorTotal(valorTotal);
		Executions.getCurrent().getSession().setAttribute("session", objSession);
		Executions.getCurrent().sendRedirect("/Aplicacion/credito.zul");
		
	}

	public Session getObjSession() {
		return objSession;
	}

	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getNombreParquimetro() {
		return nombreParquimetro;
	}

	public void setNombreParquimetro(String nombreParquimetro) {
		this.nombreParquimetro = nombreParquimetro;
	}

	public int getNumeroParquimetro() {
		return numeroParquimetro;
	}

	public void setNumeroParquimetro(int numeroParquimetro) {
		this.numeroParquimetro = numeroParquimetro;
	}

	

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getEspacioParq() {
		return espacioParq;
	}

	public void setEspacioParq(String espacioParq) {
		this.espacioParq = espacioParq;
	}

	public int getNumeroHoras() {
		return numeroHoras;
	}

	public void setNumeroHoras(int numeroHoras) {
		this.numeroHoras = numeroHoras;
	}

	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}

	public boolean isNombreParquimetroVisible() {
		return nombreParquimetroVisible;
	}

	public void setNombreParquimetroVisible(boolean nombreParquimetroVisible) {
		this.nombreParquimetroVisible = nombreParquimetroVisible;
	}

	public boolean isEspacioParquimetroVisible() {
		return espacioParquimetroVisible;
	}

	public void setEspacioParquimetroVisible(boolean espacioParquimetroVisible) {
		this.espacioParquimetroVisible = espacioParquimetroVisible;
	}

	public Double getValorSubTotal() {
		return valorSubTotal;
	}

	public void setValorSubTotal(Double valorSubTotal) {
		this.valorSubTotal = valorSubTotal;
	}

	public static Double getTasaSm() {
		return TASA_SM;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
