//import jakarta.servlet.*;
import java.io.*;
//import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.logging.*;
//import java.util.Enumeration;
import java.sql.*;

@WebServlet(name = "Recent Transactions Servlet", urlPatterns = "/RecentTransactionsServlet")
public class recentTransactionsServlet extends HttpServlet {

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

        String username = (String) session.getAttribute("username");

        svltLog.info("initializing the recentTransaction class");
        recentTransaction transactionHistory = new recentTransaction(username);
        svltLog.info("class created successfully");
        svltLog.info("running class function");
        transactionHistory.sendInfo();
        svltLog.info("successfully run the class function");

        //store variables from transactionHistory class
        int numberofAccounts = transactionHistory.getNumberofAccounts();
        ArrayList<String> accountNumbers = transactionHistory.getAccountNumbers();
        ArrayList<Integer> accountBalance = transactionHistory.getAccountBalance();
        ArrayList<ArrayList<Timestamp>> transactionDates = transactionHistory.getTransactionDates();
        ArrayList<ArrayList<String>> transactionDescriptions = transactionHistory.getTransactionDescriptions();
        ArrayList<ArrayList<String>> transactionCategories = transactionHistory.getTransactionCategories();
        ArrayList<ArrayList<Integer>> transactionAmounts = transactionHistory.getTransactionAmounts();

        // These are, hopefully, going to be tested against the database at some point.
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        out.println("<html lang=\"en\">");
        out.println("   <head>");
        out.println("       <meta charset=\"utf=8\">");
        out.println("       <link rel=\"stylesheet\" href=\"global.css\">");
        out.println("       <link rel=\"stylesheet\" href=\"assets/style/font-awesome-4.7.0/css/font-awesome.min.css\">");
        out.println("   </head>");
        out.println("");
        out.println("   <body>");
        // will need to do nested loops here to fix this
        // display account number and balance of account
        for(int i=0; i<numberofAccounts; i++){
            out.println("       <b><h2>Information for ACC Number "+accountNumbers.get(i)+"</h2>");
            out.println("       <h2>Current Balance: "+accountBalance.get(i)+"</h2>");
            // will need to look at how to display data in this table more thoroughly once the data is collected
            // will need to define and illustrate what account information we are looking at
            out.println("    <table class=\"content-table\" style = width:100%>");
            out.println("        <thead>");
            out.println("           <tr>");
            out.println("               <th>Date</th>");
            out.println("               <th>Description</th>");
            out.println("               <th>Category</th>");
            out.println("               <th>Amount</th>");
            out.println("           </tr>");
            out.println("        </thead>");
            // will likely need to do a loop through a resultSet and then display info based on that information
            for(int j=0; j<transactionDates.get(i).size(); j++){
                out.println("    <tbody>");
                out.println("       <tr>");
                out.println("               <td>"+transactionDates.get(i).get(j)+"</td>");
                out.println("               <td>"+transactionDescriptions.get(i).get(j)+"</td>");
                out.println("               <td>"+transactionCategories.get(i).get(j)+"</td>");
                out.println("               <td>"+transactionAmounts.get(i).get(j)+"</td>");
                out.println("       </tr>");
                out.println("    </tbody>");
            }
            out.println("    </table></b>");
        }
        out.println("   </body>");
        out.println("</html>");        
    } 
}