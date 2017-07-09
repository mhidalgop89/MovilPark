package ec.gob.movilpark.util;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {
	
	private String SMTP_AUTH_USER = "";
	private String SMTP_AUTH_PWD = "";
	
	public static void main(String[] args) {
		Mail mail = new Mail();
		mail.enviarMail("hola","mario.hidalgo89@hotmail.com");
	}
	
	
	public Boolean enviarMail(String mensaje, String mailPara){
		
//		CargarListaItemsDao datos = new CargarListaItemsDao();
//		ConfiguracionDto conf = datos.cargarConfigurationDao(1);
		
		boolean debug = true;
		String servidorSMTP = "mail.parqueopositivo.com";//"10.1.0.4";// //conf.getEmailSmtp();
		String pwd = "pp2016.";//conf.getEmailPassword();
		String login = "correo@parqueopositivo.com";//conf.getCompanyEmail();
		String puerto = "25";//String.valueOf(conf.getEmailPuerto());
		String mail = "correo@parqueopositivo.com";//conf.getEmailEnvia();
		String para = mailPara;//"mario.hidalgo89@hotmail.com";//conf.getCompanyEmail();
			System.out.println("---------------->login: "+login+" "+pwd+" - "+puerto+" - "+mail+" - "+para+" - "+ servidorSMTP);
		this.SMTP_AUTH_PWD = pwd;
		this.SMTP_AUTH_USER = login;
		
		try{
			
			
			Properties props = new Properties();

			props.put("mail.smtp.ssl.trust", servidorSMTP);
			props.put("mail.smtp.host", servidorSMTP);
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			props.put("mail.smtp.user", SMTP_AUTH_USER);
			props.put("mail.smtp.password", SMTP_AUTH_PWD);
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", puerto );
			 
			Authenticator auth =new SMTPAuthenticator();

			 
			Session session = Session.getInstance(props, auth);
			 
			session.setDebug(debug);
			 
			Message msg = new MimeMessage(session);
			 
			InternetAddress addressFrom = new InternetAddress(mail);
			msg.setFrom(addressFrom);
			System.out.println("Para: "+para);
			InternetAddress addressTo = new InternetAddress(para);
			msg.setRecipient(Message.RecipientType.TO, addressTo);

			msg.setSubject("Parqueo Positivo");
			//msg.setText(mensaje);
			msg.setContent(mensaje, "text/html; charset=utf-8");
			Transport.send(msg);
			System.out.println("Enviado exitosamente a:"+para);
			return true;
			
			
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
	private class SMTPAuthenticator extends javax.mail.Authenticator { 
		public PasswordAuthentication getPasswordAuthentication() { 
			String username = SMTP_AUTH_USER; String password = SMTP_AUTH_PWD; 
			return new PasswordAuthentication(username, password); } } 
	//- See more at: http://www.jvmhost.com/articles/how-to-send-mail-with-javamail-and-tomcat#sthash.bFBX8A3q.dpuf
	
	
/*	public  boolean send(String from, String[] to, String subject,
            String[] cc, String[] bcc, String message,String fileName) {
		CargarListaItemsDao datos = new CargarListaItemsDao();
		ConfiguracionDto conf = datos.cargarConfigurationDao(1);
		
		
		
		boolean debug = true;
		String servidorSMTP =conf.getEmailSmtp();
		String pwd = conf.getEmailPassword();
		String login = conf.getCompanyEmail();
		String puerto = String.valueOf(conf.getEmailPuerto());
		String mail = conf.getEmailEnvia();
		String para = conf.getCompanyEmail();
		
		this.SMTP_AUTH_PWD = pwd;
		this.SMTP_AUTH_USER = login;

		//String host = "gyq-srv-ex01";
	    // String host = "172.17.0.106";
      // String host = "sggs-dc01";
      String host =   servidorSMTP;//ReadPropertiesUtil.obtenerProperty("HOST_SERVER_MAIL");// "uio-srv-ex01";
      System.out.println("---------------->login: "+login+" "+pwd+" - "+puerto+" - "+mail+" - "+para+" - "+ servidorSMTP);
      //String host = "172.17.0.106";
      // String host = "localhost";
      // String host = "10.1.197.20"; // esafe
      // String host = "10.1.218.250"; // Tania
      // String host = "10.1.218.250"; // Supercias
//    String mailer = "sendhtml";

      
      System.out.println("Send Message from:" + from);
      System.out.println("             to  :" + to);
      System.out.println("          Subject:" + subject);
      System.out.println("             cc  :" + cc);
      System.out.println("             bcc :" + bcc);
      System.out.println("message:" + message);
      Properties props = System.getProperties();
      props.put("mail.smtp.host", host);      
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.user", SMTP_AUTH_USER);
		props.put("mail.smtp.password", SMTP_AUTH_PWD);
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", puerto);
      props.setProperty("charset","utf-8");

      // rellenar props con la información necesaria.

      Session session = Session.getDefaultInstance(props, null);
      // Session session = Session.getInstance(props, null);
    		  
      try {
    	  
            // create a message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress add = new InternetAddress(from);
            System.out.println(add.toString());
            InternetAddress[] addressTo = new InternetAddress[to.length];

            for (int i = 0; i < to.length; i++)
            {	try {
            	 addressTo[i] = new InternetAddress(to[i]);
            	 
				} catch (Exception e) {
					// TODO: handle exception
					addressTo[i] = new InternetAddress("notificacion@supercias.gob.ec" );
				}
                 
                  
            }     
            msg.setRecipients(Message.RecipientType.TO, addressTo);

            try
            {
	            if (cc != null) {
	                  InternetAddress[] addressCc = new InternetAddress[cc.length];
	                  for (int i = 0; i < cc.length; i++)
	                       addressCc[i] = new InternetAddress(cc[i]);
	                  msg.setRecipients(Message.RecipientType.CC, addressCc);
	            }
            }catch(Exception e)
            {
            	e.printStackTrace();
            }
            if (bcc != null) {
                  InternetAddress[] addressBcc = new InternetAddress[bcc.length];
                  for (int i = 0; i < bcc.length; i++)
                       addressBcc[i] = new InternetAddress(bcc[i]);
                  msg.setRecipients(Message.RecipientType.BCC, addressBcc);
            }
            
            msg.setSubject(subject);
            
           
            //collect(message, msg);
            //Se seteo el mensaje del e-mail
           
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message,"text/html; charset=utf-8");
            
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            
            msg.setContent(multipart);
            // msg.setHeader("X-Mailer", mailer);
            msg.setSentDate(new Date());
            
          if (fileName!=null)
          {
            MimeBodyPart rarAttachment = new MimeBodyPart();  
            FileDataSource rarFile = new FileDataSource(fileName);  
            rarAttachment.setDataHandler(new DataHandler(rarFile));  
            rarAttachment.setFileName(rarFile.getName());  
            multipart.addBodyPart(rarAttachment); 
          }
            
          //  msg.setFileName(fileName);
            // If the desired charset is known, you can use
            // setText(text, charset)
            // msg.setText(message);
            // Send message
//            Transport transport = session.getTransport("smtp");
//            transport.connect(host, "sales@allparts.expert", "JLPS64116");

            Transport.send(msg);
            return true;
      } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
      } catch (Exception e) {
            e.printStackTrace();
            return false;
      }
}*/
	

}
