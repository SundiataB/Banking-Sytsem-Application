import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.util.logging.*;
import java.util.ArrayList;

// Extend HttpServlet class
@WebServlet(name = "Account Summary Servlet", urlPatterns = { "/AccountSummaryServlet" })
public class account_summaryServlet extends HttpServlet {

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
        String newAccountMessage = (String) session.getAttribute("newAccountMessage");

        accountSummary accSummary = new accountSummary(username);
        accSummary.sendInfo();
        // for account numbers associated with a user
        ArrayList<String> accountNumbers = accSummary.getAccountNumbers();
        ArrayList<Integer> account_types = accSummary.getAccountTypes();
        ArrayList<String> routing_numbers = accSummary.getRoutingNumbers();
        ArrayList<Integer> current_balances = accSummary.getCurrentBalances();
        int numberofAccounts = accSummary.getNumberofAccounts();
        
        response.setContentType("text/html");

        out.println("<html>");
        out.println("   <head>");
        out.println("       <title>Account Summary - Atlantic Bank.</title>");
        out.println("       <meta charset=\"utf=8\">");
        out.println("       <link rel=\"stylesheet\" href=\"global.css\">");
        out.println("       <link rel=\"stylesheet\" href=\"assets/style/font-awesome-4.7.0/css/font-awesome.min.css\">");
        out.println("   </head>");
        out.println("");
        out.println("   <body>");
        out.println("       <nav class=\"navbar\"");
        out.println("           <a href=\"/Banking/homePageServlet\"><i class=\"fa fa-home\"></i></a>");
        out.println("           <li>");
        out.println("           <div class=\"dropdown\" style=\"float:left;\">");
        out.println("              <button class=\"dropbtn\">ACCOUNTS</button>");
        out.println("               <div class=\"dropdown-content\"style=\"left:0;\">");
        out.println("                  <a href=\"/Banking/AccountSummaryServlet\">Account Summary</a>");
        out.println("                  <a href=\"recent_transactions.html\">Transaction History</a>");
        out.println("               </div>");
        out.println("           </div>");
        out.println("           </li>");
        out.println("           <li><a href=\"\">Credit Score Checker</a></li>");
        out.println("           <li><a href=\"deposit.html\">Deposits</a></li>");
        out.println("           <li><a href=\"withdraw.html\">Withdrawals</a></li>");
        out.println("           <li><a href=\"transfer.html\">Transfers</a></li>");
        out.println("           <li><a href=\"/Banking/logout\" class=\"outline\">Log Out</a></li>");
        out.println("       </nav>");
        out.println("       <header>");
        out.println("           <h1> Account Summary </h1>");
        out.println("       </header>");
        for (int i = 0; i<numberofAccounts; i++){
            if(account_types.get(i) == 1) out.println("   <h2> Account Type: Savings </h2>");
            if(account_types.get(i) == 2) out.println("   <h2> Account Type: Credit </h2>");
            if(account_types.get(i) == 3) out.println("   <h2> Account Type: Checking </h2>");
            out.println("   <h4> Account Number (#"+accountNumbers.get(i)+") </h4>");
            out.println("   <h4> Routing Number (#"+routing_numbers.get(i)+") </h4>");
            if(account_types.get(i) == 1) out.println("           <h4> Debit Card Balance: "+current_balances.get(i)+"</h4>");
            if(account_types.get(i) == 2) out.println("           <h4> Credit Card Balance: "+current_balances.get(i)+"</h4>");
            if(account_types.get(i) == 3) out.println("           <h4> Savings Balance: "+current_balances.get(i)+"</h4>");
            out.println("   <br>");
        }
       
        out.println("<nav id=\"transaction\">");
        out.println("   <a href=\"recent_Transactions.html\" class = \"recentTransactions\">Go to my Transaction History</a>");
        out.println("</nav>");
        out.println("<h1>Want to Create a new Bank Account?</h1>");
        out.println("<p>Enter the type of account you want before submitting...");
        out.println("<form action=\"/Banking/newBankAccountServlet\">");
        out.println("   <input type=\"text\" list=\"account_type\" name=\"account_type\"></label>");
        out.println("       <datalist id=\"account_type\">");
        out.println("           <option value=\"Checking\">");
        out.println("           <option value=\"Savings\">");
        out.println("           <option value=\"Credit\">");   
        out.println("       </datalist>");
        out.println("   <input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("</p>");
        if (newAccountMessage != null || !newAccountMessage.isEmpty()) out.println("<p>"+newAccountMessage+"</p>");
        session.setAttribute("newAccountMessage", ""); // set attribute of newAccountMessage so that it can be retrieved in other pages
        out.println("<footer>");
        out.println("   <p> For any questions, please click the email below.<br>");
        out.println("   <a href=\"mailto:aljames@gmail.com\">aljames@gmail.com</a></p>");
        out.println("</footer>");
        out.println("</body>");
        out.println("<html>");
    }
}
