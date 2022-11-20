import java.io.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.*;

// Extend HttpServlet class
@WebServlet(name = "Deposit Servlet", urlPatterns = { "/depositServlet" })
public class depositServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        
        String username = (String) session.getAttribute("username");
        String accountNumToDeposit = request.getParameter("accountNum");
        int  valueToDeposit = Integer.parseInt(request.getParameter("deposit")) ; // should be int but incorrect for some reason
        
        // backend class
        depositAction deposit = new depositAction(username, accountNumToDeposit, valueToDeposit);
        boolean depositResult = deposit.sendInfo();

        // display results based on execution of query for true and false outcomes
        response.setContentType("text/html");
        if(depositResult){    
            out.println("<html lang=\"en\">");
            out.println("   <head>");
            out.println("       <meta charset=\"utf=8\">");
            out.println("       <link rel=\"stylesheet\" href=\"global.css\">");
            out.println("       <link rel=\"stylesheet\" href=\"assets/style/font-awesome-4.7.0/css/font-awesome.min.css\">");
            out.println("       <title>Deposit - Atlantic Bank.</title>");
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
            out.println("           <h1>You have arrived at the deposit depot.</h1>");
            out.println("           <h2>Enter your information below.</h2>");
            out.println("           <form action=\"/Banking/depositServlet\">");
            out.println("               User Account Number: <input type=\"text\" name=\"accountNum\" size=\"20\" maxlength = \"15\"><br>");
            out.println("               <label for=\"deposit\">$</label>");
            out.println("               <input type=\"number\" id=\"deposit\" name=\"deposit\"><br>");
            out.println("               <input type=\"submit\" value=\"Submit\">");
            out.println("           </form>");
            out.println("           <p>Deposit completed!</p>");
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
            out.println("       <title>Deposit - Atlantic Bank.</title>");
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
            out.println("           <h1>You have arrived at the deposit depot.</h1>");
            out.println("           <h2>Enter your information below.</h2>");
            out.println("           <form action=\"/Banking/depositServlet\">");
            out.println("               User Account Number: <input type=\"text\" name=\"accountNum\" size=\"20\" maxlength = \"15\"><br>");
            out.println("               <label for=\"deposit\">$</label>");
            out.println("               <input type=\"number\" id=\"deposit\" name=\"deposit\"><br>");
            out.println("               <input type=\"submit\" value=\"Submit\">");
            out.println("           </form>");
            out.println("           <p>Deposit incomplete!</p>");
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