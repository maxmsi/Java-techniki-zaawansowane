package database.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.models.*;


@WebServlet("/getUsers")
public class getUsers extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
	
	Connect connect = new Connect();
    connect.getConnection();
    connect.connect();
    connect.createTables();
    List<User> userList= connect.selectUsers();
    PrintWriter writer = response.getWriter();
    
    // build HTML code
    String htmlRespone = "<html>";
    htmlRespone+="<table style=\"width:100%\">\n" + 
    		"    <tr>\n" + 
    		"      <th>First name</th>\n" + 
    		"      <th>Last name</th>\n" + 
    		"      <th>Account N</th>\n" + 
    		"</tr>";
    for(int i=0;userList.size()>i;i++) {
    	htmlRespone+="<tr>\n" + 
    			"      <td>"+userList.get(i).getName()+"</td>\n" + 
    			"      <td>"+userList.get(i).getSecondName()+"</td>\n" + 
    			"      <td>"+userList.get(i).getAccountNumber()+"</td>\n" + 
    			"    </tr> ";
    }
    htmlRespone += "</table></html>";
     
    // return response
    writer.println(htmlRespone);
  
	}
}
