package ec.gob.movilpark.gestor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.functors.ExceptionClosure;
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
import ec.gob.movilpark.dto.Pago;
import ec.gob.movilpark.dto.Parametros;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Tramite;
import ec.gob.movilpark.util.Redondear;

public class SeleccionaTarifaGestor {
	
	Window win;
	//La posicion actual tambien refleja los minutos del vehiculo expresados en el slider
	private int posicionActual=60;//parametrizable
	private int hora=0;//parametrizable
	private int minuto=0;//parametrizable
	private Double centavoPorMinuto=1.0;//parametrizable--
	private int unidadDolar=0;//parametrizable
	private int centavos=0;//parametrizable
	
	private int tiempoMaxEnMinutos=1440;//1 dia, parametrizable--
	private int tiempoMinEnMinutos=0;
	
	private double maximoDolares=3.65;//parametrizable--
	private boolean existeLimiteDolares=true;//parametrizable
	private double valorMinimoDolares=25;//parametrizable--
	private Tramite objTramite;
	private Pago objPago;
	
	private Parametros objParam;
	private static final Double TASA_SM=0.15;
	
	private String horaDesde;
	private String horaHasta;
	private boolean noPoseeTramitePrevio=true;
	private boolean vehiculoPoseeTramitePrevio=false;
	private java.util.Date fechaActual = new java.util.Date();
	private String fechaActualLocal;
	private java.util.Date fechaFinal = new java.util.Date();
	private Date fhasta;
	private String constraintSpinPosicion="";
	////////////////////
	private Double minValorParquimetro;
	private Double maxValorParquimetro;
	private Double valorHoraParquimetro;
	private List<Tramite> listaTramite;
	private long horas=0;



	private Session objSession;
	

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
		
		System.out.println("SeleccionaTarifaGestor");
		cargar();
		
		if(objSession.getTramitePrevio()==null){
			
				constraintSpinPosicion="min "+tiempoMinEnMinutos+" max "+tiempoMaxEnMinutos+": Entre "+tiempoMinEnMinutos+" to "+tiempoMaxEnMinutos;
				noPoseeTramitePrevio=true;
		}
		else{
			
			if(objSession.getListaTramite()!=null)
				for(int i=0;i<objSession.getListaTramite().size()&&!vehiculoPoseeTramitePrevio;i++){
					System.out.println("objSession.getVehiculo().getVehiculoId():"+objSession.getVehiculo().getVehiculoId()+
							"-objSession.getListaTramite().get(i).getVehiculoId():"+objSession.getListaTramite().get(i).getVehiculoId());
					if(objSession.getVehiculo().getVehiculoId()== objSession.getListaTramite().get(i).getVehiculoId())
						vehiculoPoseeTramitePrevio=true;
					
					}
			
			boolean poseeTramitePrevioVigente=false;
			Tramite objTramite1= new Tramite();
			objTramite1.setFechaFinal(new Date());
			
			if(vehiculoPoseeTramitePrevio){
				System.out.println("aqui4: ");
				for(int i=0;i<objSession.getListaTramite().size();i++){
					System.out.println("aqui5: "+objSession.getListaTramite().get(i).getFechaFinal()+"-"+objTramite1.getFechaFinal());
					if(objSession.getListaTramite().get(i).getIdAreas()==objSession.getArea().getIdAreas())
						if(objSession.getListaTramite().get(i).getPlaca()==objSession.getVehiculo().getPlaca())
						if(objSession.getListaTramite().get(i).getFechaFinal().after(objTramite1.getFechaFinal())){
							System.out.println("aqui6");
							objTramite1=objSession.getListaTramite().get(i);
							poseeTramitePrevioVigente=true;
						}
					
				}
				if(poseeTramitePrevioVigente){
					System.out.println("aqui1: ");
					
					constraintSpinPosicion="min "+3+" max "+tiempoMaxEnMinutos+": Entre "+1+" to "+tiempoMaxEnMinutos;
				}
				else{
					System.out.println("aqui2: ");
					constraintSpinPosicion="min "+tiempoMinEnMinutos+" max "+tiempoMaxEnMinutos+": Entre "+1+" to "+tiempoMaxEnMinutos;
				}
					
				
				noPoseeTramitePrevio=false;
			}
			else{
				System.out.println("aqui3: ");
				constraintSpinPosicion="min "+tiempoMinEnMinutos+" max "+tiempoMaxEnMinutos+": Entre "+tiempoMinEnMinutos+" to "+tiempoMaxEnMinutos;
				noPoseeTramitePrevio=true;
			}
			System.out.println("noPoseeTramitePrevio:"+noPoseeTramitePrevio+"--vehiculoPoseeTramitePrevio:"+vehiculoPoseeTramitePrevio);
		}
		objSession.setVehiculoPoseeTramitePrevio(vehiculoPoseeTramitePrevio);
		System.out.println("constraintSpinPosicion:"+constraintSpinPosicion);
		calculaHoras();
		//calculaLimite();
		
		

//		System.out.println("hactual: "+hactualutil+" hours: "+hactualutil.getHours()+"..tolocal"+ hactualutil.toLocaleString());
//		horaDesde=hactualutil.toString()+":"+hactualutil.getMinutes();
//		horaHasta=(hactualutil.getHours()+hora)+":"+();
		
	}
	
	
	public long diferenciaEnDias2(Date fechaMayor, Date fechaMenor) {
		long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();
		long dias = diferenciaEn_ms / (1000 * 60 );
		return  dias;
		}
	
	public void cargaTramite(){
		Listas listaDao = new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listaDao.consultaTramiteXplacaXusuario(objSession.getUsuario().getUsuarioId(),objSession.getVehiculo().getPlaca());
		System.out.println("usuario y placa: "+objSession.getUsuario().getUsuarioId()+"--"+objSession.getVehiculo().getPlaca());
		listaTramite= new ArrayList<Tramite>();
		horas=0;
		if(map.get("error") == null){
			listaTramite=(List<Tramite>) map.get("listTramite");
			if(listaTramite!=null)
				if(listaTramite.size()>0)
					for(int i=0;i<listaTramite.size();i++){
						if(listaTramite.get(i).getIdAreas()==objSession.getArea().getIdAreas()){
							System.out.println("listaTramite.get(i).getFechaFinal():-->"+listaTramite.get(i).getFechaFinal()+"-->"+
									diferenciaEnDias2(listaTramite.get(i).getFechaFinal() ,new java.util.Date()));
							if(diferenciaEnDias2(listaTramite.get(i).getFechaFinal() ,new java.util.Date())>horas)
								horas = diferenciaEnDias2(listaTramite.get(i).getFechaFinal() ,new java.util.Date());
						}
							
					}
					
//			for(int i=0;i<listaTramite.size();i++)
//				horas = horas+diferenciaEnDias2(listaTramite.get(i).getFechaFinal() ,new java.util.Date());
			
		}else
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		System.out.println("----> horas: "+horas);
		
	}
	
	
	public void calculaTiempoMaximoSegunValor(){
		
		if(objSession.getParquimetro()==null&&objSession.getArea()==null)
			return;
		
		maxValorParquimetro=(objSession.getParquimetro()!=null)?objSession.getParquimetro().getValorMaximo():objSession.getArea().getValorMaximo();
		valorHoraParquimetro=(objSession.getParquimetro()!=null)?objSession.getParquimetro().getValorPorHora():objSession.getArea().getValorPorHora();
		
		
		Double tiempoMax=0.0;
		tiempoMax=(60*maxValorParquimetro)/(valorHoraParquimetro);
		System.out.println("tiempoMax sin redondear: "+tiempoMax);
		Redondear red= new Redondear();
		tiempoMax=red.Redondear2(tiempoMax);
		
		tiempoMaxEnMinutos=tiempoMax.intValue();
	}
	
	public void calculaTiempoMinimoSegunValor(){
		if(objSession.getParquimetro()==null&&objSession.getArea()==null)
			return;
		
		minValorParquimetro=(objSession.getParquimetro()!=null)?objSession.getParquimetro().getValorMinimo():objSession.getArea().getValorMinimo();
		valorHoraParquimetro=(objSession.getParquimetro()!=null)?objSession.getParquimetro().getValorPorHora():objSession.getArea().getValorPorHora();
		Double tiempoMin=0.0;
		tiempoMin=(60*minValorParquimetro)/(valorHoraParquimetro);
		System.out.println("tiempoMin="+tiempoMin+"=60*"+minValorParquimetro+"/"+valorHoraParquimetro);
		Redondear red= new Redondear();
		tiempoMin=red.Redondear2(tiempoMin);
		tiempoMinEnMinutos = tiempoMin.intValue();
		//etiquetaValorPorMinimo=tiempoMin.toString()+" minutos";
		//System.out.println("etiquetaValorPorMinimo: "+etiquetaValorPorMinimo);
	}
	
	@NotifyChange("*")
	public void cargar(){
		
//		Map<String,Object> map= new HashMap<String , Object>();
//		Listas listasDao= new Listas();
		
//		map = listasDao.obtieneParametros();
		
//		if(map.get("error") == null){
			
//			objParam=(Parametros) map.get("parametro");
//			if(objParam!=null){
				//parametros de la tabla de parametros
//				centavoPorMinuto= (int) (objParam.getValorPorMinuto()*100);
//				tiempoMaxEnMinutos= objParam.getTiempoMaxEnMinutos();
//				maximoDolares=objParam.getMaximoDolares();
//				valorMinimoDolares = objParam.getValorMinimoDolares();
				
				//parametros de cada parquimetro, exepto tiempo maximo en parquimetro
//				centavoPorMinuto= (int) (objSession.getParquimetro().getValorPorHora()*100/60);
				if(objSession.getParquimetro()!=null){
					centavoPorMinuto=  (objSession.getParquimetro().getValorPorHora()*100/60);
					maximoDolares=objSession.getParquimetro().getValorMaximo();
					valorMinimoDolares = objSession.getParquimetro().getValorMinimo();
				}else{
					centavoPorMinuto=  (objSession.getArea().getValorPorHora()*100/60);
					maximoDolares=objSession.getArea().getValorMaximo();
					valorMinimoDolares = objSession.getArea().getValorMinimo();
				}
				
				calculaTiempoMinimoSegunValor();
				calculaTiempoMaximoSegunValor();
				//constraint="min 15 max 240: Entre 15 to 240"
				
				//tiempoMaxEnMinutos= objParam.getTiempoMaxEnMinutos();
				
//			}
			
//					
//		}else{
//			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
//		}
	}
	
	@NotifyChange("*")
	public void calculaLimite(){
		String minutosFecActual="";
		String minutosHoraActual="";
		if(objSession.getTramitePrevio()!=null)
			fechaActual = objSession.getTramitePrevio().getFechaFinal();
		else
			fechaActual = new java.util.Date();
		
		
		
		fechaActualLocal = fechaActual.toLocaleString();
		java.util.Date hactualutil = sumaMinutos(fechaActual, posicionActual);
		
		minutosFecActual=String.valueOf(fechaActual.getMinutes());
		minutosHoraActual = String.valueOf(hactualutil.getMinutes());
		
		minutosFecActual =(minutosFecActual.length()==1)?"0"+minutosFecActual:minutosFecActual;
		minutosHoraActual = (minutosHoraActual .length()==1)?"0"+minutosHoraActual :minutosHoraActual ;
		
		horaDesde= fechaActual.getHours()+":"+minutosFecActual;//fechaActual.getMinutes();
		horaHasta= hactualutil.getHours()+":"+minutosHoraActual;//hactualutil.getMinutes();
		fhasta = hactualutil;
		System.out.println("calcula limite"+fechaActual+" -- "+hactualutil+" --minuto: "+minuto);
		
		
	}
	
	
	@Command
	@NotifyChange("*")
//	@NotifyChange({"posicionActual","unidadDolar","centavos","hora","minuto","posicionActual","unidadDolar","centavos"})
	public void calculaMinutos(){
		
		posicionActual = (hora*60)+minuto;
		calculaDinero();
		calculaLimite();
	}
	
	
	@Command
//	@NotifyChange({"hora","minuto","posicionActual","unidadDolar","centavos"})
	@NotifyChange({"*"})
	public void calculaHoras(){
		hora=posicionActual/60;  
        minuto=(posicionActual-(60*hora)); 
        System.out.println(hora+":"+minuto+"----"+posicionActual);
        calculaDinero();
        calculaLimite();
	}
	
	@Command
	@NotifyChange({"unidadDolar","centavos","hora","minuto","posicionActual"})
	public void calculaDinero(){
		Double totalCentavos= centavoPorMinuto * posicionActual;//posicioActual refleja los minutos
		
//		if(objSession.getTramitePrevio()==null)//new
		if(!vehiculoPoseeTramitePrevio)//new
			if(totalCentavos < (valorMinimoDolares*100) )
				totalCentavos=(Double) (valorMinimoDolares*100);
		
		unidadDolar=(int) (totalCentavos/100);
		centavos=(int) (totalCentavos-(100*unidadDolar));
		System.out.println("dolar,cents"+unidadDolar+"--"+centavos);
//		if(objSession.getTramitePrevio()==null)//new
		if(!vehiculoPoseeTramitePrevio)//new
			if(existeLimiteDolares)
				if((unidadDolar+(centavos/100))>maximoDolares)
				{
	//				Messagebox.show("El valor monto máximo es : $"+maximoDolares, "Information", Messagebox.OK, Messagebox.INFORMATION);
					unidadDolar=(int) Math.floor(maximoDolares);
					centavos=(int) ((maximoDolares*100)-(unidadDolar*100));
					return;
				}
		
	}
	
	
	@Command
//	@NotifyChange({"posicionActual","unidadDolar","centavos","hora","minuto","posicionActual","unidadDolar","centavos"})
	@NotifyChange({"*"})
	public void calculaTiempo(){
			
			int unidadDolar=0;
			int centavos=0;
			int total=0;
			total =(this.unidadDolar*100)+this.centavos;
			if(existeLimiteDolares)
			if((this.unidadDolar+(this.centavos/100))>maximoDolares){
				Messagebox.show("El valor monto máximo es : $"+maximoDolares, "Information", Messagebox.OK, Messagebox.INFORMATION);
				unidadDolar=0;
				centavos=0;
				posicionActual=0;
				calculaHoras();
				return;
			}
			
			posicionActual=(int) (1*total/centavoPorMinuto);
			calculaHoras();
	}
	
	public java.util.Date sumaMinutos(java.util.Date fecha,int minsParam){
		
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); // Configuramos la fecha que se recibe
	      calendar.add(Calendar.MINUTE, minsParam);  // numero de horas a añadir, o restar en caso de horas<0

	      return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
	}
	
	@Command
	public void onSiguiente(){
		cargaTramite();
		double horasLimites=posicionActual+horas;
		if(horasLimites>tiempoMaxEnMinutos){
			Messagebox.show("No puede comprar mas de "+tiempoMaxEnMinutos+" minutos.", "Information", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
			
		//setea tramite
		objTramite=new Tramite();
		
		objTramite.setVehiculoId(objSession.getVehiculo().getVehiculoId());
		if(objSession.getParquimetro()!=null)
			objTramite.setIdParquimetro(objSession.getParquimetro().getIdParquimetro());
		
		if(objSession.getEspacioPorParquimetro()!=null)
			objTramite.setIdEspacioPorParquimetro(objSession.getEspacioPorParquimetro().getIdEspacioPorParquimetro());
		
		objTramite.setIdUsuario(objSession.getUsuario().getUsuarioId());
		
		objTramite.setIdTipoTramite(1);
		objTramite.setEstado("A");
		objTramite.setFechaInicial(fechaActual);
		objTramite.setFechaFinal(fhasta);
		objTramite.setMinutos(posicionActual);
		objTramite.setFechaTramite(new Date());
		objTramite.setUsuarioIngresa(objSession.getUsuario().getUsuario());
		objTramite.setFechaIngresa(new Date());
		//setea pago
		objPago=new Pago();
		Double unidadDolarDoub=(double) unidadDolar;
		Double centavosDouble=(double) centavos;
		objPago.setValorSubTotal(unidadDolarDoub+(centavosDouble/100));
		objPago.setValorTotal(unidadDolarDoub+(centavosDouble/100)+TASA_SM);
		Redondear red= new Redondear();
		objPago.setValorTotal(red.Redondear2(objPago.getValorTotal()));
		objPago.setValorPagado(0.0);
		objPago.setSaldo( unidadDolarDoub+(centavosDouble/100));
		objPago.setEstado("A");
		objPago.setUsuarioIngresa(objSession.getUsuario().getUsuario());
		objPago.setFechaIngresa(new Date());
		objPago.setFechaPago(new Date());
				
		objSession.setTramite(objTramite);
		objSession.setObjPago(objPago);
		
		Executions.getCurrent().getSession().setAttribute("session", objSession);
		
		Executions.getCurrent().sendRedirect("/Aplicacion/confirmaPago.zul");
	}
	
	
	@Command
	public void onAtras(){
		Executions.getCurrent().sendRedirect("/Aplicacion/pagoEstacionamiento.zul");
	}
	
	@Command
	public void verTarifas(){
		try{
			Window winAgregarActividad = (Window) Executions.createComponents("/Aplicacion/tarifas.zul", null, null);
			winAgregarActividad.doModal();					
			
		}catch(Exception e){e.printStackTrace();}		
	}

	public int getPosicionActual() {
		return posicionActual;
	}

	public void setPosicionActual(int posicionActual) {
		this.posicionActual = posicionActual;
	}

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	public int getMinuto() {
		return minuto;
	}

	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}



	public int getUnidadDolar() {
		return unidadDolar;
	}

	public void setUnidadDolar(int unidadDolar) {
		this.unidadDolar = unidadDolar;
	}

	public int getCentavos() {
		return centavos;
	}

	public void setCentavos(int centavos) {
		this.centavos = centavos;
	}


	public double getMaximoDolares() {
		return maximoDolares;
	}

	public void setMaximoDolares(double maximoDolares) {
		this.maximoDolares = maximoDolares;
	}

	public boolean isExisteLimiteDolares() {
		return existeLimiteDolares;
	}

	public void setExisteLimiteDolares(boolean existeLimiteDolares) {
		this.existeLimiteDolares = existeLimiteDolares;
	}

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

	public java.util.Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(java.util.Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getFechaActualLocal() {
		return fechaActualLocal;
	}

	public void setFechaActualLocal(String fechaActualLocal) {
		this.fechaActualLocal = fechaActualLocal;
	}

	public double getValorMinimoDolares() {
		return valorMinimoDolares;
	}

	public void setValorMinimoDolares(double valorMinimoDolares) {
		this.valorMinimoDolares = valorMinimoDolares;
	}

	public Parametros getObjParam() {
		return objParam;
	}

	public void setObjParam(Parametros objParam) {
		this.objParam = objParam;
	}

	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}

	public Double getCentavoPorMinuto() {
		return centavoPorMinuto;
	}

	public void setCentavoPorMinuto(Double centavoPorMinuto) {
		this.centavoPorMinuto = centavoPorMinuto;
	}

	public Tramite getObjTramite() {
		return objTramite;
	}

	public void setObjTramite(Tramite objTramite) {
		this.objTramite = objTramite;
	}

	public Pago getObjPago() {
		return objPago;
	}

	public void setObjPago(Pago objPago) {
		this.objPago = objPago;
	}

	public java.util.Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(java.util.Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFhasta() {
		return fhasta;
	}

	public void setFhasta(Date fhasta) {
		this.fhasta = fhasta;
	}

	public Session getObjSession() {
		return objSession;
	}

	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}

	public static Double getTasaSm() {
		return TASA_SM;
	}

	

	public int getTiempoMaxEnMinutos() {
		return tiempoMaxEnMinutos;
	}


	public void setTiempoMaxEnMinutos(int tiempoMaxEnMinutos) {
		this.tiempoMaxEnMinutos = tiempoMaxEnMinutos;
	}


	public int getTiempoMinEnMinutos() {
		return tiempoMinEnMinutos;
	}


	public void setTiempoMinEnMinutos(int tiempoMinEnMinutos) {
		this.tiempoMinEnMinutos = tiempoMinEnMinutos;
	}


	public String getConstraintSpinPosicion() {
		return constraintSpinPosicion;
	}


	public void setConstraintSpinPosicion(String constraintSpinPosicion) {
		this.constraintSpinPosicion = constraintSpinPosicion;
	}


	public Double getMinValorParquimetro() {
		return minValorParquimetro;
	}


	public void setMinValorParquimetro(Double minValorParquimetro) {
		this.minValorParquimetro = minValorParquimetro;
	}


	public Double getMaxValorParquimetro() {
		return maxValorParquimetro;
	}


	public void setMaxValorParquimetro(Double maxValorParquimetro) {
		this.maxValorParquimetro = maxValorParquimetro;
	}


	public Double getValorHoraParquimetro() {
		return valorHoraParquimetro;
	}


	public void setValorHoraParquimetro(Double valorHoraParquimetro) {
		this.valorHoraParquimetro = valorHoraParquimetro;
	}


	public boolean isNoPoseeTramitePrevio() {
		return noPoseeTramitePrevio;
	}


	public void setNoPoseeTramitePrevio(boolean noPoseeTramitePrevio) {
		this.noPoseeTramitePrevio = noPoseeTramitePrevio;
	}


	public boolean isVehiculoPoseeTramitePrevio() {
		return vehiculoPoseeTramitePrevio;
	}


	public void setVehiculoPoseeTramitePrevio(boolean vehiculoPoseeTramitePrevio) {
		this.vehiculoPoseeTramitePrevio = vehiculoPoseeTramitePrevio;
	}


	public List<Tramite> getListaTramite() {
		return listaTramite;
	}


	public void setListaTramite(List<Tramite> listaTramite) {
		this.listaTramite = listaTramite;
	}


	public long getHoras() {
		return horas;
	}


	public void setHoras(long horas) {
		this.horas = horas;
	}






	

}
