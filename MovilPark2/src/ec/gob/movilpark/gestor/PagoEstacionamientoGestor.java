package ec.gob.movilpark.gestor;

import java.util.ArrayList;
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
import ec.gob.movilpark.dao.LlamaUsuario;
import ec.gob.movilpark.dto.Areas;
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.EspacioPorParquimetro;
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Tramite;
import ec.gob.movilpark.dto.Usuario;
import ec.gob.movilpark.dto.Vehiculo;

public class PagoEstacionamientoGestor {
	
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
	private List<Tramite> listaTramite;
	private List<Tramite> listaTramiteXveh;
	
	private boolean verArea=false;
	
	private List<Parquimetro> listaParquimetros;
	private Parquimetro objParquimetro;


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
		//fillProvincia();
		fillVehiculos();
//		fillParquimetro();
		fillCiudades();
		consultaTramite();
	}
	

	
	
	@NotifyChange({"listaVehiculos","objVehiculo"})
	@Command
	public void creaNuevaPlaca(){
		try{
			
			Map param= new HashMap();
			param.put("usuario", objSession.getUsuario());
			
			Window win= (Window) Executions.getCurrent().createComponents("mantenimientoPlaca.zul", null,param);
			win.doModal();
			win.setClosable(true);
			fillVehiculos();
		}catch(Exception e){e.printStackTrace();}
	}
	
	@NotifyChange("verArea")
	@Command
	public void verDivArea(){
		if(objArea==null)
			verArea=false;
		else
			verArea=true;
	}
	
	@NotifyChange({"listaVehiculos","objVehiculo"})
	@Command
	public void eliminaPlaca(){
		if(objVehiculo==null){
			Messagebox.show("No se ha seleccionado ningún vehiculo..", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		Listas listaDao = new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listaDao.inactivaVehiculo(objVehiculo.getVehiculoId());
		
		
		if(map.get("error") == null)
			Messagebox.show("Vehiculo eliminado.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
		else
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		fillVehiculos();
	}

	@NotifyChange("*")
	public void consultaTramite(){
		
		Listas listaDao = new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listaDao.consultaTramiteXusuario(objSession.getUsuario().getUsuarioId());
		
		Tramite objTramiteAnt = new Tramite();
		Areas objAreasAnt= new Areas();
		
		if(map.get("error") == null){
			listaTramite=(List<Tramite>) map.get("listTramite");
			if(listaTramite!=null)
				if(listaTramite.size()>0){
						objTramiteAnt = listaTramite.get(listaTramite.size()-1);
						objSession.setTramitePrevio(listaTramite.get(listaTramite.size()-1));
						objSession.setListaTramite(listaTramite);
				}
				else{
					objSession.setListaTramite(null);
					objSession.setTramitePrevio(null);
					return;
				}
			else{
				objSession.setListaTramite(null);
				objSession.setTramitePrevio(null);
				return;
			}
					
			
			for(Vehiculo veh: listaVehiculos){
				if(veh.getVehiculoId()==objTramiteAnt.getVehiculoId()){
						objVehiculo=veh;
						break;
				}
			}
			
			
			
			
			Map<String,Object> map2= new HashMap<String , Object>();
			Listas listasDao= new Listas();
			
			map2 = listasDao.obtieneAreas("0", "0");
			
			if(map2.get("error") == null)				
				listaAreas=(List<Areas>) map2.get("areas");
			else
				Messagebox.show(map2.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);		
			
			
			for(Areas area: listaAreas){
				if(area.getIdAreas()==objTramiteAnt.getIdAreas()){
					objAreasAnt=area;
					objArea=area;
					break;
				}				
			}
				
			for(Ciudad ciu : listaCiudades){
				if(ciu.getProCodigo().equals(objAreasAnt.getCodigoProvincia()))
					if(ciu.getCiuCodigo().equals(objAreasAnt.getCodigoCiudad())){
						objCiudad=ciu;
						break;
					}
//				System.out.println("111.."+ciu.getProCodigo()+" ** "+ciu.getCiuCodigo()+"***"
//					+objTramiteAnt.getIdAreas()+" ****"+objAreasAnt.getCodigoCiudad()+"*****"+objAreasAnt.getCodigoProvincia());
			}

			
			map2 = listasDao.obtieneAreas(objArea.getCodigoCiudad(), objArea.getCodigoProvincia());
						
						if(map2.get("error") == null)							
							listaAreas=(List<Areas>) map2.get("areas");
						else
							Messagebox.show(map2.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
//						
//						
//						for(Areas area: listaAreas){
//							if(area.getIdAreas()==objTramiteAnt.getIdAreas()){
//								objAreasAnt=area;
//								objArea=area;
//								return;
//							}				
//						}
				
			System.out.println("===?"+objArea.getNombre()+"---"+objSession.getUsuario().getUsuarioId()+" -- "+objTramiteAnt.getVehiculoId()+
					"--"+objTramiteAnt.getIdAreas()+"--"+objCiudad.getNombre()+"--"+objVehiculo.getPlaca()+"--"+objAreasAnt.getCodigoProvincia());
			
		}else{
			
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	@NotifyChange({"listaVehiculos","objVehiculo"})
	@Command
	public void verArea(){
		try{
			
			
			
			
			if(objArea==null)
				return;
			if(objArea.getRutaImagen()==null){
				Messagebox.show("Esta area no posee imagen.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
				
			
			Map param= new HashMap();
			param.put("area", objArea);
			
			Window win= (Window) Executions.getCurrent().createComponents("verArea.zul", null,param);
//			Window win= (Window) Executions.getCurrent().createComponents("image.html", null,null);
			win.doModal();
			win.setClosable(true);
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	@NotifyChange({"listaParquimetros","objParquimetro","objEspacioPorParquimetro","listaEspacioPorParquimetro"})
	@Command
	public void fillParquimetro(){
		objParquimetro=null;
		objEspacioPorParquimetro=null;
		listaEspacioPorParquimetro=null;
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();

		System.out.println(" objSession.getUsuario().getUsuarioId(): "+ objSession.getUsuario().getUsuarioId());
		if(objArea==null)return;
		
		System.out.println("prov: "+objCiudad.getProCodigo()+"ciudad: "+objCiudad.getCiuCodigo()+"area: "+objArea.getIdAreas());
		map = listasDao.consultaParquimetroParam(objCiudad.getProCodigo(), objCiudad.getCiuCodigo(), objArea.getIdAreas());
		listaParquimetros= new ArrayList<Parquimetro>();
		
		if(map.get("error") == null){
			
			listaParquimetros=(List<Parquimetro>) map.get("listaParquimetro");
			if(listaParquimetros.size()>0){
			objParquimetro=listaParquimetros.get(0);
			consultaEspacio();
			}
					
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
		listaEspacioPorParquimetro= new ArrayList<EspacioPorParquimetro>();
		
		if(map.get("error") == null){
			
			listaEspacioPorParquimetro=(List<EspacioPorParquimetro>) map.get("listaEspacio");
			objEspacioPorParquimetro=listaEspacioPorParquimetro.get(0);
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	@NotifyChange({"listaVehiculos","objVehiculo"})
	public void fillVehiculos(){
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();

		System.out.println(" objSession.getUsuario().getUsuarioId(): "+ objSession.getUsuario().getUsuarioId());
		
		map = listasDao.obtieneVehiculos( objSession.getUsuario().getUsuarioId(), 0);
		listaVehiculos= new ArrayList<Vehiculo>();
		
		if(map.get("error") == null){
			
			listaVehiculos=(List<Vehiculo>) map.get("vehiculo");
			if(listaVehiculos!=null)
				if(listaVehiculos.size()>0)
					objVehiculo=listaVehiculos.get(0);
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@NotifyChange({"listaProvincia","objProvincia"})
	public void fillProvincia(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.obtieneProvincias(0);
		listaProvincia= new ArrayList<Provincia>();
		
		if(map.get("error") == null){
			
			listaProvincia=(List<Provincia>) map.get("provincia");
			objProvincia=listaProvincia.get(0);
			//fillCiudades();
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@NotifyChange({"listaCiudades","objCiudad","objArea","objParquimetro","objEspacioPorParquimetro","listaParquimetros","listaEspacioPorParquimetro"})
	@Command
public void fillCiudades(){
		
		objCiudad=null;
		objArea=null;
		objParquimetro=null;
		objEspacioPorParquimetro=null;
		listaParquimetros = null;
		listaEspacioPorParquimetro=null;
//		if(objProvincia==null)return;
		System.out.println("entra fillCiudad!!!!");
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.obtieneCiudadesParquimetro("0", "0");
				listaCiudades= new ArrayList<Ciudad>();

		
		if(map.get("error") == null){
			
			listaCiudades=(List<Ciudad>) map.get("ciudad");
			
			
			objCiudad=listaCiudades.get(0);
			
			fillProvincia();
			for(int i=0;i<listaProvincia.size();i++)
			{
				if(objCiudad.getProCodigo()==listaProvincia.get(i).getProCodigo());
				{
					objProvincia=listaProvincia.get(i);
					break;
				}
			}
			
			
			fillAreas();
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	
	
	@NotifyChange({"listaAreas","objArea","objParquimetro","objEspacioPorParquimetro","listaParquimetros","listaEspacioPorParquimetro"})
	@Command
	public void fillAreas(){
		objArea=null;
		objParquimetro=null;
		objEspacioPorParquimetro=null;
		listaParquimetros = null;
		listaEspacioPorParquimetro=null;
		listaAreas=null;
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		if(objCiudad==null)	return;
		System.out.println("fillAreas: objCiudad.getCiuCodigo(): "+objCiudad.getCiuCodigo()+"  objProvincia.getProCodigo():"+ objProvincia.getProCodigo());
//		map = listasDao.obtieneAreas(objCiudad.getCiuCodigo(), objProvincia.getProCodigo());
		map = listasDao.obtieneAreas(objCiudad.getCiuCodigo(), objCiudad.getProCodigo());
		
			//listaAreas= new ArrayList<Areas>();
		
		if(map.get("error") == null){
			
			listaAreas=(List<Areas>) map.get("areas");
			//objArea= listaAreas.get(0);		
			//fillParquimetro();
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	
	@Command
	public void onAtras(){
		Executions.getCurrent().sendRedirect("/Aplicacion/parquimetro.zul");
		
	}
	
	@Command
	public void onSiguiente(){

		if(objVehiculo==null){
			Messagebox.show("Favor seleccione placa", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		
//		if(objParquimetro==null){
//			Messagebox.show("Favor seleccione parquimetro", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
//			return;
//		}
		
//		
//		if(objProvincia==null){
//			Messagebox.show("Favor seleccione provincia", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
//			return;
//		}
		if(objCiudad==null){
			Messagebox.show("Favor seleccione ciudad", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(objArea==null){
			Messagebox.show("Favor seleccione area", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
//		Consulta si el vehiculo esta siendo usado en otro lugar (no deja pasar en caso de que se este usando en ese lugar)		
//		Listas listaDao = new Listas();
//		Map<String,Object> map= new HashMap<String , Object>();
//		map = listaDao.consultaTramiteXusuario(objVehiculo.getVehiculoId());
//		Tramite objTramite;
//		
//		if(map.get("error") == null){
//			listaTramite=(List<Tramite>) map.get("listTramite");
//			if(listaTramite!=null)
//				if(listaTramite.size()>0){
//					objTramite = listaTramite.get(listaTramite.size()-1);
//					if(objTramite.getIdAreas() != objArea.getIdAreas()){
//						Messagebox.show("Este vehiculo está siendo usado en otra area", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
//						return;
//					}
//				}
//		}
		
		
//		if(objEspacioPorParquimetro==null){
//			Messagebox.show("Favor seleccione espacio de parquimetro", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
//			return;
//		}
		
	/*	
		//se consulta si el espacio esta ocupado
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.consultaExisteTramite(objParquimetro.getIdParquimetro(), objEspacioPorParquimetro.getIdEspacioPorParquimetro());
		List<Tramite>listaTramite;
		System.out.println("Datos existe tramite: "+objParquimetro.getIdParquimetro()+"-"+objEspacioPorParquimetro.getIdEspacioPorParquimetro()
		+"-"+objVehiculo.getVehiculoId());
		
		if(map.get("error") == null){
			
			listaTramite=(List<Tramite>) map.get("listTramite");
//			if(listaTramite==null){
//				Messagebox.show("Este espacio ya esta ocupado.", "Atención", Messagebox.OK, Messagebox.ERROR);
//				return;
//			}
			if(listaTramite.size()>0){
				if(listaTramite.get(listaTramite.size()-1).getIdUsuario()!=objSession.getUsuario().getUsuarioId())
				{					
					Messagebox.show("Este espacio ya esta ocupado.", "Atención", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				objSession.setTramitePrevio(listaTramite.get(listaTramite.size()-1));
				
			}
			
			
			
			
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
		*/
			
			
			objSession.setVehiculo(objVehiculo);
			objSession.setParquimetro(objParquimetro);
			objSession.setEspacioPorParquimetro(objEspacioPorParquimetro);
			objSession.setProvincia(objProvincia);
			objSession.setCiudad(objCiudad);
			objSession.setArea(objArea);
			
			
			Executions.getCurrent().getSession().setAttribute("session", objSession);
		
		Executions.getCurrent().sendRedirect("/Aplicacion/seleccionaTarifa.zul");
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

	public Provincia getObjProvincia() {
		return objProvincia;
	}

	public void setObjProvincia(Provincia objProvincia) {
		this.objProvincia = objProvincia;
	}

	public List<Vehiculo> getListaVehiculos() {
		return listaVehiculos;
	}

	public void setListaVehiculos(List<Vehiculo> listaVehiculos) {
		this.listaVehiculos = listaVehiculos;
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

	public EspacioPorParquimetro getObjEspacioPorParquimetro() {
		return objEspacioPorParquimetro;
	}

	public void setObjEspacioPorParquimetro(EspacioPorParquimetro objEspacioPorParquimetro) {
		this.objEspacioPorParquimetro = objEspacioPorParquimetro;
	}

	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
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




	public boolean isVerArea() {
		return verArea;
	}




	public void setVerArea(boolean verArea) {
		this.verArea = verArea;
	}




	public List<Tramite> getListaTramite() {
		return listaTramite;
	}




	public void setListaTramite(List<Tramite> listaTramite) {
		this.listaTramite = listaTramite;
	}



	
	
	
	
}
