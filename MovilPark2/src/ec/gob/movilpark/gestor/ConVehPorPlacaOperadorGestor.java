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
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.filtros.Filtros;

public class ConVehPorPlacaOperadorGestor {
	private Filtros objFiltros;
	private List<EspacioPorParquimetro> lsEspacioPorParquimetro;
	
	
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
	}
	public long diferenciaEnDias2(Date fechaMayor, Date fechaMenor) {
		long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();
		long dias = diferenciaEn_ms / (1000 * 60 );
		return  dias;
		}
	@Command
	public void consultaPorPlaca(){
		if(objFiltros==null)
			return;
		if(objFiltros.getObjVehiculo()==null)
			return;
		if(objFiltros.getObjVehiculo().getPlaca()==null)
			return;
		
		System.out.println("consultaParquimetroPorUso!!!");
		long minutos=0;
		Listas listasDao= new Listas();
		Map<String,Object> map= new HashMap<String , Object>();
		map = listasDao.consultaParquimetroPorPlaca(objFiltros.getObjVehiculo().getPlaca().toUpperCase());
		
		if(map.get("error") == null){
			lsEspacioPorParquimetro=(List<EspacioPorParquimetro>) map.get("listaEspacio");
			if(lsEspacioPorParquimetro!=null)
				if(lsEspacioPorParquimetro.size()>0){
					
					minutos = diferenciaEnDias2(lsEspacioPorParquimetro.get(lsEspacioPorParquimetro.size()-1).getFechaFinal(),new Date());	
					if(minutos<0)
						minutos=0;
						Messagebox.show("La Placa "+objFiltros.getObjVehiculo().getPlaca()+" posee tiempo vigente."+
					"\n Ciudad: "+lsEspacioPorParquimetro.get(0).getCiudad()+
					"\n Areas: "+lsEspacioPorParquimetro.get(0).getAreas()+
					"\n Remanente: "+minutos+" minutos.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
				}
				else
					Messagebox.show("La Placa "+objFiltros.getObjVehiculo().getPlaca()+" NO posee tiempo vigente.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
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

	public Filtros getObjFiltros() {
		return objFiltros;
	}

	public void setObjFiltros(Filtros objFiltros) {
		this.objFiltros = objFiltros;
	}

	public List<EspacioPorParquimetro> getLsEspacioPorParquimetro() {
		return lsEspacioPorParquimetro;
	}

	public void setLsEspacioPorParquimetro(List<EspacioPorParquimetro> lsEspacioPorParquimetro) {
		this.lsEspacioPorParquimetro = lsEspacioPorParquimetro;
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
	
}
