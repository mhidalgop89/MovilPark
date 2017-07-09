package ec.gob.movilpark.gestor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.Areas;
import ec.gob.movilpark.dto.EspacioPorParquimetro;
import ec.gob.movilpark.dto.Pago;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.filtros.Filtros;
import ec.gob.movilpark.util.Redondear;

public class ConsultaPagosPorFecha {
	
	private List<Pago> listaPagos= new ArrayList<Pago>();
	
	private Double valorPagadoTotal=0.0;
	private List<Areas> listaAreas;
	private Areas objArea;
	private int cantidadMinutosTotales=0;
	private String placa;
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	private Pago objPago;
	private Date fechaInicio= new Date();
	private Date fechaFin= new Date();
	private Session objSession;
	Window win;

	@Init
	@NotifyChange("*")
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
				return;
			}
		fillAreas();
		
	}
	
	@NotifyChange({"listaPagos","valorPagadoTotal","cantidadMinutosTotales"})
	@Command
	public void consultaPorFecha(){
		if(fechaInicio==null)
			return;
		if(fechaFin==null)
			return;
		if(fechaFin.before(fechaInicio)){
			Messagebox.show("Fecha final debe ser posterior a fecha inicial", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
			
		
		String placaParam=((placa==null)?"0":placa);
		if(placa==null)
			placaParam="0";
		if(placa!=null)
		if(placa.isEmpty())
			placaParam="0";
		
		if(placa=="")
			placaParam="0";
		
		cantidadMinutosTotales=0;
		valorPagadoTotal=0.0;
		
		System.out.println("consultaParquimetroPorUso!!!");
		long minutos=0;
		Listas listasDao= new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listasDao.consultaPagoPorFecha(fechaInicio, fechaFin,((objArea==null)?0:objArea.getIdAreas()), placaParam);
		Redondear red= new Redondear();
		if(map.get("error") == null){
			listaPagos=(List<Pago>) map.get("listaPago");
			for(int i=0;i<listaPagos.size();i++){
				valorPagadoTotal=valorPagadoTotal+listaPagos.get(i).getValorPagado();
				cantidadMinutosTotales = cantidadMinutosTotales + listaPagos.get(i).getMinutos(); 
			}
			valorPagadoTotal=red.Redondear2(valorPagadoTotal);
		}
		else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}
	}
	
	
	@NotifyChange("listaAreas")
	@Command
	public void fillAreas(){
		objArea=null;
		listaAreas=null;
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
//		map = listasDao.obtieneAreas(objCiudad.getCiuCodigo(), objProvincia.getProCodigo());
		map = listasDao.obtieneAreas("0", "0");
		
			//listaAreas= new ArrayList<Areas>();
		
		if(map.get("error") == null){
			
			listaAreas=(List<Areas>) map.get("areas");
			//objArea= listaAreas.get(0);		
			//fillParquimetro();
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<Pago> getListaPagos() {
		return listaPagos;
	}

	public void setListaPagos(List<Pago> listaPagos) {
		this.listaPagos = listaPagos;
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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	public Pago getObjPago() {
		return objPago;
	}


	public void setObjPago(Pago objPago) {
		this.objPago = objPago;
	}

	public Double getValorPagadoTotal() {
		return valorPagadoTotal;
	}

	public void setValorPagadoTotal(Double valorPagadoTotal) {
		this.valorPagadoTotal = valorPagadoTotal;
	}

	public int getCantidadMinutosTotales() {
		return cantidadMinutosTotales;
	}

	public void setCantidadMinutosTotales(int cantidadMinutosTotales) {
		this.cantidadMinutosTotales = cantidadMinutosTotales;
	}

	public List<Areas> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(List<Areas> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public Areas getObjArea() {
		return objArea;
	}

	public void setObjArea(Areas objArea) {
		this.objArea = objArea;
	}

}
