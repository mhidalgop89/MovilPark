package ec.gob.movilpark.gestor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.Areas;
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.filtros.Filtros;

public class MantenimientoAreasGestor {
	
	
	private List<Provincia>listaProvincia;
	private List<Ciudad> listaCiudad;
	private List<Areas> listaAreas;
	
	private Provincia objProvincia;
	private Ciudad objCiudad;
	private Areas objAreas;
	private String nombreArchivo;
	
	private Filtros objFiltros;
	
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
		objFiltros = new Filtros();
		fillAreas();
		fillProvincia();
		fillCiudades();
		
	}
	
	public String getExtension(String fileName){
		String extension = "";

		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}
	

	
	
	@Command
	public void verArea(){
		try{
			
			
			
			
			if(objAreas==null)
				return;
			if(objAreas.getRutaImagen()==null){
				Messagebox.show("Esta area no posee imagen.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
				
			
			Map param= new HashMap();
			param.put("area", objAreas);
			
			Window win= (Window) Executions.getCurrent().createComponents("verArea.zul", null,param);
//			Window win= (Window) Executions.getCurrent().createComponents("image.html", null,null);
			win.doModal();
			win.setClosable(true);
		}catch(Exception e){e.printStackTrace();}
	}
	
	@Command
	public void saveToDisk() throws Exception {
		String extension="";
		org.zkoss.util.media.Media media = Fileupload.get();
		if(media==null)
			return;
//		nombreArchivo = "C:\\exports\\modulos\\movilpark\\imagenes\\"+media.getName();
		String so = System.getProperty("os.name"); 
		extension = getExtension(media.getName());
		if (so.contains("Windows"))
		{
			nombreArchivo = "C:\\exports\\modulos\\movilpark\\imagenes\\"+objAreas.getIdAreas()+"."+extension;
		}else{
			nombreArchivo = "opt/exports/modulos/movilpark/imagenes/"+objAreas.getIdAreas()+"."+extension;
			//input = new FileInputStream("/opt/movilpark/messages.properties");
		}
		
		File file = new File(nombreArchivo);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(media.getByteData());
		fos.close();
		objAreas.setRutaImagen(nombreArchivo);
		actualizarArea();
	}
	
	public void actualizarArea(){
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.editaAreas(objAreas);
		
		if(map.get("error") == null)
			Messagebox.show("Actualizado con exito.", "Atención", Messagebox.OK, Messagebox.INFORMATION);					
		else
			{
				Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		
	}
	
	@NotifyChange("listaAreas")
	@Command
	public void nuevaArea(){
		System.out.println("inicia nueva area");
		Window winAreas = (Window) Executions.createComponents("agregarArea.zul", null, null);
		winAreas.doModal();	
		fillAreas();
	}
	
	@NotifyChange("listaAreas")
	@Command
	public void editaArea(){
		
		System.out.println("inicia edita Area");
		if(objAreas==null)
			objAreas=new Areas();
			
			
		Map mapParametros = new HashMap();
		mapParametros.put("area",objAreas);
		Window winArea = (Window) Executions.createComponents("agregarArea.zul", null, mapParametros);
		winArea.doModal();	
		fillAreas();
		System.out.println("sale edita areas ");
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
	
public void fillCiudades(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		System.out.println("ciudades: "+  (	(objFiltros.getObjProvincia()==null)?"0":objFiltros.getObjProvincia().getProCodigo()));
		map = listasDao.obtieneCiudades("0", (	(objFiltros.getObjProvincia()==null)?"0":objFiltros.getObjProvincia().getProCodigo()));
			listaCiudad= new ArrayList<Ciudad>();
		
		if(map.get("error") == null){
			
			listaCiudad=(List<Ciudad>) map.get("ciudad");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}

@NotifyChange({"listaAreas","listaCiudad"})
@Command
public void filtraProvincia(){
	if(objFiltros.getObjProvincia()==null && objFiltros.getObjCiudad()==null){
		fillAreas();
		return;
	}
	
	fillCiudades();
	List<Areas> listaAreasAux= new ArrayList<Areas>();
	List<Areas> listaAreas= new ArrayList<Areas>();
	listaAreasAux.addAll(this.listaAreas);
	
		
	if(objFiltros.getObjProvincia()!=null){
		for(Areas area:listaAreasAux){
			
			if(area.getCodigoProvincia().equals(objFiltros.getObjProvincia().getProCodigo()))
				listaAreas.add(area);
		}
	this.listaAreas=listaAreas;
	System.out.println("size: "+listaAreas.size());
	}else{
		fillAreas();
		filtraCiudad();
	}
		
}


@NotifyChange("listaAreas")
@Command
public void filtraCiudad(){
	if(objFiltros.getObjProvincia()==null && objFiltros.getObjCiudad()==null){
		fillAreas();
		return;
	}
	
	List<Areas> listaAreasAux= new ArrayList<Areas>();
	List<Areas> listaAreas= new ArrayList<Areas>();
	listaAreasAux.addAll(this.listaAreas);
	if(objFiltros.getObjCiudad()!=null){
		for(Areas area:listaAreasAux){
			
			if(area.getCodigoCiudad().equals(objFiltros.getObjCiudad().getCiuCodigo()))
				listaAreas.add(area);
		}
	this.listaAreas=listaAreas;
	System.out.println("size: "+listaAreas.size());
	}else{
		fillAreas();
		filtraProvincia();
	}
		
}

@NotifyChange("listaAreas")
public void fillAreas(){
	
	Map<String,Object> map= new HashMap<String , Object>();
	Listas listasDao= new Listas();
	map = listasDao.obtieneAreas("0", "0");
		listaAreas= new ArrayList<Areas>();
	
	if(map.get("error") == null){
		
		listaAreas=(List<Areas>) map.get("areas");
				
	}else{
		Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
	}
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


	public Window getWin() {
		return win;
	}


	public void setWin(Window win) {
		this.win = win;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Filtros getObjFiltros() {
		return objFiltros;
	}

	public void setObjFiltros(Filtros objFiltros) {
		this.objFiltros = objFiltros;
	}
	

}
