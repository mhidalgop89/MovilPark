package ec.gob.movilpark.gestor;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.gob.movilpark.dto.Areas;
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Session;

public class VerAreaGestor {

	@Wire
	private Iframe ifMinuta = new Iframe();
	@Wire
	private Image imArea = new Image();
	private  Areas objAreasParam;
	
	private Session objSession;
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
		this.objAreasParam=objAreasParam;
		
		try{
		org.zkoss.image.AImage img = new org.zkoss.image.AImage(objAreasParam.getRutaImagen()); 

		win.appendChild(imArea);
		imArea.setContent(img);
		imArea.invalidate();						
		System.out.println("se carga la imagen");
		}catch(Exception e){e.printStackTrace();}
		////////////////////////////////
		////////////////////////////////
		////////////////////////////////
		////////////////////////////////
	////cargo la minuta en el iframe
	/*			FileInputStream in=null;
				try
				{
					System.out.println("cargar la imagen");
					try
					{
						System.out.println("ruta imagen: "+objAreasParam.getRutaImagen());
						in= new FileInputStream(objAreasParam.getRutaImagen());
					 
					}catch(Exception e)
					{
						e.printStackTrace();

					}
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					int bytesRead;
					while ((bytesRead = in.read(buf, 0, buf.length)) >= 0) { // Read in next chunk of pdf
					os.write(buf, 0, bytesRead); // write it out
					}
					AMedia am = new AMedia(objAreasParam.getRutaImagen(), "jpg", "application/jpg", os.toByteArray());
					
					if (am!=null)
					{
						org.zkoss.image.AImage img = new org.zkoss.image.AImage(objAreasParam.getRutaImagen()); 

						win.appendChild(ifMinuta);						
						ifMinuta.setContent(am);						
						ifMinuta.invalidate();						
						System.out.println("se carga la imagen");
					}

					
				}catch(Exception e)
				{
					e.printStackTrace();
				}	*/
	}
	
	
	@Command
	public void salir(){
		win.detach();
	}

	public Iframe getIfMinuta() {
		return ifMinuta;
	}

	public void setIfMinuta(Iframe ifMinuta) {
		this.ifMinuta = ifMinuta;
	}

	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}

	public Areas getObjAreasParam() {
		return objAreasParam;
	}

	public void setObjAreasParam(Areas objAreasParam) {
		this.objAreasParam = objAreasParam;
	}

	public Image getImArea() {
		return imArea;
	}

	public void setImArea(Image imArea) {
		this.imArea = imArea;
	}


	public Session getObjSession() {
		return objSession;
	}


	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}

	
	
}
