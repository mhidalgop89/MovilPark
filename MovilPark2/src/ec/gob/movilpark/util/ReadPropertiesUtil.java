package ec.gob.movilpark.util;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
public class ReadPropertiesUtil {

	public static String obtenerProperty(String propiedad)
	{
		Properties prop = new Properties();
		InputStream input = null;
		
		String valor=null;
	 
		try {
			
			String so = System.getProperty("os.name"); 
			
			if (so.contains("Windows"))
			{
				input = new FileInputStream("C:\\exports\\modulos\\movilpark\\config\\messages.properties");
//				if(idioma.equals("en"))
//					input = new FileInputStream("C:\\Users\\toshiba\\Documents\\mvera\\messages.properties");
//				if(idioma.equals("es"))
//					input = new FileInputStream("C:\\Users\\developer\\Documents\\propertiesGmaps\\messages_es.properties");
			
			}else
			{
				//input = new FileInputStream("/opt/constitucion/constitucionElectronica.properties");
				input = new FileInputStream("/opt/movilpark/messages.properties");
			}
	 
			
	 
			// load a properties file
			prop.load(input);
			
			
			valor =prop.getProperty(propiedad);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return valor;
	}
	
	
	public static void main(String[] args) {
		System.out.println(ReadPropertiesUtil.obtenerProperty("Ingresar"));
	}


	
}
