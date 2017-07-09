package ec.gob.movilpark.gestor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
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
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.EspacioPorParquimetro;
import ec.gob.movilpark.dto.MultaVehiculo;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Session;

public class EspacioParqOperadorGestor {

	private List<EspacioPorParquimetro> lsEspacioPorParquimetro;
	private EspacioPorParquimetro objEspacioPorParquimetro; 
	
	
	private Provincia objProvincia;
	private List<Provincia>listaProvincia;
	private List<Ciudad>listaCiudad;
	private Ciudad objCiudad;
	private List<Areas>listaAreas;
	private Areas objAreas;
	private Areas objAreasParam;
	private String estilo;
	private boolean repiteTimer=true;
	private boolean poseeTiempoVigente=false;
	private MultaVehiculo objMultaVeh;
	
	
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
		fillProvincia();
		fillAreas();
			consultaParquimetroPorUso();	

	}
	
	
	
	@Command
	public void onAtras(){
		Executions.getCurrent().sendRedirect("/Aplicacion/serviciosOperador.zul");
	}
	
	@Command
	public void salir(){
		Executions.getCurrent().getSession().removeAttribute("session");
		Executions.getCurrent().sendRedirect("../");		
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
	
	@Command
	@NotifyChange({"lsEspacioPorParquimetro","estilo"})
	public void consultaParquimetroPorUso(){
		System.out.println("consultaParquimetroPorUso!!!");
		long minutos=0;
		long segundos=0;
		Listas listasDao= new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		int idArea=((objAreasParam==null)?0:objAreasParam.getIdAreas());
		map = listasDao.consultaEspacioPorParquimetroEnUsoXarea(idArea);
		
		if(map.get("error") == null){
			lsEspacioPorParquimetro=(List<EspacioPorParquimetro>) map.get("listaEspacio");
			for(int i=0;i<lsEspacioPorParquimetro.size();i++){
//				System.out.println("fecFinal: "+lsEspacioPorParquimetro.get(i).getFechaFinal()+"- new Date" +new Date() );
				
				if(lsEspacioPorParquimetro.get(i).getIdMultaVehiculo()==0)
					lsEspacioPorParquimetro.get(i).setExisteMulta(false);
				else
					lsEspacioPorParquimetro.get(i).setExisteMulta(true);
				
				minutos = diferenciaEnDias2(lsEspacioPorParquimetro.get(i).getFechaFinal(),new Date());
				segundos = diferenciaEnDiasSeg(lsEspacioPorParquimetro.get(i).getFechaFinal(),new Date());
				segundos = segundos - (minutos*60);
				
				if(segundos<0)
					lsEspacioPorParquimetro.get(i).setSegundos(0);
				else
					lsEspacioPorParquimetro.get(i).setSegundos(segundos);
				
				
				if(minutos<0)
					lsEspacioPorParquimetro.get(i).setMinutos(0);
				else
					lsEspacioPorParquimetro.get(i).setMinutos(minutos);
				
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
				//fillAreas();
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
	
	public void consultaPorPlaca(){
		
		System.out.println("consultaParquimetroPorUso!!!");
		long minutos=0;
		Listas listasDao= new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listasDao.consultaParquimetroPorPlaca(objEspacioPorParquimetro.getPlaca());
		
		if(map.get("error") == null){
			lsEspacioPorParquimetro=(List<EspacioPorParquimetro>) map.get("listaEspacio");
			if(lsEspacioPorParquimetro!=null)
				if(lsEspacioPorParquimetro.size()>0)
					poseeTiempoVigente=true;
				else
					poseeTiempoVigente=false;
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		
	}
	@Command
	public void gestionaMulta(@BindingParam("param") EspacioPorParquimetro objEspacioPorParquimetro){
		
		if(objEspacioPorParquimetro==null){
			Messagebox.show("Porfavor Seleccione un Registro", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
//		consultaPorPlaca();
//		
//		if(poseeTiempoVigente){
//			Messagebox.show("El vehiculo con placa "+objEspacioPorParquimetro.getPlaca()+" posee tiempo vigente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
//			return;
//		}
		
		if(!objEspacioPorParquimetro.isExisteMulta()){
				objMultaVeh = new MultaVehiculo();
				objMultaVeh.setVehiculoId(objEspacioPorParquimetro.getVehiculoId());
				objMultaVeh.setEstado("A");
				objMultaVeh.setFechaIngresa(new Date());
				objMultaVeh.setUsuarioIngresa(objSession.getUsuario().getUsuario());
				
				Listas listasDao= new Listas(); 
				Map<String,Object> map= new HashMap<String , Object>();  
				map = listasDao.insertaMultaVeh(objMultaVeh);
				
				if(map.get("error") == null){
					Messagebox.show("Transaccion realizada con exito", "Atención", Messagebox.OK, Messagebox.INFORMATION);
					
				}else{
					Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
					return;
				}
		}else{
			
			objMultaVeh = new MultaVehiculo();
			objMultaVeh.setIdMultaVehiculo(objEspacioPorParquimetro.getIdMultaVehiculo());
			objMultaVeh.setFechaActualiza(new Date());
			objMultaVeh.setUsuarioActualiza(objSession.getUsuario().getUsuario());
			
			Listas listasDao= new Listas(); 
			Map<String,Object> map= new HashMap<String , Object>();  
			map = listasDao.inactivaMultaVeh(objMultaVeh);
			
			if(map.get("error") == null){
				Messagebox.show("Transaccion realizada con exito", "Atención", Messagebox.OK, Messagebox.INFORMATION);
				
			}else{
				Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
	}
	
	
	public void fillAreas(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		String cciudad=((objCiudad==null)?"0":objCiudad.getCiuCodigo());
		String cprovincia=((objProvincia==null)?"0":objProvincia.getProCodigo());
		
		map = listasDao.obtieneAreas(cciudad, cprovincia);
			listaAreas= new ArrayList<Areas>();
		
		if(map.get("error") == null){
			
			listaAreas=(List<Areas>) map.get("areas");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public long diferenciaEnDias2(Date fechaMayor, Date fechaMenor) {
		long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();
		long dias = diferenciaEn_ms / (1000 * 60 );
		return  dias;
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

public List<EspacioPorParquimetro> getLsEspacioPorParquimetro() {
	return lsEspacioPorParquimetro;
}

public void setLsEspacioPorParquimetro(List<EspacioPorParquimetro> lsEspacioPorParquimetro) {
	this.lsEspacioPorParquimetro = lsEspacioPorParquimetro;
}

public EspacioPorParquimetro getObjEspacioPorParquimetro() {
	return objEspacioPorParquimetro;
}

public void setObjEspacioPorParquimetro(EspacioPorParquimetro objEspacioPorParquimetro) {
	this.objEspacioPorParquimetro = objEspacioPorParquimetro;
}

public Provincia getObjProvincia() {
	return objProvincia;
}

public void setObjProvincia(Provincia objProvincia) {
	this.objProvincia = objProvincia;
}

public List<Provincia> getListaProvincia() {
	return listaProvincia;
}

public void setListaProvincia(List<Provincia> listaProvincia) {
	this.listaProvincia = listaProvincia;
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

public String getEstilo() {
	return estilo;
}

public void setEstilo(String estilo) {
	this.estilo = estilo;
}

public boolean isRepiteTimer() {
	return repiteTimer;
}

public void setRepiteTimer(boolean repiteTimer) {
	this.repiteTimer = repiteTimer;
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

public boolean isPoseeTiempoVigente() {
	return poseeTiempoVigente;
}

public void setPoseeTiempoVigente(boolean poseeTiempoVigente) {
	this.poseeTiempoVigente = poseeTiempoVigente;
}

public MultaVehiculo getObjMultaVeh() {
	return objMultaVeh;
}

public void setObjMultaVeh(MultaVehiculo objMultaVeh) {
	this.objMultaVeh = objMultaVeh;
}



public Areas getObjAreasParam() {
	return objAreasParam;
}



public void setObjAreasParam(Areas objAreasParam) {
	this.objAreasParam = objAreasParam;
}
	
}
