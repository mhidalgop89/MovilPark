package ec.gob.movilpark.gestor;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Session;

public class MapaGestor {
	
	private Session objSession;
	private Double lat=-0.23813317003436887;
	private Double lng=-78.39637756347656;
//	@Wire
//	private Gmaps mymap= new Gmaps();
//	@Wire
//	private Window winMapa= new Window();
//	@Wire
//	private Div divMap= new Div();
	
	Window win;
	

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view)
	{
		
		Selectors.wireComponents(view, this, false);
		win =(Window) view;
		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
				return;
			}
//		winMapa.setWidth("100%");
//		winMapa.setHeight("100%");
//		divMap.setWidth("100%");
//		divMap.setHeight("100%");
//		mymap.setWidth("100%");
//		mymap.setHeight("100%");
//		divMap.appendChild(mymap);
//		winMapa.appendChild(divMap);
//		winMapa.invalidate();
		
	}
	
	
	@Command
	public void salir(){
		objSession.setLatitud(lat);
		objSession.setLongitud(lng);
		Executions.getCurrent().getSession().setAttribute("session", objSession);
		win.detach();
	}


	public Session getObjSession() {
		return objSession;
	}


	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}


	public Double getLat() {
		return lat;
	}


	public void setLat(Double lat) {
		this.lat = lat;
	}


	public Double getLng() {
		return lng;
	}


	public void setLng(Double lng) {
		this.lng = lng;
	}


	public Window getWin() {
		return win;
	}


	public void setWin(Window win) {
		this.win = win;
	}

}
