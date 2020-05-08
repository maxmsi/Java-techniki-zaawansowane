package database.connection;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/postUser")
public class insertUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
			
		// read form fields
        String username = request.getParameter("username");
        String secondname = request.getParameter("secondname");
        String usernumber = request.getParameter("usernumber");
         
        
        Connect connect = new Connect();
        connect.getConnection();
        connect.connect();
        connect.createTables();
        connect.insertUser(username, secondname, usernumber);
        System.out.println("username: " + username);

    }
}
