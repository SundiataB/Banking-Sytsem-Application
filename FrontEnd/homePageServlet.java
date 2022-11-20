//import jakarta.servlet.*;
import java.io.*;
//import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.*;
//import java.util.Enumeration;
//import java.sql.*;

// To directly compile (NOT test) this servlet, open a Git bash terminal and type in
// One of these, preferably the second.
// javac -classpath 'lib/servlet-api.jar' FrontEnd/Servlets/homePageServlet.java
// javac -classpath 'lib/servlet-api.jar' -d build FrontEnd/Servlets/homePageServlet.java

@WebServlet(name = "Home Page Servlet", urlPatterns = "/homePageServlet")
public class homePageServlet extends HttpServlet {

    Logger svltLog;
    boolean loggedIn = true;
    String pageToDisplay;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        if (svltLog == null) {
            svltLog  = Logger.getLogger(this.getClass().getName() + "Servlet");
        }
        svltLog.warning("Firing up servlet " + this.getClass().getName());

        HttpSession session = request.getSession(true);
        // These are, hopefully, going to be tested against the database at some point.
        PrintWriter out = response.getWriter();
        
        String username = (String) session.getAttribute("username");

        response.setContentType("text/html");

        out.println("<html lang=\"en\">");
        out.println("   <head>");
        out.println("       <meta charset=\"utf=8\">");
        out.println("       <link rel=\"stylesheet\" href=\"global.css\">");
        out.println("       <link rel=\"stylesheet\" href=\"assets/style/font-awesome-4.7.0/css/font-awesome.min.css\">");
        out.println("       <title>Welcome to Atlantic Bank's website.</title>");
        out.println("   </head>");
        out.println("");
        out.println("   <body>");
        out.println("       <nav class=\"navbar\"");
        out.println("           <a href=\"/Banking/homePageServlet\"><i class=\"fa fa-home\"></i></a>");
        out.println("           <li>");
        out.println("               <div class=\"dropdown\" style=\"float:left;\">");
        out.println("                   <button class=\"dropbtn\">ACCOUNTS</button>");
        out.println("                   <div class=\"dropdown-content\"style=\"left:0;\">");
        out.println("                       <a href=\"/Banking/AccountSummaryServlet\">Account Summary</a>");
        out.println("                       <a href=\"recent_transactions.html\">Transaction History</a>");
        out.println("                   </div>");
        out.println("               </div>");
        out.println("          </li>");
        out.println("          <li><a href=\"\">Credit Score Checker</a></li>");
        out.println("          <li><a href=\"deposit.html\">Deposits</a></li>");
        out.println("          <li><a href=\"withdraw.html\">Withdrawals</a></li>");
        out.println("          <li><a href=\"transfer.html\">Transfers</a></li>");
        out.println("<!-- Need to update this so that it is servlet and logs out a user -->");
        out.println("          <li><a href=\"/Banking/logout\" class=\"outline\">Log Out</a></li>");
        out.println("       </nav>");
        out.println("");
        out.println("          <h1 class=\"indextitle\">You have been logged in "+username+"</h1>");
        out.println("       <h1 class=\"indextitle\"> Welcome Back to Atlantic Bank !</h1>");
        out.println("");
        out.println("       <footer>");
        out.println("           <p> For any questions, please click the email below.<br>");
        out.println("           <a href=\"mailto:aljames@gmail.com\">aljames@gmail.com</a></p>");
        out.println("       </footer>");
        out.println("   </body>");
        out.println("</html>");        
    } 
}