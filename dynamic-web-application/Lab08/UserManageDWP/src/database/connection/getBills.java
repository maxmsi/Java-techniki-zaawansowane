package database.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.models.Bills;
import database.models.Installation;

@WebServlet("/getBills")
public class getBills extends HttpServlet{
	
	
	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		
		Connect connect = new Connect();
	    connect.getConnection();
	    connect.connect();
	    connect.createTables();
	    int id=Integer.parseInt(request.getParameter("id"));
	    String date1 =request.getParameter("date1");
	    String date2 = request.getParameter("date2");
	    
	    List<Bills> billsList= connect.selectBillsByDate(id,date1,date2);
	    PrintWriter writer = response.getWriter();
		
	    String htmlRespone = "<html>";
	    htmlRespone+="<table style=\"width:100%\">\n" + 
	    		"    <tr>\n" + 
	    		"      <th>Data</th>\n" + 
	    		"      <th>Do zaplaty</th>\n" + 
	    		"</tr>";
	    for(int i=0;billsList.size()>i;i++) {
	    	htmlRespone+="<tr>\n" + 
	    			"      <td>"+billsList.get(i).getString()+"</td>\n" + 
	    			"      <td>"+billsList.get(i).getPaymentAmount()+"</td>\n" + 
	    			"    </tr> ";
	    }
	    htmlRespone += "</table></html>";
	     
	    // return response
	    writer.println(htmlRespone);
		
		
	}
}
