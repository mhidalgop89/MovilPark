package ec.gob.movilpark.gestor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import ec.gob.movilpark.dao.Listas;
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.Firebase;
import ec.gob.movilpark.dto.PushMessage;
import ec.gob.movilpark.dto.Session;
import ec.gob.movilpark.filtros.Filtros;


public class FcmGestor {
	
	private Session objSession;
	Window win;
	
	private PushMessage push= new PushMessage();
	private Firebase firebase = new Firebase();
	private String nombreArchivo;
	private PushMessage pmessage = new PushMessage();

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
	
	public String getAccessToken2() throws IOException {
		System.out.println("firebase.getRutaClavePrivada(): "+firebase.getRutaClavePrivada());
		  GoogleCredential googleCredential = GoogleCredential//https://fcm.googleapis.com/v1/projects/parqueo-b85ec/messages:send
		      .fromStream(new FileInputStream(firebase.getRutaClavePrivada()/*"C:\\parqueo-b85ec-firebase-adminsdk-7hp3v-4a7757afd1.json"*/))
		      .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
		  googleCredential.refreshToken();
		  return googleCredential.getAccessToken();
		}
	
	public void leeWsRest(){
		
		
		  try {
			  String token= getAccessToken2();
			  System.out.println(token);

				URL url = new URL("https://fcm.googleapis.com/v1/projects/parqueo-b85ec/messages:send");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Authorization", "Bearer "+token);
				

				String input = "{"+
"\"message\":{"+
"  \"topic\" : \"marketingParkMobile\","+
"  \"notification\" : {"+
"    \"body\" : \" "+push.getCuerpo()+" \","+
"    \"title\" : \""+push.getMensaje()+"\""+
"    }"+
" }"+
"}";
				
				System.out.println("--"+input);

				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();

				if (conn.getResponseCode() != 200/*HttpURLConnection.HTTP_CREATED*/) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode()+"--"+conn.getResponseMessage()+"--"+conn);
				}
				String respMessage = "";
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
					respMessage=respMessage+output;
				}

				conn.disconnect();

				
				push.setCodigoCompania(1);//cod cia debe ser automatico
				push.setRequest(input);
				push.setResponse(respMessage);
				push.setMetodo("https://fcm.googleapis.com/v1/projects/parqueo-b85ec/messages:send");
				push.setToken(token);
				push.setEstado(conn.getResponseMessage());
				push.setRutaClavePrivada(firebase.getRutaClavePrivada());
				push.setUsuarioIngresa(objSession.getUsuario().getUsuario());
				
				Map<String,Object> map= new HashMap<String , Object>();
				Listas listasDao= new Listas();
				map = listasDao.ingresaPushMessage(push); 
				
				if(map.get("error") == null)
					if("OK".equals(push.getEstado()))
						Messagebox.show("Enviado Correctamente.", "Atención", Messagebox.OK, Messagebox.INFORMATION);		
					else
						Messagebox.show("Consulte con el administrador.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
				else
					{
						Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				
				
				

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			 }

		
	}
	
	@Command
	@NotifyChange("push")
	public void limpiarPush(){
		push= new PushMessage();
	}
	
	
	@Command
	public void enviaPush(){
		
		if(push.getMensaje()== null){
			Messagebox.show("Campo Mensaje obligatorio.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		Firebase objFirebase= new Firebase();
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		
		map = listasDao.consultaFirebase(1/*cod cia debe ser automatico de la empresa registrada*/,"FCM"/*cod producto firebase (cloud message en este caso)*/);
			List<Firebase> listaFirebase= new ArrayList<Firebase>();
		
		if(map.get("error") == null)
			listaFirebase=(List<Firebase>) map.get("firebase");
		else{
			Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		if(listaFirebase.size()==0){
			Messagebox.show("Primero debe registrar la clave privada", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		firebase = listaFirebase.get(0);
		leeWsRest();
			
		
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
		System.out.println("media.getName():"+media.getName()+"extension:"+extension);
		if(!"json".equals(extension)){
			Messagebox.show("La extensión no es valida.", "Atención", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
			
		if (so.contains("Windows"))
		{
			nombreArchivo = "C:\\exports\\modulos\\movilpark\\empresa\\1\\"+media.getName();
		}else{
			nombreArchivo = "opt/exports/modulos/movilpark/empresa/1/"+media.getName();
			//input = new FileInputStream("/opt/movilpark/messages.properties");
		}
		
		File file = new File(nombreArchivo);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(media.getByteData());
		fos.close();
		
		
		firebase.setCodigoCompania(1);//debe venir de la cia registrada-- pendiente automatizar
		firebase.setCproductoFirebase("FCM");//FCM firebase cloud message
		firebase.setProductoFirebase("Firebase Cloud Message");//pendiente crear una tabla productos de firebase a usar
		firebase.setRutaClavePrivada(nombreArchivo.replace("\\", "\\\\"));
		firebase.setObservacion("Registro de clave privada para uso de FCM");
		
		Map<String,Object> map= new HashMap<String , Object>();
		Listas listasDao= new Listas();
		map = listasDao.ingresaFirebase(firebase);
		
		if(map.get("error") == null)
			Messagebox.show("Registrado Correctamente.", "Atención", Messagebox.OK, Messagebox.INFORMATION);					
		else
			{
				Messagebox.show(map.get("mensajeError").toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		
		/*almacenar la ruta en variable de tipo firebase
		 * objAreas.setRutaImagen(nombreArchivo);
		actualizarArea();*/
	}
	
	public static void main(String[] args) {

		FcmGestor tfcm =new FcmGestor();
		tfcm.leeWsRest();
		
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

	public PushMessage getPush() {
		return push;
	}

	public void setPush(PushMessage push) {
		this.push = push;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public PushMessage getPmessage() {
		return pmessage;
	}

	public void setPmessage(PushMessage pmessage) {
		this.pmessage = pmessage;
	}

	public Firebase getFirebase() {
		return firebase;
	}

	public void setFirebase(Firebase firebase) {
		this.firebase = firebase;
	}
	
	
	
	

}
