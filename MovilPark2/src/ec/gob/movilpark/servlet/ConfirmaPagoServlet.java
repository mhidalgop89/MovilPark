package ec.gob.movilpark.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ConfirmaPagoServlet
 */
@WebServlet("/ConfirmaPagoServlet")
public class ConfirmaPagoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmaPagoServlet() {
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
		
		if ("Completed".equals(payment_status ) ) {
			response(resp, "valid payment");
		} else {
			response(resp, "invalid payment");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
		
		String payment_status  = req.getParameter("payment_status");
		System.out.println("payment_statuspost: "+payment_status);
		
		if ("Completed".equals(payment_status ) ) {
			response(resp, "valid payment");
		} else {
			response(resp, "invalid payment");
		}

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
