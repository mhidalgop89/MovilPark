package ec.gob.movilpark.gestor;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.Areas;
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.util.Redondear;

public class AgregarAreaGestor {
	
	private String nombreArchivo;
	private List<Provincia>listaProvincia;
	private List<Ciudad> listaCiudad;
	private List<Areas> listaAreas;
	
	private Provincia objProvincia;
	private Ciudad objCiudad;
	private Areas objAreas;
	private Areas objAreasParam;
	
	private boolean disabled=false;
	 
	private String estado;
	private Session objSession;
	
	private boolean deshabilitaValorMinimo=true;
	private boolean deshabilitaValorMaximo=true;
	private String etiquetaValorPorMinimo;
	private String etiquetaValorPorMaximo;
	Window win;

	@Init
	@NotifyChange("*")
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("area") Areas objAreasParam)
	{ 
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
				return;
			}
		fillProvincia();
		
		if(objAreasParam==null){
			disabled=true;
			estado="A";
			objAreas= new Areas();
		}
		else{
			this.objAreasParam=objAreasParam;
			objAreas=objAreasParam;
			disabled=false;
			deshabilitaValorMinimo=false;
			deshabilitaValorMaximo=false;
			
				for(int j=0; j<listaProvincia.size();j++){
					if(objAreas.getCodigoProvincia().equals(listaProvincia.get(j).getProCodigo())){
						objAreas.setNombreProvincia(listaProvincia.get(j).getNombre());
						objProvincia=listaProvincia.get(j);
					}	
				}
			fillCiudades();
				for(int j=0; j<listaCiudad.size();j++){
					if(objAreas.getCodigoCiudad().equals(listaCiudad.get(j).getCiuCodigo())){
						objAreas.setNombreProvincia(listaCiudad.get(j).getNombre());
						objCiudad=listaCiudad.get(j);
					}	
				}
				
				if("A".equals(objAreas.getEstado()))
					estado="A";
				else
					estado="I";
			
		}
			
		
	}
	
	
	@Command
	public void saveToDisk() throws Exception {
		org.zkoss.util.media.Media media = Fileupload.get();
		if(media==null)
			return;
		nombreArchivo = "C:\\exports\\modulos\\movilpark\\imagenes\\"+media.getName();
		File file = new File(nombreArchivo);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(media.getByteData());
		fos.close();
	}
	
	@NotifyChange({"etiquetaValorPorMaximo"})
	@Command
	public void calculaTiempoMaximoSegunValor(){
		
		if(objAreas==null)
			return;
		if(objAreas.getValorMaximo()==null)
			{
				objAreas.setValorMaximo(0.0);
				return;
			}
		if(objAreas.getValorMaximo()<=0)
			{
				Messagebox.show("Campo Valor Maximo debe de llevar valores mayores a 0.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
				objAreas.setValorMaximo(0.0);
				return;
			}
		
		
		Double tiempoMax=0.0;
		tiempoMax=(60*objAreas.getValorMaximo())/(objAreas.getValorPorHora());
		System.out.println("tiempoMax sin redondear: "+tiempoMax);
		Redondear red= new Redondear();
		tiempoMax=red.Redondear2(tiempoMax);
		
		etiquetaValorPorMaximo=tiempoMax.toString()+" minutos";
		System.out.println("etiquetaValorPorMaximo: "+etiquetaValorPorMaximo);
	}
	
	
	@NotifyChange({"etiquetaValorPorMinimo"})
	@Command
	public void calculaTiempoMinimoSegunValor(){
		if(objAreas==null)
			return;
		if(objAreas.getValorMinimo()==null)
			{
				objAreas.setValorMinimo(0.0);
				return;
			}
		if(objAreas.getValorMinimo()<=0)
			{
				Messagebox.show("Campo Valor Minimo debe de llevar valores mayores a 0.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
				objAreas.setValorMinimo(0.0);
				return;
			}
		
		
		Double tiempoMin=0.0;
		tiempoMin=(60*objAreas.getValorMinimo())/(objAreas.getValorPorHora());
		Redondear red= new Redondear();
		tiempoMin=red.Redondear2(tiempoMin);
		etiquetaValorPorMinimo=tiempoMin.toString()+" minutos";
		System.out.println("etiquetaValorPorMinimo: "+etiquetaValorPorMinimo);
	}
	
	@NotifyChange({"deshabilitaValorMaximo","deshabilitaValorMinimo","objParquimetro","etiquetaValorPorMinimo","etiquetaValorPorMaximo"})
	@Command
	public void llenaValorPorHora(){
		
		
		
		if(objAreas==null)
			return;
		if(objAreas.getValorPorHora()==null)
			{
				objAreas.setValorMinimo(0.0);
				objAreas.setValorMaximo(0.0);
				deshabilitaValorMaximo=true;
				deshabilitaValorMinimo=true;
				return;
			}
		if(objAreas.getValorPorHora()<=0)
			{
			Messagebox.show("Campo Valor por Hora debe de llevar valores mayores a 0.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			objAreas.setValorMinimo(0.0);
			objAreas.setValorMaximo(0.0);
			deshabilitaValorMaximo=true;
			deshabilitaValorMinimo=true;
				return;
			}
		
		
		calculaTiempoMinimoSegunValor();
		calculaTiempoMaximoSegunValor();
		deshabilitaValorMaximo=false;
		deshabilitaValorMinimo=false;
		
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
	
	@NotifyChange("listaCiudad")	
	@Command
	public void fillCiudades(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		if(objProvincia==null)return;
		map = listasDao.obtieneCiudades("0", objProvincia.getProCodigo());
			listaCiudad= new ArrayList<Ciudad>();
		
		if(map.get("error") == null){
			
			listaCiudad=(List<Ciudad>) map.get("ciudad");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Command
	public void guardarArea(){
		
		
		if(objAreas==null){
			Messagebox.show("Debe llenar los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if(objProvincia==null){
			Messagebox.show("Campo Provincia es obligatorio.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if(objCiudad==null){
			Messagebox.show("Campo Ciudad es obligatorio.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if(objAreas.getNombre()==null){
			Messagebox.show("Campo Nombre es obligatorio.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		
		
		
		if(objAreas.getValorMinimo()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objAreas.getValorMaximo()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objAreas.getValorMinimo()==0){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objAreas.getValorMaximo()==0){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objAreas.getValorPorHora()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objAreas.getValorPorHora()<=0){
			Messagebox.show("Debe llenar todos los campos obligatorios. Recuerde que el campo Valor por Hora no debe tener valores "
					+ "menores o iguales a Cero", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objAreas.getValorMaximo()<=0){
			Messagebox.show("El campo Valor Minimo No debe poseer valores menores o iguales a Cero.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objAreas.getValorMinimo()<=0){
			Messagebox.show("El campo Valor Maximo No debe poseer valores menores o iguales a Cero.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objAreas.getValorMinimo()>objAreas.getValorMaximo()){
			Messagebox.show("El campo Valor Maximo No debe ser menor a Valor Minimo.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		objAreas.setCodigoCiudad(objCiudad.getCiuCodigo());
		objAreas.setCodigoProvincia(objProvincia.getProCodigo());
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		
		
		if(objAreasParam==null){
			objAreas.setEstado("A");
			
			map = listasDao.insertaAreas(objAreas);
			
			if(map.get("error") == null)
				Messagebox.show("Almacenado con exito.", "Atención", Messagebox.OK, Messagebox.INFORMATION);					
			else
				{
					Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
					return;
				}
		}
		else{
			objAreas.setEstado(estado);
			
			map = listasDao.editaAreas(objAreas);
			
			if(map.get("error") == null)
				Messagebox.show("Almacenado con exito.", "Atención", Messagebox.OK, Messagebox.INFORMATION);					
			else
				{
					Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
					return;
				}
		}
		salir();
		
	}

	
	@Command
	public void salir(){
		win.detach();
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

	public Areas getObjAreas() {
		return objAreas;
	}

	public void setObjAreas(Areas objAreas) {
		this.objAreas = objAreas;
	}

	public Session getObjSession() {
		return objSession;
	}

	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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


	public Areas getObjAreasParam() {
		return objAreasParam;
	}


	public void setObjAreasParam(Areas objAreasParam) {
		this.objAreasParam = objAreasParam;
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
