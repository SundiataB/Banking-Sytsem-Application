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
@WebServlet(name = "New Bank Account Servlet", urlPatterns = { "/newBankAccountServlet" })
public class newBankAccountServlet extends HttpServlet {

    Logger svltLog;
    String newAccountNumber = null;
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
        //PrintWriter out = response.getWriter();

        String username = (String) session.getAttribute("username");
        //retrieve the username and password submitted by the form
        String account_type = request.getParameter("account_type");
        //int valueToWithdraw = Integer.parseInt(request.getParameter("withdraw"));

        int numForAccount_type = 0;
        if (account_type.equals("Savings")) numForAccount_type = 1;
        if (account_type.equals("Credit")) numForAccount_type = 2;
        if (account_type.equals("Checking")) numForAccount_type = 3;


        newBankAccount newBankingAccount = new newBankAccount(username, numForAccount_type);
        newAccountNumber = newBankingAccount.sendInfo();

        //INSERT initial transaction of 0 to the new account to initialize the current balance
        depositAction deposit = new depositAction(username, newAccountNumber, 0);
        deposit.sendInfo();
          
        // display results based on execution of query for true and false outcomes
        response.setContentType("text/html");
        if(newAccountNumber != null){    
            svltLog.info("new account was create");
            session.setAttribute("newAccountMessage", "A new bank account was created!"); // set attribute of newAccountMessage so that it can be retrieved in other pages
            response.sendRedirect("/Banking/AccountSummaryServlet"); // Redirect to home page
        } else {
            svltLog.info("new account was failed to initialize");
            session.setAttribute("newAccountMessage", "Bank account failed to be created!"); // set attribute of newAccountMessage so that it can be retrieved in other pages
            response.sendRedirect("/Banking/AccountSummaryServlet"); // Redirect to home page
        }
    }
}
