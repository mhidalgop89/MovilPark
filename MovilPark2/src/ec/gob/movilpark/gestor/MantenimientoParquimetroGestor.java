package ec.gob.movilpark.gestor;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dao.LlamaUsuario;
import ec.gob.movilpark.dto.Areas;
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Usuario;

public class MantenimientoParquimetroGestor {
	
	private List<Provincia>listaProvincia;
	private List<Ciudad> listaCiudad;
	private List<Areas> listaAreas;
	
	private Provincia objProvincia;
	private Ciudad objCiudad;
	private Areas objAreas;
	
	private List<Parquimetro>lsParquimetro;
	private Parquimetro objParquimetro;
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
		
		consultaParquimetro();
		
	}
	
	@NotifyChange("lsParquimetro")
	@Command
	public void nuevoParquimetro(){
		System.out.println("inicia nuevoParquimetro");
		Window winParq = (Window) Executions.createComponents("agregarParquimetro.zul", null, null);
		winParq.doModal();	
		consultaParquimetro();
		
	}

	@NotifyChange("lsParquimetro")
	@Command
	public void editaParquimetro(){
		
		System.out.println("inicia editaParquimetro");
		if(objParquimetro==null)
			objParquimetro=new Parquimetro();
			
			
		Map mapParametros = new HashMap();
		mapParametros.put("parquimetro",objParquimetro);
		Window winParq = (Window) Executions.createComponents("agregarParquimetro.zul", null, mapParametros);
		winParq.doModal();	
		consultaParquimetro();
		System.out.println("sale editaUsuario ");
	}
	
	@NotifyChange("lsParquimetro")
	public void consultaParquimetro(){
		
		
		Listas listasDao= new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listasDao.consultaParquimetro();
		
		if(map.get("error") == null){
			lsParquimetro=(List<Parquimetro>) map.get("listaParquimetro");
			for(int i=0;i<lsParquimetro.size();i++){
				
				for(int j=0;j<listaProvincia.size();j++){
					if(lsParquimetro.get(i).getProvinciaCodigo().equals(listaProvincia.get(j).getProCodigo())){
						lsParquimetro.get(i).setNombreProvincia(listaProvincia.get(j).getNombre());
						objProvincia=listaProvincia.get(j);
							}
						}
				fillCiudades();
				for(int j=0;j<listaCiudad.size();j++){
					if(lsParquimetro.get(i).getCiudadCodigo().equals(listaCiudad.get(j).getCiuCodigo())){
						lsParquimetro.get(i).setNombreCiudad(listaCiudad.get(j).getNombre());
						objCiudad=listaCiudad.get(j);
					}
				}
				fillAreas();
				for(int j=0;j<listaAreas.size();j++){
					if(lsParquimetro.get(i).getIdAreas()==listaAreas.get(j).getIdAreas()){
						lsParquimetro.get(i).setNombreArea(listaAreas.get(j).getNombre());
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

	public List<Parquimetro> getLsParquimetro() {
		return lsParquimetro;
	}

	public void setLsParquimetro(List<Parquimetro> lsParquimetro) {
		this.lsParquimetro = lsParquimetro;
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

	public Parquimetro getObjParquimetro() {
		return objParquimetro;
	}

	public void setObjParquimetro(Parquimetro objParquimetro) {
		this.objParquimetro = objParquimetro;
	}

}
