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
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Usuario;
import ec.gob.movilpark.util.Redondear;

public class AgreagarParquimetroGestor {
	
	private Parquimetro objParquimetro;
	private Parquimetro objParquimetroParam;
	private List<Ciudad> listaCiudades;
	private List<Provincia> listaProvincias;
	private List<Areas> listaAreas;
	private Provincia objProvincia;
	private Ciudad objCiudad;
	private Areas objArea;
	
	
	private String estado;
	private Session objSession;
	private boolean disabled=true;
	
	private boolean deshabilitaValorMinimo=true;
	private boolean deshabilitaValorMaximo=true;
	private String etiquetaValorPorMinimo;
	private String etiquetaValorPorMaximo;
	Window win;
	

	@Init
	@NotifyChange("*")
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("parquimetro") Parquimetro objParquimetroParam)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
				Executions.getCurrent().sendRedirect("../");
				return;
			}
		
		fillProvincia();
		if(objParquimetroParam!=null){
			deshabilitaValorMaximo=false;
			deshabilitaValorMinimo=false;
			this.objParquimetroParam=objParquimetroParam;
			this.objParquimetro=objParquimetroParam;

			for(int j=0;j<listaProvincias.size();j++){
				if(objParquimetro.getProvinciaCodigo().equals(listaProvincias.get(j).getProCodigo())){
					objProvincia=listaProvincias.get(j);
						}
					}
			fillCiudades();
			
			for(int j=0;j<listaCiudades.size();j++){
				if(objParquimetro.getCiudadCodigo().equals(listaCiudades.get(j).getCiuCodigo())){
					objParquimetro.setNombreCiudad(listaCiudades.get(j).getNombre());
					objCiudad=listaCiudades.get(j);
				}
			}
			fillAreas();
			for(int j=0;j<listaAreas.size();j++){
				if(objParquimetro.getIdAreas()==listaAreas.get(j).getIdAreas()){
					objParquimetro.setNombreArea(listaAreas.get(j).getNombre());
					objArea=listaAreas.get(j);
				}
			}
			
			if("A".equals(objParquimetroParam.getEstado()))
				estado="A";
			else
				estado="I";
		}
		else{
			this.objParquimetroParam=objParquimetroParam;
			objParquimetro= new Parquimetro();
			estado="A";
			disabled=true;

			
		}
		
		
	}
	
	@NotifyChange({"deshabilitaValorMaximo","deshabilitaValorMinimo","objParquimetro","etiquetaValorPorMinimo","etiquetaValorPorMaximo"})
	@Command
	public void llenaValorPorHora(){
		
		
		
		if(objParquimetro==null)
			return;
		if(objParquimetro.getValorPorHora()==null)
			{
				objParquimetro.setValorMinimo(0.0);
				objParquimetro.setValorMaximo(0.0);
				deshabilitaValorMaximo=true;
				deshabilitaValorMinimo=true;
				return;
			}
		if(objParquimetro.getValorPorHora()<=0)
			{
			Messagebox.show("Campo Valor por Hora debe de llevar valores mayores a 0.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			objParquimetro.setValorMinimo(0.0);
			objParquimetro.setValorMaximo(0.0);
			deshabilitaValorMaximo=true;
			deshabilitaValorMinimo=true;
				return;
			}
		
		
		
		deshabilitaValorMaximo=false;
		deshabilitaValorMinimo=false;
		calculaTiempoMinimoSegunValor();
		calculaTiempoMaximoSegunValor();
		
	}
	@NotifyChange({"etiquetaValorPorMinimo"})
	@Command
	public void calculaTiempoMinimoSegunValor(){
		if(objParquimetro==null)
			return;
		if(objParquimetro.getValorMinimo()==null)
			{
				objParquimetro.setValorMinimo(0.0);
				return;
			}
		if(objParquimetro.getValorMinimo()<=0)
			{
			Messagebox.show("Campo Valor Minimo debe de llevar valores mayores a 0.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			objParquimetro.setValorMinimo(0.0);
				return;
			}
		
		
		Double tiempoMin=0.0;
		tiempoMin=(60*objParquimetro.getValorMinimo())/(objParquimetro.getValorPorHora());
		Redondear red= new Redondear();
		tiempoMin=red.Redondear2(tiempoMin);
		etiquetaValorPorMinimo=tiempoMin.toString()+" minutos";
		System.out.println("etiquetaValorPorMinimo: "+etiquetaValorPorMinimo);
	}
	
	@NotifyChange({"etiquetaValorPorMaximo"})
	@Command
	public void calculaTiempoMaximoSegunValor(){
		
		if(objParquimetro==null)
			return;
		if(objParquimetro.getValorMaximo()==null)
			{
				objParquimetro.setValorMaximo(0.0);
				return;
			}
		if(objParquimetro.getValorMaximo()<=0)
			{
			Messagebox.show("Campo Valor Maximo debe de llevar valores mayores a 0.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			objParquimetro.setValorMaximo(0.0);
				return;
			}
		
		
		Double tiempoMax=0.0;
		tiempoMax=(60*objParquimetro.getValorMaximo())/(objParquimetro.getValorPorHora());
		System.out.println("tiempoMax sin redondear: "+tiempoMax);
		Redondear red= new Redondear();
		tiempoMax=red.Redondear2(tiempoMax);
		
		etiquetaValorPorMaximo=tiempoMax.toString()+" minutos";
		System.out.println("etiquetaValorPorMaximo: "+etiquetaValorPorMaximo);
	}
	
	@Command
	public void numEspacios(){
		
		System.out.println("--->"+objParquimetro.getNumeroEspacios());
	}

	@NotifyChange("objParquimetro")
	@Command
	public void verMapa(){
		
//		Messagebox.show("Mapa generado exitosamente!!! Por favor presione aceptar para visualizar el mapa.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
		System.out.println("inicia verMapa");
		Window winMap = (Window) Executions.createComponents("mapa.zul", null, null);
		winMap.doModal();	
		
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session"); 
		objParquimetro.setLatitud(objSession.getLatitud());
		objParquimetro.setLongitud(objSession.getLongitud());
	}
	

	
	@Command
	public synchronized void guardarParquimetro(){
		
		if(objParquimetro.getNombre()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objProvincia==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objCiudad==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objArea==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objParquimetro.getCalle()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objParquimetro.getPoste()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		} 
		if(objParquimetro.getNumeroEspacios()==0){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objParquimetro.getValorMinimo()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objParquimetro.getValorMaximo()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objParquimetro.getValorMinimo()==0){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objParquimetro.getValorMaximo()==0){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objParquimetro.getValorPorHora()<=0){
			Messagebox.show("Debe llenar todos los campos obligatorios. Recuerde que el campo Valor por Hora no debe tener valores "
					+ "menores o iguales a Cero", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objParquimetro.getValorPorHora()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objParquimetro.getValorMaximo()<=0){
			Messagebox.show("El campo Valor Minimo No debe poseer valores menores o iguales a Cero.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objParquimetro.getValorMinimo()<=0){
			Messagebox.show("El campo Valor Maximo No debe poseer valores menores o iguales a Cero.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objParquimetro.getValorMinimo()>objParquimetro.getValorMaximo()){
			Messagebox.show("El campo Valor Maximo No debe ser menor a Valor Minimo.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		
		if(objParquimetro.getLatitud()==null)
			objParquimetro.setLatitud(0.0);
		if(objParquimetro.getLongitud()==null)
			objParquimetro.setLongitud(0.0);
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		
		objParquimetro.setProvinciaCodigo(objProvincia.getProCodigo());
		objParquimetro.setCiudadCodigo(objCiudad.getCiuCodigo());
		objParquimetro.setIdAreas(objArea.getIdAreas());
		if(this.objParquimetroParam==null){
			System.out.println("inserta parq");
			
			map = listasDao.insertaParquimetro(objParquimetro);
		}
		else{
			System.out.println("act parq");
			objParquimetro.setEstado(estado);
			
			map = listasDao.editaParquimetro(objParquimetro);
		}
			
		
		if(map.get("error") == null)
			Messagebox.show("Almacenado con exito.", "Atención", Messagebox.OK, Messagebox.INFORMATION);					
		else
			{
				Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		
		win.detach();
		
		
	}
	
	@NotifyChange("*")
	@Command
	public void estableceValores(){
//		if(objArea==null){
//			System.out.println("objArea: "+objArea);
//			return;
//		}
		System.out.println("objArea2: "+objArea);
		objParquimetro.setValorMaximo(objArea.getValorMaximo());
		objParquimetro.setValorMinimo(objArea.getValorMinimo());
		objParquimetro.setValorPorHora(objArea.getValorPorHora());
		llenaValorPorHora();
		calculaTiempoMinimoSegunValor();
		calculaTiempoMaximoSegunValor();
		
	}
	
	@NotifyChange("listaAreas")
	@Command
	public void fillAreas(){
		listaAreas=null;
		objArea=null;
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		if(objCiudad==null)	return;
		map = listasDao.obtieneAreas(objCiudad.getCiuCodigo(), objProvincia.getProCodigo());
		
			//listaAreas= new ArrayList<Areas>();
		
		if(map.get("error") == null){
			
			listaAreas=(List<Areas>) map.get("areas");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@NotifyChange({"listaProvincia","listaCiudades","listaAreas","objCiudad","objArea"})
	@Command
	public void fillProvincia(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.obtieneProvincias(0);
		listaProvincias= new ArrayList<Provincia>();
		
		if(map.get("error") == null){
			
			listaProvincias=(List<Provincia>) map.get("provincia");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@NotifyChange({"listaProvincia","listaCiudades","listaAreas","objCiudad","objArea"})
	@Command
public void fillCiudades(){
		listaCiudades=null;
		listaAreas=null;
		objCiudad=null;
		objArea=null;
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		
		if(objProvincia==null)return;
		
		map = listasDao.obtieneCiudades("0", objProvincia.getProCodigo());
				//listaCiudades= new ArrayList<Ciudad>();
		
		if(map.get("error") == null){
			
			listaCiudades=(List<Ciudad>) map.get("ciudad");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Command
	public void salir(){
		win.detach();
	}

	public Parquimetro getObjParquimetro() {
		return objParquimetro;
	}


	public void setObjParquimetro(Parquimetro objParquimetro) {
		this.objParquimetro = objParquimetro;
	}


	public Parquimetro getObjParquimetroParam() {
		return objParquimetroParam;
	}


	public void setObjParquimetroParam(Parquimetro objParquimetroParam) {
		this.objParquimetroParam = objParquimetroParam;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
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


	public boolean isDisabled() {
		return disabled;
	}


	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}


	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}


	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}


	public List<Provincia> getListaProvincias() {
		return listaProvincias;
	}


	public void setListaProvincias(List<Provincia> listaProvincias) {
		this.listaProvincias = listaProvincias;
	}


	public List<Areas> getListaAreas() {
		return listaAreas;
	}


	public void setListaAreas(List<Areas> listaAreas) {
		this.listaAreas = listaAreas;
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


	public Areas getObjArea() {
		return objArea;
	}


	public void setObjArea(Areas objArea) {
		this.objArea = objArea;
	}

	public boolean isDeshabilitaValorMinimo() {
		return deshabilitaValorMinimo;
	}

	public void setDeshabilitaValorMinimo(boolean deshabilitaValorMinimo) {
		this.deshabilitaValorMinimo = deshabilitaValorMinimo;
	}

	public boolean isDeshabilitaValorMaximo() {
		return deshabilitaValorMaximo;
	}

	public void setDeshabilitaValorMaximo(boolean deshabilitaValorMaximo) {
		this.deshabilitaValorMaximo = deshabilitaValorMaximo;
	}

	public String getEtiquetaValorPorMinimo() {
		return etiquetaValorPorMinimo;
	}

	public void setEtiquetaValorPorMinimo(String etiquetaValorPorMinimo) {
		this.etiquetaValorPorMinimo = etiquetaValorPorMinimo;
	}

	public String getEtiquetaValorPorMaximo() {
		return etiquetaValorPorMaximo;
	}

	public void setEtiquetaValorPorMaximo(String etiquetaValorPorMaximo) {
		this.etiquetaValorPorMaximo = etiquetaValorPorMaximo;
	}



}
