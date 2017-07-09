package ec.gob.movilpark.gestor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.chart.Charts;
import org.zkoss.chart.LinearGradient;
import org.zkoss.chart.PaneBackground;
import org.zkoss.chart.YAxis;
import org.zkoss.chart.model.DefaultDialModel;
import org.zkoss.chart.model.DialModel;
import org.zkoss.chart.model.DialModelScale;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.EspacioPorParquimetro;
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.dto.Tramite;

public class VerDetalleEspacioParquimetroGestor extends SelectorComposer<Window> {
	
	
	private List<Tramite> lsTramite;
	private Tramite objTramite;
	private EspacioPorParquimetro objEspacioPorParquimetro;
	private long horas=0;
	
	
	
	@Wire
    Charts chart;
	@Wire
	Timer timer;
	@Wire
	Label lblFecInicial;
	@Wire
	Label lblFecFinal;
	@Wire
	Label lblNombre;
	@Wire
	Label lblApellido;
	@Wire
	Label lblUsuario;
	@Wire
	Label lblTelefono;
	
	
	@Wire
	Label lblProvincia;
	@Wire
	Label lblCiudad;
	@Wire
	Label lblArea;
	@Wire
	Label lblParquimetro;
	
	@Wire
	Label lblProvinciaNombre;
	@Wire
	Label lblCiudadNombre;
	@Wire
	Label lblAreaNombre;
	@Wire
	Label lblParquimetroNombre;
	AnnotateDataBinder binder;

	private Session objSession;
	@Wire
	Window win;
	
	//,@ExecutionArgParam("espacio") EspacioPorParquimetro objEspacioPorParquimetro
	@AfterCompose
	public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        binder = new AnnotateDataBinder(comp);
        objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		horas=0;
		
		if(objSession==null){
			Executions.getCurrent().sendRedirect("../");
				return;
			}
		objEspacioPorParquimetro= objSession.getObjEspacioPorParquimetroParam();
		System.out.println("objEspacioPorParquimetro"+objEspacioPorParquimetro);
	
		llamaInfoParquimetro();
		reporteGrafico();
		System.out.println("objEspacioPorParquimetro.getIdEspacioPorParquimetro():"+objEspacioPorParquimetro.getIdEspacioPorParquimetro());
		System.out.println("objTramite.getFechaInicial(): "+objTramite.getFechaInicial());
		lblFecInicial.setValue(objTramite.getFechaInicial().toString());
		lblFecFinal.setValue(objTramite.getFechaFinal().toString());
		lblUsuario.setValue(objTramite.getUsuario());
		lblNombre.setValue(objTramite.getNombre());
		lblApellido.setValue(objTramite.getApellido());
		lblTelefono.setValue(objTramite.getMovil());
		
		if(objEspacioPorParquimetro.getProvincia()==null)
			{
				lblProvinciaNombre.setVisible(false);
				lblProvincia.setVisible(false);
			}
		if(objEspacioPorParquimetro.getCiudad()==null)
		{
			lblCiudadNombre.setVisible(false);
			lblCiudad.setVisible(false);
		}
		if(objEspacioPorParquimetro.getAreas()==null)
		{
			lblAreaNombre.setVisible(false);
			lblArea.setVisible(false);
		}
		if(objEspacioPorParquimetro.getProvincia()==null)
		{
			lblParquimetroNombre.setVisible(false);
			lblParquimetro.setVisible(false);
		}
		
		lblProvincia.setValue(objEspacioPorParquimetro.getProvincia());
		lblCiudad.setValue(objEspacioPorParquimetro.getCiudad());
		lblArea.setValue(objEspacioPorParquimetro.getAreas());
		lblParquimetro.setValue(objEspacioPorParquimetro.getNombreParquimetro());
		binder.loadAll();
	
	}

//	@Init
//	public void init(@ContextParam(ContextType.VIEW) Component view,@ExecutionArgParam("espacio") EspacioPorParquimetro objEspacioPorParquimetro)
//	{
//		Selectors.wireComponents(view, this, false);
//		win =(Window) view;
//		objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
//		horas=0;
//		if(objSession==null){
//				Executions.getCurrent().sendRedirect("/Aplicacion/login.zul");
//				return;
//			}
//		reporteGrafico();
//		
//		if(objEspacioPorParquimetro!=null)
//			this.objEspacioPorParquimetro=objEspacioPorParquimetro;
//	
//		llamaInfoParquimetro();
//	}
	
    // Add some life
    @Listen("onTimer = #timer")
    public void updateData() {
//        int inc = (int) Math.round((Math.random() - 0.5) * 20);
//        int oldVal = chart.getSeries().getPoint(0).getY().intValue();
//        int newVal = oldVal + inc;
//        if (newVal < 0 || newVal > 200) {
//            newVal = oldVal - inc;
//        }
    	System.out.println("horas:"+horas);
    	
    	horas--;
        chart.getSeries().getPoint(0).update(horas);
        
        if(horas<=0)
        	timer.stop();
    }
	
	public void reporteGrafico(){
		DialModel dialmodel = new DefaultDialModel();
        dialmodel.setFrameBgColor(null);
        dialmodel.setFrameBgColor1(null);
        dialmodel.setFrameBgColor2(null);
        dialmodel.setFrameFgColor(null);
        DialModelScale scale = dialmodel.newScale(0, 240, -150, -300, 10, 4);
        scale.setText("MINUTOS");
        scale.setTickColor("#666666");
        scale.setValue(horas);
        scale.newRange(75, 240, "#55BF3B", 0.9, 1); // green
        scale.newRange(20, 75, "#DDDF0D", 0.9, 1); // yellow
        scale.newRange(0, 20, "#DF5353", 0.9, 1); // red
        chart.setModel(dialmodel);

        List<PaneBackground> backgrounds = new LinkedList<PaneBackground>();

        PaneBackground background1 = new PaneBackground();
        LinearGradient linearGradient1 = new LinearGradient(0, 0, 0, 1);
        linearGradient1.setStops("#FFF", "#333");
        background1.setBackgroundColor(linearGradient1);
        background1.setBorderWidth(0);
        background1.setOuterRadius("109%");
        backgrounds.add(background1);

        PaneBackground background2 = new PaneBackground();
        LinearGradient linearGradient2 = new LinearGradient(0, 0, 0, 1);
        linearGradient2.setStops("#333", "#FFF");
        background2.setBackgroundColor(linearGradient2);
        background2.setBorderWidth(1);
        background2.setOuterRadius("107%");
        backgrounds.add(background2);

        // default background
        backgrounds.add(new PaneBackground());

        PaneBackground background3 = new PaneBackground();
        background3.setBackgroundColor("#DDD");
        background3.setBorderWidth(0);
        background3.setOuterRadius("105%");
        background3.setInnerRadius("103%");
        backgrounds.add(background3);

        chart.getPane().setBackground(backgrounds);

        // the value axis
        YAxis yAxis = chart.getYAxis();
        // yAxis.setMinorTickInterval("auto");
        yAxis.setMinorTickWidth(1);
        yAxis.setMinorTickPosition("inside");
        yAxis.setMinorTickColor("#666");

        yAxis.setTickPixelInterval(30);
        yAxis.setTickWidth(2);
        yAxis.setTickPosition("inside");
        yAxis.setTickLength(10);

        yAxis.getLabels().setStep(2);
        yAxis.getLabels().setRotation("auto");

        chart.getPlotOptions().getGauge().getTooltip().setValueSuffix("Minutos");
        
        chart.getSeries().setName("Remanente");
		
	}
	
	public long diferenciaEnDias2(Date fechaMayor, Date fechaMenor) {
		long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();
		long dias = diferenciaEn_ms / (1000 * 60 );
		return  dias;
		}
	
	
	public void llamaInfoParquimetro(){
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.consultaTramiteXespacioParq(objEspacioPorParquimetro.getIdEspacioPorParquimetro());
		
		if(map.get("error") == null){
			
			lsTramite=(List<Tramite>) map.get("listTramite");
			if(lsTramite!=null)
				if(lsTramite.size()>0)
				{
					objTramite=lsTramite.get(lsTramite.size()-1);
				}
			if(objTramite!=null){
				horas = diferenciaEnDias2(objTramite.getFechaFinal() ,new java.util.Date());
				System.out.println("objTramite.getFechaInicial(): "+objTramite.getFechaInicial());	
			}
			

			
					
		}else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	@Command
	public void salir(){
		timer.stop();
		win.detach();
	}


	public List<Tramite> getLsTramite() {
		return lsTramite;
	}


	public void setLsTramite(List<Tramite> lsTramite) {
		this.lsTramite = lsTramite;
	}


	public Tramite getObjTramite() {
		return objTramite;
	}


	public void setObjTramite(Tramite objTramite) {
		this.objTramite = objTramite;
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


	public EspacioPorParquimetro getObjEspacioPorParquimetro() {
		return objEspacioPorParquimetro;
	}


	public void setObjEspacioPorParquimetro(EspacioPorParquimetro objEspacioPorParquimetro) {
		this.objEspacioPorParquimetro = objEspacioPorParquimetro;
	}


	public long getHoras() {
		return horas;
	}


	public void setHoras(long horas) {
		this.horas = horas;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Label getLblFecInicial() {
		return lblFecInicial;
	}

	public void setLblFecInicial(Label lblFecInicial) {
		this.lblFecInicial = lblFecInicial;
	}

	public Label getLblFecFinal() {
		return lblFecFinal;
	}

	public void setLblFecFinal(Label lblFecFinal) {
		this.lblFecFinal = lblFecFinal;
	}

	public Label getLblNombre() {
		return lblNombre;
	}

	public void setLblNombre(Label lblNombre) {
		this.lblNombre = lblNombre;
	}

	public Label getLblApellido() {
		return lblApellido;
	}

	public void setLblApellido(Label lblApellido) {
		this.lblApellido = lblApellido;
	}

	public Label getLblUsuario() {
		return lblUsuario;
	}

	public void setLblUsuario(Label lblUsuario) {
		this.lblUsuario = lblUsuario;
	}

	public Label getLblTelefono() {
		return lblTelefono;
	}

	public void setLblTelefono(Label lblTelefono) {
		this.lblTelefono = lblTelefono;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Label getLblProvincia() {
		return lblProvincia;
	}

	public void setLblProvincia(Label lblProvincia) {
		this.lblProvincia = lblProvincia;
	}

	public Label getLblCiudad() {
		return lblCiudad;
	}

	public void setLblCiudad(Label lblCiudad) {
		this.lblCiudad = lblCiudad;
	}

	public Label getLblArea() {
		return lblArea;
	}

	public void setLblArea(Label lblArea) {
		this.lblArea = lblArea;
	}

	public Label getLblParquimetro() {
		return lblParquimetro;
	}

	public void setLblParquimetro(Label lblParquimetro) {
		this.lblParquimetro = lblParquimetro;
	}

	public Charts getChart() {
		return chart;
	}

	public void setChart(Charts chart) {
		this.chart = chart;
	}

	public Label getLblProvinciaNombre() {
		return lblProvinciaNombre;
	}

	public void setLblProvinciaNombre(Label lblProvinciaNombre) {
		this.lblProvinciaNombre = lblProvinciaNombre;
	}

	public Label getLblCiudadNombre() {
		return lblCiudadNombre;
	}

	public void setLblCiudadNombre(Label lblCiudadNombre) {
		this.lblCiudadNombre = lblCiudadNombre;
	}

	public Label getLblAreaNombre() {
		return lblAreaNombre;
	}

	public void setLblAreaNombre(Label lblAreaNombre) {
		this.lblAreaNombre = lblAreaNombre;
	}

	public Label getLblParquimetroNombre() {
		return lblParquimetroNombre;
	}

	public void setLblParquimetroNombre(Label lblParquimetroNombre) {
		this.lblParquimetroNombre = lblParquimetroNombre;
	}

}
