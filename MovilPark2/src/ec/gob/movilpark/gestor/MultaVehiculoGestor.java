package ec.gob.movilpark.gestor;

import java.util.Date;
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
import ec.gob.movilpark.dto.EspacioPorParquimetro;
import ec.gob.movilpark.dto.MultaVehiculo;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Vehiculo;
import ec.gob.movilpark.filtros.Filtros;

public class MultaVehiculoGestor {
	
	private List<EspacioPorParquimetro> lsEspacioPorParquimetro;
	private List<Vehiculo> listaVehiculos;
	private MultaVehiculo objMultaVeh;
	private Vehiculo objVehiculo;
	private boolean poseeTiempoVigente=false;
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
		
		consultaVehiculos();
	}
	
	@NotifyChange("listaVehiculos")
	public void consultaVehiculos(){
	
		Listas listasDao= new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		//map = listasDao.obtieneVehiculosMulta(0, 0);
		map = listasDao.obtieneVehiculos(0, 0);
		
		if(map.get("error") == null){
			listaVehiculos=(List<Vehiculo>) map.get("vehiculo");
			
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
		map = listasDao.consultaParquimetroPorPlaca(objVehiculo.getPlaca());
		
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
	public void multaVehiculo(){
		
		if(objVehiculo==null){
			Messagebox.show("Porfavor Seleccione un Vehiculo", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		consultaPorPlaca();
		
		if(poseeTiempoVigente){
			Messagebox.show("El vehiculo con placa "+objVehiculo.getPlaca()+" posee tiempo vigente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		objMultaVeh = new MultaVehiculo();
		objMultaVeh.setVehiculoId(objVehiculo.getVehiculoId());
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


	public List<Vehiculo> getListaVehiculos() {
		return listaVehiculos;
	}

	public void setListaVehiculos(List<Vehiculo> listaVehiculos) {
		this.listaVehiculos = listaVehiculos;
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

	public Vehiculo getObjVehiculo() {
		return objVehiculo;
	}

	public void setObjVehiculo(Vehiculo objVehiculo) {
		this.objVehiculo = objVehiculo;
	}

	public MultaVehiculo getObjMultaVeh() {
		return objMultaVeh;
	}

	public void setObjMultaVeh(MultaVehiculo objMultaVeh) {
		this.objMultaVeh = objMultaVeh;
	}

	public List<EspacioPorParquimetro> getLsEspacioPorParquimetro() {
		return lsEspacioPorParquimetro;
	}

	public void setLsEspacioPorParquimetro(List<EspacioPorParquimetro> lsEspacioPorParquimetro) {
		this.lsEspacioPorParquimetro = lsEspacioPorParquimetro;
	}

	public boolean isPoseeTiempoVigente() {
		return poseeTiempoVigente;
	}

	public void setPoseeTiempoVigente(boolean poseeTiempoVigente) {
		this.poseeTiempoVigente = poseeTiempoVigente;
	}

}
