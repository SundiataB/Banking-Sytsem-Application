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
import java.sql.*;
import java.sql.Timestamp;

// Extend HttpServlet class
@WebServlet(name = "withdraw Servlet", urlPatterns = { "/withdrawServlet" })
public class withdrawServlet extends HttpServlet {

    Logger svltLog;
    boolean withdrewFromAccount = false;
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
        //retrieve the username and password submitted by the form
        String accountNumToWithdraw = request.getParameter("accountNum");
        int valueToWithdraw = Integer.parseInt(request.getParameter("withdraw"));

        withdrawAction withdraw = new withdrawAction(username, accountNumToWithdraw, valueToWithdraw);
        withdrewFromAccount = withdraw.sendInfo();
          
        // display results based on execution of query for true and false outcomes
        response.setContentType("text/html");
        if(withdrewFromAccount){    
            out.println("<html lang=\"en\">");
            out.println("   <head>");
            out.println("       <meta charset=\"utf=8\">");
            out.println("       <link rel=\"stylesheet\" href=\"global.css\">");
            out.println("       <link rel=\"stylesheet\" href=\"assets/style/font-awesome-4.7.0/css/font-awesome.min.css\">");
            out.println("       <title>Withdraw - Atlantic Bank.</title>");
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
            out.println("           <h1>Enter the amount you wish to withdraw below.</h1>");
            out.println("           <p> Please enter the desired amount you wish to withdraw.</p>");
            out.println("           <form action=\"/Banking/withdrawServlet\">");
            out.println("               Account Number: <input type=\"text\" name=\"accountNum\" size=\"20\"><br>");
            out.println("               <label for=\"withdraw\">$</label>");
            out.println("               <input type=\"number\" id=\"withdraw\" name=\"withdraw\"><br>");
            out.println("               <input type=\"submit\" value=\"Submit\">");
            out.println("           </form>");
            out.println("           <p>Withdrawal completed!</p>");
            out.println("");
            out.println("       <footer>");
            out.println("           <p> For any questions, please click the email below.<br>");
            out.println("           <a href=\"mailto:aljames@gmail.com\">aljames@gmail.com</a></p>");
            out.println("       </footer>");
            out.println("   </body>");
            out.println("</html>");        
        } else {
            out.println("<html lang=\"en\">");
            out.println("   <head>");
            out.println("       <meta charset=\"utf=8\">");
            out.println("       <link rel=\"stylesheet\" href=\"global.css\">");
            out.println("       <link rel=\"stylesheet\" href=\"assets/style/font-awesome-4.7.0/css/font-awesome.min.css\">");
            out.println("       <title>Withdraw - Atlantic Bank.</title>");
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
            out.println("          <li><a href=\"/Banking/logout\" class=\"outline\">Log Out</a></li>");
            out.println("       </nav>");
            out.println("");
            out.println("           <h1>Enter the amount you wish to withdraw below.</h1>");
            out.println("           <p> Please enter the desired amount you wish to withdraw.</p>");
            out.println("           <form action=\"/Banking/withdrawServlet\">");
            out.println("               Account Number: <input type=\"text\" name=\"accountNum\" size=\"20\"><br>");
            out.println("               <label for=\"withdraw\">$</label>");
            out.println("               <input type=\"number\" id=\"withdraw\" name=\"withdraw\"><br>");
            out.println("               <input type=\"submit\" value=\"Submit\">");
            out.println("           </form>");
            out.println("           <p>Withdrawal failed!</p>");
            out.println("");
            out.println("       <footer>");
            out.println("           <p> For any questions, please click the email below.<br>");
            out.println("           <a href=\"mailto:aljames@gmail.com\">aljames@gmail.com</a></p>");
            out.println("       </footer>");
            out.println("   </body>");
            out.println("</html>");
        }
    }
}
