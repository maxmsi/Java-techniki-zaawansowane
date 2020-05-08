package database.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.models.Installation;
import database.models.User;

@WebServlet("/getInstallations")
public class getInstallation extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		
	protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
		
		Connect connect = new Connect();
	    connect.getConnection();
	    connect.connect();
	    connect.createTables();
	    
	    List<Installation> userInstList= connect.selectInstallations(Integer.parseInt(request.getParameter("id")));
	    PrintWriter writer = response.getWriter();
	    
	    // build HTML code
	    String htmlRespone = "<html>";
	    htmlRespone+="<table style=\"width:100%\">\n" + 
	    		"    <tr>\n" + 
	    		"      <th>Adres</th>\n" + 
	    		"      <th>NumerRutera</th>\n" + 
	    		"      <th>TypUslugi</th>\n" + 
	    		"      <th>IdUslugi</th>\n" + 
	    		"      <th>IdUser</th>\n" + 
	    		"</tr>";
	    for(int i=0;userInstList.size()>i;i++) {
	    	htmlRespone+="<tr>\n" + 
	    			"      <td>"+userInstList.get(i).getAddres()+"</td>\n" + 
	    			"      <td>"+userInstList.get(i).getRoutherNumber()+"</td>\n" + 
	    			"      <td>"+userInstList.get(i).getServiceType()+"</td>\n" + 
	    			"      <td>"+userInstList.get(i).getServiceid()+"</td>\n" +
	    			"      <td>"+userInstList.get(i).getUserid()+"</td>\n" +
	    			"    </tr> ";
	    }
	    htmlRespone += "</table></html>";
	     
	    // return response
	    writer.println(htmlRespone);
	  
		}
	
}
