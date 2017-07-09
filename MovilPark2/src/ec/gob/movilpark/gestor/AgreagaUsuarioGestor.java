package ec.gob.movilpark.gestor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import ec.gob.movilpark.dao.PerfilesDao;
import ec.gob.movilpark.dto.Areas;
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.Perfil;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Usuario;

public class AgreagaUsuarioGestor {
	
	private Session objSession;
	Window win;
	private String nombrePerfil;
	private boolean disabled=false;
	private Usuario objUsuario= new Usuario();
	private Usuario objUsuarioParam;
	private String estado="A";
	private boolean habilitaUsuario=false;
	
	private List<Provincia>listaProvincia;
	private List<Ciudad> listaCiudad;
	private List<Areas> listaAreas;
	
	private List<Perfil> listaPerfiles;
	
	private Provincia objProvincia;
	private Ciudad objCiudad;
	private Areas objAreas;
	private Perfil objPerfil;
	private boolean desactivaPerfil=true;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	

	@Init
	@NotifyChange("*")
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("usuario") Usuario objUsuarioParam)
	{
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
				return;
			}
	
		fillProvincia();
		consultaPerfiles();
		if(objUsuarioParam!=null){
			desactivaPerfil = true;
			this.objUsuarioParam=objUsuarioParam;
			this.objUsuario=objUsuarioParam;
			habilitaUsuario=true;
			//nombrePerfil=this.objPerfil.getNombre();
			if("A".equals(objUsuarioParam.getEstado()))
				estado="A";
			else
				estado="I";
			for(int i=0;i<listaPerfiles.size();i++){
				if(objUsuario.getPerId()==listaPerfiles.get(i).getIdPerfil()){
					objPerfil=listaPerfiles.get(i);
					break;
				}
			}
			
			if(objUsuario.getCodigoProvincia()!=null){
				for(int i=0;i<listaProvincia.size();i++)
					if(listaProvincia.get(i).getProCodigo().equals(objUsuario.getCodigoProvincia())){
						objProvincia= listaProvincia.get(i);
						objUsuario.setProvincia(listaProvincia.get(i).getNombre());
					}
				
				fillCiudades();
				for(int i=0;i<listaCiudad.size();i++)
					if(listaCiudad.get(i).getProCodigo().equals(objUsuario.getCodigoProvincia()) && listaCiudad.get(i).getCiuCodigo().equals(objUsuario.getCodigpCiudad())){
						objCiudad= listaCiudad.get(i);
						objUsuario.setProvincia(listaCiudad.get(i).getNombre());
					}			
			}
		}
		else{
			this.objUsuarioParam=objUsuarioParam;
			estado="A";
			disabled=true;
			desactivaPerfil=false;
//			cmbEstadoPerfil.setSelectedIndex(0);
//			cmbEstadoPerfil.setDisabled(true);
			
		}
		
		
	}
	
	
	public boolean validateEmail(String email) {
		try{
		    // Compiles the given regular expression into a pattern.
		    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		    // Match the given input against this pattern
		    Matcher matcher = pattern.matcher(email);
		    return matcher.matches();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@NotifyChange("listaPerfiles")
	public void consultaPerfiles(){
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.consultaPerfiles();
		listaPerfiles= new ArrayList<Perfil>();
		
		if(map.get("error") == null){
			
			listaPerfiles=(List<Perfil>) map.get("perfil");
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
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
	
	@NotifyChange({"listaCiudad","objCiudad"})	
	@Command
	public void fillCiudades(){
		
		listaCiudad=null;
		objCiudad=null;
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
	public void guardarUsuario(){
		System.out.println("inicia guardarUsuario");
		if(objUsuario.getIdentificacion()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objUsuario.getNombre()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objUsuario.getApellido()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objUsuario.getEmail()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objUsuario.getUsuario()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objUsuario.getPass()==null){
			Messagebox.show("Debe llenar todos los campos obligatorios.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objProvincia==null){
			Messagebox.show("Campo provincia es obligatorio.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		if(objCiudad==null){
			Messagebox.show("Campo Ciudad es obligatorio.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objPerfil==null){
			Messagebox.show("Campo Perfil es obligatorio.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
			return;
		}
		
		if(objPerfil.getIdPerfil()==3){
			if(!validateEmail(objUsuario.getUsuario())){
				Messagebox.show("Si el perfil es MÓVIL el usuario debe poseer formato de e-mail.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);	
				return;
			}
		}
		
		objUsuario.setCodigoProvincia(objProvincia.getProCodigo());
		objUsuario.setCodigpCiudad(objCiudad.getCiuCodigo());
		objUsuario.setPerId(objPerfil.getIdPerfil());
		//objUsuario.setIdTipoUsuario(objPerfil.getIdPerfil());
		
		
		Map<String,Object> map= new HashMap<String , Object>();
		Map<String,Object> mapConsulta= new HashMap<String , Object>();
		LlamaUsuario usuarioDao= new LlamaUsuario();
		boolean existe=false;
		if(this.objUsuarioParam==null){
			mapConsulta = usuarioDao.consultaExisteUsuario(objUsuario.getUsuario());
			if(mapConsulta.get("error") == null)
				{
					existe = (boolean) mapConsulta.get("existe");
					if(existe){
						Messagebox.show("Nombre de usuario ya existe.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
						return;					
					}
								
				}
			else
				{
					Messagebox.show(mapConsulta.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
					return;
				}
		}

		
		
		if(this.objUsuarioParam==null){
			objUsuario.setUsuarioIngresa(objSession.getUsuario().getUsuario());
			objUsuario.setFechaIngresa(new Date());
			System.out.println("ingresa");
			objUsuario.setIdTipoUsuario(1);
			map = usuarioDao.insertaUsuario(objUsuario);
		}
		else{
			objUsuario.setUsuarioModifica(objSession.getUsuario().getUsuario());
			objUsuario.setFechaModifica(new Date());
			objUsuario.setEstado(estado);
			
			System.out.println("actualiza"+objUsuario.getUsuarioId());
			map = usuarioDao.editaUsuario(objUsuario);
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
	
	@Command
	public void salir(){
		
		win.detach();
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




	public String getNombrePerfil() {
		return nombrePerfil;
	}




	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}




	public boolean isDisabled() {
		return disabled;
	}




	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Usuario getObjUsuario() {
		return objUsuario;
	}

	public void setObjUsuario(Usuario objUsuario) {
		this.objUsuario = objUsuario;
	}

	public Usuario getObjUsuarioParam() {
		return objUsuarioParam;
	}

	public void setObjUsuarioParam(Usuario objUsuarioParam) {
		this.objUsuarioParam = objUsuarioParam;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isHabilitaUsuario() {
		return habilitaUsuario;
	}

	public void setHabilitaUsuario(boolean habilitaUsuario) {
		this.habilitaUsuario = habilitaUsuario;
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


	public List<Perfil> getListaPerfiles() {
		return listaPerfiles;
	}


	public void setListaPerfiles(List<Perfil> listaPerfiles) {
		this.listaPerfiles = listaPerfiles;
	}


	public Perfil getObjPerfil() {
		return objPerfil;
	}


	public void setObjPerfil(Perfil objPerfil) {
		this.objPerfil = objPerfil;
	}


	public boolean isDesactivaPerfil() {
		return desactivaPerfil;
	}


	public void setDesactivaPerfil(boolean desactivaPerfil) {
		this.desactivaPerfil = desactivaPerfil;
	}




}
