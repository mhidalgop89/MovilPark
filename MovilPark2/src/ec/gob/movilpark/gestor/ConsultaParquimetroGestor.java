package ec.gob.movilpark.gestor;

import java.util.ArrayList;
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
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.EspacioPorParquimetro;
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Tramite;
import ec.gob.movilpark.dto.Vehiculo;

public class ConsultaParquimetroGestor {

	
	Window win;
	private String horaActual;
	private String horaFinal;
	private List<Provincia> listaProvincia;
	private List<Ciudad> listaCiudades;
	private List<Vehiculo> listaVehiculos;
	private Provincia objProvincia;
	private Ciudad objCiudad;
	private Vehiculo objVehiculo;
	private Session objSession;
	private List<EspacioPorParquimetro> listaEspacioPorParquimetro;
	private EspacioPorParquimetro objEspacioPorParquimetro;
	private List<Areas> listaAreas;
	private Areas objArea;
	
	private List<Parquimetro> listaParquimetros;
	private Parquimetro objParquimetro;
	private List<EspacioPorParquimetro> listaEspacioPorParquimetroResult;
	private EspacioPorParquimetro objEspacioPorParquimetroResult;
	

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
		
		fillProvincia();
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
	
	
	@NotifyChange({"listaCiudades","objCiudad","objArea","objParquimetro","objEspacioPorParquimetro"})
	@Command
public void fillCiudades(){
		
		objCiudad=null;
		objArea=null;
		objParquimetro=null;
		objEspacioPorParquimetro=null;
		if(objProvincia==null)return;
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.obtieneCiudades("0", objProvincia.getProCodigo());
				listaCiudades= new ArrayList<Ciudad>();
		
		if(map.get("error") == null){
			
			listaCiudades=(List<Ciudad>) map.get("ciudad");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	
	
	@NotifyChange({"listaAreas","objArea","objParquimetro","objEspacioPorParquimetro"})
	@Command
	public void fillAreas(){
		objArea=null;
		objParquimetro=null;
		objEspacioPorParquimetro=null;
		
		listaAreas=null;
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		if(objCiudad==null)	return;
		System.out.println("objCiudad.getCiuCodigo(): "+objCiudad.getCiuCodigo());
		map = listasDao.obtieneAreas(objCiudad.getCiuCodigo(), objProvincia.getProCodigo());
		
			//listaAreas= new ArrayList<Areas>();
		
		if(map.get("error") == null){
			
			listaAreas=(List<Areas>) map.get("areas");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	@Command
	@NotifyChange("listaEspacioPorParquimetro")
	public void consultaEspacio(){
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
			if(objParquimetro==null)return;
		map = listasDao.consultaEspacioPorParquimetro(objParquimetro.getIdParquimetro());
//		listaEspacioPorParquimetro= new ArrayList<EspacioPorParquimetro>();
		
		if(map.get("error") == null){
			
			listaEspacioPorParquimetro=(List<EspacioPorParquimetro>) map.get("listaEspacio");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	@Command
	public void consultar(){
		
//		if(objVehiculo==null){
//			Messagebox.show("Favor seleccione placa", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
//			return;
//		}
		if(objParquimetro==null){
			Messagebox.show("Favor seleccione parquimetro", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		
		if(objProvincia==null){
			Messagebox.show("Favor seleccione provincia", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(objCiudad==null){
			Messagebox.show("Favor seleccione ciudad", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(objArea==null){
			Messagebox.show("Favor seleccione area", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if(objEspacioPorParquimetro==null){
			Messagebox.show("Favor seleccione espacio de parquimetro", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		
		Listas listaDao = new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		
		map = listaDao.consultaEspacioPorParquimetro(objParquimetro.getIdParquimetro());
		
		
		if(map.get("error") == null){
			
			listaEspacioPorParquimetroResult=(List<EspacioPorParquimetro>) map.get("listaEspacio");
			if(listaEspacioPorParquimetroResult!=null)
				if(listaEspacioPorParquimetroResult.size()>0)
				{
					for(int i=0;i<listaEspacioPorParquimetroResult.size();i++)
					{
						if(objEspacioPorParquimetro.getIdEspacioPorParquimetro()== listaEspacioPorParquimetroResult.get(i).getIdEspacioPorParquimetro()){
							objEspacioPorParquimetroResult=listaEspacioPorParquimetroResult.get(i);
						}
					}
				}
			
		}else{
			
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		
		if("S".equals(objEspacioPorParquimetroResult.getEnUso())){
			Map mapParametros = new HashMap();
			mapParametros.put("espacio",objEspacioPorParquimetroResult);
			
			objSession.setObjEspacioPorParquimetroParam(objEspacioPorParquimetroResult);
			Executions.getCurrent().getSession().setAttribute("session", objSession);
			
			final Window win5 = (Window) Executions.createComponents("verDetalleEspacioParquimetro.zul", null, mapParametros);
			win5.doModal();	
//			Messagebox.show("Este Parquimetro se encuentra en uso", "Atención", Messagebox.OK, Messagebox.INFORMATION);
		}else{
			Messagebox.show("Este Parquimetro NO se encuentra en uso", "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	
	@NotifyChange({"listaParquimetros","objParquimetro","objEspacioPorParquimetro"})
	@Command
	public void fillParquimetro(){
		objParquimetro=null;
		objEspacioPorParquimetro=null;
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();

		System.out.println(" objSession.getUsuario().getUsuarioId(): "+ objSession.getUsuario().getUsuarioId());
		if(objArea==null)return;
		map = listasDao.consultaParquimetroParam(objProvincia.getProCodigo(), objCiudad.getCiuCodigo(), objArea.getIdAreas());
		listaParquimetros= new ArrayList<Parquimetro>();
		
		if(map.get("error") == null){
			
			listaParquimetros=(List<Parquimetro>) map.get("listaParquimetro");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		
	}


	public Window getWin() {
		return win;
	}


	public void setWin(Window win) {
		this.win = win;
	}


	public String getHoraActual() {
		return horaActual;
	}


	public void setHoraActual(String horaActual) {
		this.horaActual = horaActual;
	}


	public String getHoraFinal() {
		return horaFinal;
	}


	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}


	public List<Provincia> getListaProvincia() {
		return listaProvincia;
	}


	public void setListaProvincia(List<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}


	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}


	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}


	public List<Vehiculo> getListaVehiculos() {
		return listaVehiculos;
	}


	public void setListaVehiculos(List<Vehiculo> listaVehiculos) {
		this.listaVehiculos = listaVehiculos;
	}


	public Provincia getObjProvincia() {
		return objProvincia;
	}


	public void setObjProvincia(Provincia objProvincia) {
		this.objProvincia = objProvincia;
	}


	public Ciudad getObjCiudad() {
		return objCiudad;
	}


	public void setObjCiudad(Ciudad objCiudad) {
		this.objCiudad = objCiudad;
	}


	public Vehiculo getObjVehiculo() {
		return objVehiculo;
	}


	public void setObjVehiculo(Vehiculo objVehiculo) {
		this.objVehiculo = objVehiculo;
	}


	public Session getObjSession() {
		return objSession;
	}


	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}


	public List<EspacioPorParquimetro> getListaEspacioPorParquimetro() {
		return listaEspacioPorParquimetro;
	}


	public void setListaEspacioPorParquimetro(List<EspacioPorParquimetro> listaEspacioPorParquimetro) {
		this.listaEspacioPorParquimetro = listaEspacioPorParquimetro;
	}


	public EspacioPorParquimetro getObjEspacioPorParquimetroResult() {
		return objEspacioPorParquimetroResult;
	}


	public void setObjEspacioPorParquimetroResult(EspacioPorParquimetro objEspacioPorParquimetroResult) {
		this.objEspacioPorParquimetroResult = objEspacioPorParquimetroResult;
	}


	public List<EspacioPorParquimetro> getListaEspacioPorParquimetroResult() {
		return listaEspacioPorParquimetroResult;
	}


	public void setListaEspacioPorParquimetroResult(List<EspacioPorParquimetro> listaEspacioPorParquimetroResult) {
		this.listaEspacioPorParquimetroResult = listaEspacioPorParquimetroResult;
	}


	public EspacioPorParquimetro getObjEspacioPorParquimetro() {
		return objEspacioPorParquimetro;
	}


	public void setObjEspacioPorParquimetro(EspacioPorParquimetro objEspacioPorParquimetro) {
		this.objEspacioPorParquimetro = objEspacioPorParquimetro;
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


	public List<Parquimetro> getListaParquimetros() {
		return listaParquimetros;
	}


	public void setListaParquimetros(List<Parquimetro> listaParquimetros) {
		this.listaParquimetros = listaParquimetros;
	}


	public Parquimetro getObjParquimetro() {
		return objParquimetro;
	}


	public void setObjParquimetro(Parquimetro objParquimetro) {
		this.objParquimetro = objParquimetro;
	} 
	
	
	
	
}
