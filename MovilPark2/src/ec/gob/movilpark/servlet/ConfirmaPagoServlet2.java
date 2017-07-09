package ec.gob.movilpark.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

import ec.gob.movilpark.dto.Session;
import jdk.nashorn.internal.ir.CallNode.EvalArgs;

/**
 * Servlet implementation class ConfirmaPagoServlet2
 */
@WebServlet("/ConfirmaPagoServlet2")
public class ConfirmaPagoServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmaPagoServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.getWriter().append("Served at: ").append(req.getContextPath());
		
		
		String payment_status  = req.getParameter("payment_status");
		System.out.println("payment_statusget: "+payment_status);
		
		String tx  = req.getParameter("tx");
		System.out.println("tx: "+tx);
		
		
//		doPost(req, resp);
//		
//		
//		if ("Completed".equals(payment_status ) ) {
//			response(resp, "valid payment");
//		} else {
//			response(resp, "invalid payment");
//		}
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(req, resp);
//		
//		String payment_status  = req.getParameter("payment_status");
//		System.out.println("payment_statuspost: "+payment_status);
//		
//		if ("Completed".equals(payment_status ) ) {
//			response(resp, "valid payment");
//		} else {
//			response(resp, "invalid payment");
//		}
//
//	}
	
	 @Override
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	         throws ServletException, IOException {
	     // Java JSP
	     //log.error("IPN doPost " + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds());
		 String paramName;
		 String paramValue;
	     // read post from PayPal system and add 'cmd'
	     Enumeration<String> en = request.getParameterNames();
	     String str = "?cmd=_notify-validate";
	     while (en.hasMoreElements()) {
	         paramName = (String) en.nextElement();
	         paramValue = request.getParameter(paramName);
	         paramValue = new String(paramValue.getBytes("iso-8859-1"), "utf-8");
	         str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue);
	     }

	     boolean isSandbox = false;//"true".equals(PropertiesManager.getProperty("signbox", "PaypalSandbox"));
	     
	     // post back to PayPal system to validate
	     // NOTE: change http: to https: in the following URL to verify using SSL (for increased security).
	     // using HTTPS requires either Java 1.4 or greater, or Java Secure Socket Extension (JSSE)
	     // and configured for older versions.
	     String url = null;
	     if (isSandbox){
	         url = "https://www.sandbox.paypal.com/cgi-bin/webscr"+str;
	     }else{
	         url = "https://www.paypal.com/cgi-bin/webscr"+str;
	     }
	     //log.error("La url de a la que redirigimos a Paypal es " + url);
	     System.out.println("La url de a la que redirigimos a Paypal es " + url);
	     URL u = new URL(url);
	     URLConnection uc = u.openConnection();
	     uc.setDoOutput(true);
	     uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	     PrintWriter pw = new PrintWriter(uc.getOutputStream());
	     pw.println(str);
	     pw.close();

	     BufferedReader in = new BufferedReader(
	             new InputStreamReader(uc.getInputStream()));
	     String res = in.readLine();
	     in.close();
	     System.out.println("Tras abrir la conexión, res es = " + res);
	     //log.error("Tras abrir la conexión, res es = " + res);

	     // assign posted variables to local variables
	     String idUser = request.getParameter("custom");
	     String idCompra = request.getParameter("invoice");
	     String paymentStatus = request.getParameter("payment_status");
	     String paymentAmount = request.getParameter("mc_gross");
	     String fee = request.getParameter("mc_fee");
	     String paymentCurrency = request.getParameter("mc_currency");
	     String txnId = request.getParameter("txn_id");
	     String receiptId = request.getParameter("receipt_id");
	     String receiverEmail = request.getParameter("receiver_email");
	     String payerEmail = request.getParameter("payer_email");
	     String paymentType = request.getParameter("payment_type");
	     String txnType = request.getParameter("txn_type");
	     System.out.println("res"+res);
	     
	     System.out.println("paymSta: "+paymentStatus);
	     if("Completed".equals(paymentStatus))
	    	 response.sendRedirect("http://40.76.59.162:8080/MovilPark/Aplicacion/pagoCompleto.zul");
	     else
	    	 response.sendRedirect("http://40.76.59.162:8080/MovilPark/Aplicacion/pagoNoCompletado.zul");
	     
	     //Session objSession;
	     
	     //objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
	     
//		Clients.evalJavaScript("Response.Redirect('http://localhost:8080/MovilPark/Aplicacion/servicio.zul')");
	     

//	     if (!"instant".equals(paymentType) || !"cart".equals(txnType)) {
//	         log.debug("NO ES UN CART CHECKOUT. Detalles:");
//	         log.debug("idCompra=" + idCompra);
//	         log.debug("status=" + paymentStatus);
//	         log.debug("amount=" + paymentAmount);
//	         log.debug("currency=" + paymentCurrency);
//	         log.debug("transactionId=" + txnId);
//	         log.debug("receiptId=" + receiptId);
//	         log.debug("receiverEmail=" + receiverEmail);
//	         log.debug("payerEmail=" + payerEmail);
//	         log.debug("paymentType=" + paymentType);
//	         log.debug("txnType=" + txnType);
//
//	         return;
//	     }
	 }

	
	private void response(HttpServletResponse resp, String msg)
			throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<t1>" + msg + "</t1>");
		out.println("</body>");
		out.println("</html>");
	}

}
