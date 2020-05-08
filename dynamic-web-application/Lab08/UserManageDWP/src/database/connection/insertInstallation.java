package database.connection;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/postInstallation")
public class insertInstallation extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
			
		// read form fields
        String address = request.getParameter("address");
        String numerRutera = request.getParameter("routherNumber");
        String typ_usługi = request.getParameter("servicetype");
        int id_usługi = Integer.parseInt(request.getParameter("iduslugi"));
        int id_user =Integer.parseInt(request.getParameter("iduser"));
        
        Connect connect = new Connect();
        connect.getConnection();
        connect.connect();
        connect.createTables();
        connect.insertInstallation(address, numerRutera, typ_usługi, id_user, id_usługi);

    }

}
