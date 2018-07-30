package ec.gob.movilpark.gestor;

import java.util.ArrayList;
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
import ec.gob.movilpark.dto.Areas;
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.EspacioPorParquimetro;
import ec.gob.movilpark.dto.Parametros;
import ec.gob.movilpark.dto.Perfil;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Tramite;

public class EstadoCuentasGestor {
	
	private List<Tramite> listaTramite;
	private List<Tramite> listaTramitePlaca;
	private List<EspacioPorParquimetro> lsEspacioPorParquimetro;
	private Tramite objTramite;
	Window win;
	private Session objSession;	
	private long horas;
	private long mins;
	private long segundos;
	private List<Provincia>listaProvincia;
	private Provincia objProvincia;
	private List<Ciudad>listaCiudad;
	private Ciudad objCiudad;
	private List<Areas>listaAreas;
	private Areas objAreas;

	@Init
	@NotifyChange("*")
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("perfil") Perfil objPerfil)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
			return;
		}
			
		fillProvincia();
		consultaParquimetroPorUso();
		//cargaTramite();
//		if(objTramite!=null){
//			horas = diferenciaEnDias2(objTramite.getFechaFinal() ,new java.util.Date());
//			System.out.println("objTramite.getFechaInicial(): "+objTramite.getFechaInicial());	
//		}
		
//		if(objSession.getTramite()!=null)
//		horas=diferenciaEnDias2(objSession.getTramite().getFechaFinal(),new java.util.Date());

			
			
//		System.out.println("horas: "+horas+"  fec sess:"+objSession.getTramite().getFechaFinal()) ;
		
	}
	
	
	
	
	
	@NotifyChange("listaProvincia")
	public void fillProvincia(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.obtieneProvincias(0);
		listaProvincia= new ArrayList<Provincia>();
		
		if(map.get("error") == null){
			
			listaProvincia=(List<Provincia>) map.get("provincia");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public long diferenciaEnDiasSeg(Date fechaMayor, Date fechaMenor) {
		long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();
		long seg = diferenciaEn_ms / (1000);
		return  seg;
		}
	
public void fillCiudades(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.obtieneCiudades("0", objProvincia.getProCodigo());
			listaCiudad= new ArrayList<Ciudad>();
		
		if(map.get("error") == null){
			
			listaCiudad=(List<Ciudad>) map.get("ciudad");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		
		
	}
	
	

public void fillAreas(){
	
	Map<String,Object> map= new HashMap<String , Object>();
	Listas listasDao= new Listas();
	map = listasDao.obtieneAreas(objCiudad.getCiuCodigo(), objProvincia.getProCodigo());
		listaAreas= new ArrayList<Areas>();
	
	if(map.get("error") == null){
		
		listaAreas=(List<Areas>) map.get("areas");
				
	}else{
		Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
	}
}
	
	@Command
	@NotifyChange({"lsEspacioPorParquimetro","estilo","horas"})
	public void consultaParquimetroPorUso(){
		System.out.println("consultaParquimetroPorUso!!!");
		long minutos=0;
		long segundos=0;
		Listas listasDao= new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listasDao.consultaEspacioPorParquimetroEnUsoPorUsuario(objSession.getUsuario().getUsuarioId());
		
		if(map.get("error") == null){
			lsEspacioPorParquimetro=(List<EspacioPorParquimetro>) map.get("listaEspacio");
			for(int i=0;i<lsEspacioPorParquimetro.size();i++){
//				System.out.println("fecFinal: "+lsEspacioPorParquimetro.get(i).getFechaFinal()+"- new Date" +new Date() );
				
				if(lsEspacioPorParquimetro.get(i).getIdMultaVehiculo()==0)
					lsEspacioPorParquimetro.get(i).setExisteMulta(false);
				else
					lsEspacioPorParquimetro.get(i).setExisteMulta(true);
				
				minutos = diferenciaEnDias2(lsEspacioPorParquimetro.get(i).getFechaFinal(),new Date());
				
				//segundos = diferenciaEnDiasSeg(lsEspacioPorParquimetro.get(i).getFechaFinal(),new Date());
				//segundos = segundos - (minutos*60);
				
				if(minutos<0){
					lsEspacioPorParquimetro.get(i).setMinutos(0);
					minutos =0;
				}
				else
					lsEspacioPorParquimetro.get(i).setMinutos(minutos);
				
				horas = horas + minutos;
				
				if(segundos<0)
					lsEspacioPorParquimetro.get(i).setSegundos(0);
				else
					lsEspacioPorParquimetro.get(i).setSegundos(segundos);
				
				if(minutos>=75)
					lsEspacioPorParquimetro.get(i).setEstilo("background-color: green; color: white; font-weight: bold");
				if(minutos<75 && minutos>=15)
					lsEspacioPorParquimetro.get(i).setEstilo("background-color: yellow; color: black; font-weight: bold");
				if(minutos<15)
					lsEspacioPorParquimetro.get(i).setEstilo("background-color: red; color: white; font-weight: bold");
				
//				if("S".equals(lsEspacioPorParquimetro.get(i).getEnUso()))
//					lsEspacioPorParquimetro.get(i).setEstilo("background-color: green; color: white; font-weight: bold");
//				else
//					lsEspacioPorParquimetro.get(i).setEstilo("background-color: red; color: white; font-weight: bold");
				
				for(int j=0;j<listaProvincia.size();j++){
					if(lsEspacioPorParquimetro.get(i).getCodigoProvincia().equals(listaProvincia.get(j).getProCodigo())){
						objProvincia=listaProvincia.get(j);
							}
						}
				fillCiudades();
				for(int j=0;j<listaCiudad.size();j++){
					if(lsEspacioPorParquimetro.get(i).getCodigoCiudad().equals(listaCiudad.get(j).getCiuCodigo())){
						objCiudad=listaCiudad.get(j);
					}
				}
				fillAreas();
				for(int j=0;j<listaAreas.size();j++){
					if(lsEspacioPorParquimetro.get(i).getIdAreas()==listaAreas.get(j).getIdAreas()){
						objAreas=listaAreas.get(j);
					}
				}
				objProvincia=null;
				objCiudad=null;
				objAreas=null;
				
			}
			
			
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		
		
		
	}
	
	
	
	
	
	
	@Command
	public void muestraTiempo(){
		try{
			
			Map param= new HashMap();
			param.put("tramite", objTramite);
			System.out.println("placa envia: "+objTramite.getPlaca());
			Window win= (Window) Executions.getCurrent().createComponents("detalleEstadoCuentas.zul", null,param);
			win.doModal();
			win.setClosable(true);
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public long diferenciaEnDias2(Date fechaMayor, Date fechaMenor) {
		long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();
		long dias = diferenciaEn_ms / (1000 * 60 );
		return  dias;
		}
	
	@NotifyChange({"listaTramite","horas","listaTramitePlaca"})
	public void cargaTramite(){
		Listas listaDao = new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listaDao.consultaTramiteXusuarioPlaca(objSession.getUsuario().getUsuarioId());
		
		
		if(map.get("error") == null){
			listaTramite=(List<Tramite>) map.get("listTramite");
//			if(listaTramite!=null)
//				if(listaTramite.size()>0)
//					horas = diferenciaEnDias2(listaTramite.get(listaTramite.size()-1).getFechaFinal() ,new java.util.Date());
			for(int i=0;i<listaTramite.size();i++)
				horas = horas+diferenciaEnDias2(listaTramite.get(i).getFechaFinal() ,new java.util.Date());
			
			
			
			map = listaDao.consultaPlacaXusuario(objSession.getUsuario().getUsuarioId());
			if(map.get("error") == null)
				listaTramitePlaca=(List<Tramite>) map.get("listTramite");
			else
				Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
					
			
			for(int i=0;i<listaTramitePlaca.size();i++)
				listaTramitePlaca.get(i).setMinutos((int)diferenciaEnDias2(listaTramite.get(i).getFechaFinal() ,new java.util.Date()));
			
			
			
			
			
//			if(listaTramite!=null)
//				if(listaTramite.size()>0)
//				{
//					objTramite=listaTramite.get(listaTramite.size()-1);
//				}
			
		}else
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		
	}
	
	@Command
	public void onServicio(){
		Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
	}
	
	@Command
	public void onCheck(){
		Executions.getCurrent().sendRedirect("/Aplicacion/servicios.zul");
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

	public long getHoras() {
		return horas;
	}

	public void setHoras(long horas) {
		this.horas = horas;
	}

	public long getMins() {
		return mins;
	}

	public void setMins(long mins) {
		this.mins = mins;
	}

	public long getSegundos() {
		return segundos;
	}

	public void setSegundos(long segundos) {
		this.segundos = segundos;
	}

	public List<Tramite> getListaTramite() {
		return listaTramite;
	}

	public void setListaTramite(List<Tramite> listaTramite) {
		this.listaTramite = listaTramite;
	}

	public Tramite getObjTramite() {
		return objTramite;
	}

	public void setObjTramite(Tramite objTramite) {
		this.objTramite = objTramite;
	}


	public List<Tramite> getListaTramitePlaca() {
		return listaTramitePlaca;
	}


	public void setListaTramitePlaca(List<Tramite> listaTramitePlaca) {
		this.listaTramitePlaca = listaTramitePlaca;
	}





	public List<Provincia> getListaProvincia() {
		return listaProvincia;
	}





	public void setListaProvincia(List<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}





	public List<EspacioPorParquimetro> getLsEspacioPorParquimetro() {
		return lsEspacioPorParquimetro;
	}





	public void setLsEspacioPorParquimetro(List<EspacioPorParquimetro> lsEspacioPorParquimetro) {
		this.lsEspacioPorParquimetro = lsEspacioPorParquimetro;
	}





	public Provincia getObjProvincia() {
		return objProvincia;
	}





	public void setObjProvincia(Provincia objProvincia) {
		this.objProvincia = objProvincia;
	}





	public List<Ciudad> getListaCiudad() {
		return listaCiudad;
	}





	public void setListaCiudad(List<Ciudad> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}





	public Ciudad getObjCiudad() {
		return objCiudad;
	}





	public void setObjCiudad(Ciudad objCiudad) {
		this.objCiudad = objCiudad;
	}





	public List<Areas> getListaAreas() {
		return listaAreas;
	}





	public void setListaAreas(List<Areas> listaAreas) {
		this.listaAreas = listaAreas;
	}





	public Areas getObjAreas() {
		return objAreas;
	}





	public void setObjAreas(Areas objAreas) {
		this.objAreas = objAreas;
	}

}
