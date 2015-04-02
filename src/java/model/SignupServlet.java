package model;

import controller.AuthDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samuel
 */
@WebServlet(urlPatterns = {"/SignupServlet"})
public class SignupServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");

        String registerMessage = "";
        String username, password, passwordConfirm, firstName, lastName;
        int newUserID;
        boolean insertNewUser = true;

        username = request.getParameter("username");
        password = request.getParameter("password");
        passwordConfirm = request.getParameter("password_confirm");
        firstName = request.getParameter("fname");
        lastName = request.getParameter("lname");
        String checkUsernameBtn = request.getParameter("check_username");
        String submitBtn = request.getParameter("submit");

        //Prevent null pointer exception
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        if (passwordConfirm == null) {
            passwordConfirm = "";
        }
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        if (checkUsernameBtn == null) {
            checkUsernameBtn = "";
        }
        if (submitBtn == null) {
            submitBtn = "";
        }

        if (username.equals("")) {
            registerMessage += "You did not enter a username";
            insertNewUser = false;
        } else if (AuthDAO.isUserNameAvailable(username)) {
            if (checkUsernameBtn.length() != 0) {
                registerMessage += "<span style='color: green'>Username [" + username + "] is available</span>";
            }
        } else {
            registerMessage += "Username [" + username + "] is NOT available";
            insertNewUser = false;
        }

        if (submitBtn.length() != 0) { //If the submit button was pressed
            if (firstName.equals("")) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "You did not enter a first name";
                insertNewUser = false;
            }
            if (lastName.equals("")) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "You did not enter a last name";
                insertNewUser = false;
            }
            if (password.equals("")) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "You did not enter a password";
                insertNewUser = false;
            }
            if (passwordConfirm.equals("")) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "You did not confirm your password";
                insertNewUser = false;
            }
            if (!password.equals("") && !passwordConfirm.equals("") && !password.equals(passwordConfirm)) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "Passwords did not match";
                insertNewUser = false;
            }

            if (insertNewUser) {
                newUserID = AuthDAO.enterNewUser(username, password);
                if (newUserID == -1) {
                    registerMessage = "Username Insert Failed.";
                } else if (!AuthDAO.enterUserName(newUserID, firstName, lastName)) {
                    registerMessage = "Create Account Failed, Please Try Again.";
                } else {
                    rd = request.getRequestDispatcher("index.jsp");
                    request.setAttribute("indexMessage", "<span style='color: green'>Registration for [" + username + "] succesful. Log in to view your account.</span>");
                    rd.forward(request, response);
                }
            }
        } else if (checkUsernameBtn.length() == 0) { //If the check username button was not pressed
            registerMessage += "An enexpected error has occured"; //There was an error in http request
        }

        try {
            AuthDAO.DB_Close();
        } catch (Throwable e) {
            request.setAttribute("loginMessage", request.getAttribute("loginMessage") + "<br />" + e.toString());
        }

        request.setAttribute("registerMessage", registerMessage);
        rd.forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
