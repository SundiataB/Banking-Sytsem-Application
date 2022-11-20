//import jakarta.servlet.*;
import java.io.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.*;

// This is for the create_account.html page!

@WebServlet(name = "Create Account Servlet", urlPatterns = "/createAccountServlet")
public class createAccountServlet extends HttpServlet {

    Logger svltLog;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // utilize the svltLog
        if (svltLog == null) {
            svltLog  = Logger.getLogger (this.getClass().getName() + "Servlet");
        }
        svltLog.warning ("Firing up servlet " + this.getClass().getName());
                
        svltLog.info("grabbing user information from form ");
        //retrieve the username and password submitted by the form
        String userName = request.getParameter("username");
        String passWord = request.getParameter("account_password");
        // the remaining variables we need
        String firstname = request.getParameter("first_name");
        String lastname = request.getParameter("last_name");
        String email = request.getParameter("email");
        String contactnumber = request.getParameter("contact_number");
        String streetaddress = request.getParameter("street_address");
        String postcode = request.getParameter("postcode");
        String dateofbirth = request.getParameter ("date_of_birth");
        String ssn = request.getParameter("social_security_num");

        svltLog.info("initializing the createAccount class");
        createAccount newAccount = new createAccount(userName, passWord, firstname, lastname, email, contactnumber, streetaddress, postcode, dateofbirth, ssn);
        svltLog.info("class created successfully");
        svltLog.info("running class function");
        Boolean queryExecuted = newAccount.sendInfo();
        svltLog.info("successfully run the class function");
        
        if(queryExecuted == true){
            svltLog.warning("connection was tested, query was run if successful connection and now redirect the user");
            request.getRequestDispatcher("login.html").forward(request, response); // Redisplay login.html.
        } else {
            svltLog.warning("query wasn't executed - display info to user to register again");
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");

            out.println("<html lang=\"en\">");
            out.println("   <head>");
            out.println("       <meta charset=\"utf=8\">");
            out.println("       <link rel=\"stylesheet\" href=\"global.css\">");
            out.println("       <link rel=\"stylesheet\" href=\"assets/style/font-awesome-4.7.0/css/font-awesome.min.css\">");
            out.println("       <title>Create Account - Atlantic Bank</title>");
            out.println("   </head>");
            out.println("");
            out.println("   <body>");
            out.println("       <nav class=\"navbar\"");
            out.println("           <a href=\"index.html\"><i class=\"fa fa-home\"></i></a>");
            out.println("           <li><a href=\"credit_score_checker.html\">Credit Score Checker</a></li>");
            out.println("           <li>");
            out.println("               <div class=\"dropdown\" style=\"float:left;\">");
            out.println("                   <button class=\"dropbtn\">MORE INFORMATION</button>");
            out.println("                   <div class=\"dropdown-content\"style=\"left:0;\">");
            out.println("                       <a href=\"faq.html\">FAQS</a> ");
            out.println("                       <a href=\"about.html\">ABOUT US</a>");
            out.println("                       <a href=\"contact.html\">CONTACT US</a>");
            out.println("                   </div>");
            out.println("               </div>");
            out.println("          </li>");
            out.println("          <li><a href=\"login.html\" class=\"outline\">Log In</a></li>");
            out.println("          <li><a href=\"create_account.html\" class=\"outline\">Register</a></li>");
            out.println("       </nav>");
            out.println("");
            out.println("           <center><h1 class=\"indextitle\">Account information already exists</h1>");
            out.println("           <h3>Either Social Security Number is already in use or Username is already in use</h1></center>");
            out.println("");
            out.println("           <p><a href=\"create_account.html\">Return to register</a></p>");
            out.println("       <footer>");
            out.println("           <p> For any questions, please click the email below.<br>");
            out.println("           <a href=\"mailto:aljames@gmail.com\">aljames@gmail.com</a></p>");
            out.println("       </footer>");
            out.println("   </body>");
            out.println("</html>");
        }
    }
}
