package ec.gob.movilpark.gestor;

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
import ec.gob.movilpark.dao.LlamaUsuario;
import ec.gob.movilpark.dto.NotaCredito;
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Usuario;

public class NotaCreditoGestor {
	
	private List<Usuario>lsUsuarios;
	private String filtroConsulta="";
	private List<NotaCredito> lsNotasCredito;
	private NotaCredito objNotaCredito;
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
		
		consultaUsuarios();
		consultaNotasCredito();
		
	}
	
	@NotifyChange("lsNotasCredito")
	@Command
	public void consultaFiltroNotaCredito(){
		System.out.println("filtroConsulta: "+filtroConsulta);
		if("TODAS".equals(filtroConsulta))
			consultaNotasCredito();
		if("COMHOY".equals(filtroConsulta))
			consultaNotasCreditoCompradasDia();
		if("USAHOY".equals(filtroConsulta))
			consultaNotasCreditoUsadasDia();
		
	}
	
	public void consultaUsuarios(){
		LlamaUsuario usuariosDao= new LlamaUsuario();
		Map<String,Object> map= new HashMap<String , Object>();
		map = usuariosDao.consultaUsuario();
		
		if(map.get("error") == null){
			lsUsuarios=(List<Usuario>) map.get("usuarios");
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
	}
	
	@NotifyChange("lsNotasCredito")
	@Command
	public void editaNotaCredito(){
		
		System.out.println("inicia editaNotaCredito");
		if(objNotaCredito==null)
			objNotaCredito=new NotaCredito();
			
			
		Map mapParametros = new HashMap();
		mapParametros.put("notaCredito",objNotaCredito);
		Window winParq = (Window) Executions.createComponents("agregarNotasCredito.zul", null, mapParametros);
		winParq.doModal();	
		consultaNotasCredito();
		System.out.println("sale editaUsuario ");
	}
	
	
	@NotifyChange("lsNotasCredito")
	@Command
	public void nuevaNotaCredito(){
		System.out.println("inicia nuevoNotaCredito");
		Window winParq = (Window) Executions.createComponents("agregarNotasCredito.zul", null, null);
		winParq.doModal();	
		consultaNotasCredito();
		
	}
	
	
	public void consultaNotasCredito(){
		Listas listasDao= new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listasDao.consultaNotasCreditos();
		
		
		
		if(map.get("error") == null){
			lsNotasCredito=(List<NotaCredito>) map.get("listaNotaCredito");
			for(int i=0;i<lsNotasCredito.size();i++){
				
				for(int j=0;j<lsUsuarios.size();j++){
					if(lsNotasCredito.get(i).getUsuarioId()==lsUsuarios.get(j).getUsuarioId()){
						lsNotasCredito.get(i).setNombreUsuario(lsUsuarios.get(j).getNombre());
						lsNotasCredito.get(i).setUsuario(lsUsuarios.get(j).getUsuario());						
					}
				}
			}			
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}		
	}
	
	
	public void consultaNotasCreditoUsadasDia(){
		Listas listasDao= new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listasDao.consultaNotasCreditosUsadasDuranteDia();
		
		
		
		if(map.get("error") == null){
			lsNotasCredito=(List<NotaCredito>) map.get("listaNotaCredito");
			for(int i=0;i<lsNotasCredito.size();i++){
				
				for(int j=0;j<lsUsuarios.size();j++){
					if(lsNotasCredito.get(i).getUsuarioId()==lsUsuarios.get(j).getUsuarioId()){
						lsNotasCredito.get(i).setNombreUsuario(lsUsuarios.get(j).getNombre());
						lsNotasCredito.get(i).setUsuario(lsUsuarios.get(j).getUsuario());						
					}
				}
			}			
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}		
	}
	public void consultaNotasCreditoCompradasDia(){
		Listas listasDao= new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listasDao.consultaNotasCreditosCompDuranteDia();
		
		
		
		if(map.get("error") == null){
			lsNotasCredito=(List<NotaCredito>) map.get("listaNotaCredito");
			System.out.println("size de nc: "+lsNotasCredito.size());
			for(int i=0;i<lsNotasCredito.size();i++){
				
				for(int j=0;j<lsUsuarios.size();j++){
					if(lsNotasCredito.get(i).getUsuarioId()==lsUsuarios.get(j).getUsuarioId()){
						lsNotasCredito.get(i).setNombreUsuario(lsUsuarios.get(j).getNombre());
						lsNotasCredito.get(i).setUsuario(lsUsuarios.get(j).getUsuario());						
					}
				}
			}			
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}		
	}
	
	public List<Usuario> getLsUsuarios() {
		return lsUsuarios;
	}
	public void setLsUsuarios(List<Usuario> lsUsuarios) {
		this.lsUsuarios = lsUsuarios;
	}
	public List<NotaCredito> getLsNotasCredito() {
		return lsNotasCredito;
	}
	public void setLsNotasCredito(List<NotaCredito> lsNotasCredito) {
		this.lsNotasCredito = lsNotasCredito;
	}
	public NotaCredito getObjNotaCredito() {
		return objNotaCredito;
	}
	public void setObjNotaCredito(NotaCredito objNotaCredito) {
		this.objNotaCredito = objNotaCredito;
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
	public String getFiltroConsulta() {
		return filtroConsulta;
	}
	public void setFiltroConsulta(String filtroConsulta) {
		this.filtroConsulta = filtroConsulta;
	}

}
