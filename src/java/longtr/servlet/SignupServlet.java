/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountErrorDTO;
import longtr.account.AccountDAO;
import longtr.util.DBHelper;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class SignupServlet extends HttpServlet {

    final static Logger LOGGER = Logger.getLogger(SignupServlet.class);
    private static String ERRSIGNUP = "signup.jsp";
    private static String LOGIN_PAGE = "login.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = ERRSIGNUP;

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String name = request.getParameter("txtName");
        String confirm = request.getParameter("txtConfirm");
        String verifyCode = request.getParameter("txtVerifyCode");
        AccountErrorDTO errDTO = new AccountErrorDTO();
        AccountDAO dao = new AccountDAO();
        HttpSession session = request.getSession();
        String sentCode = (String) session.getAttribute("SENTCODE");

        try {
            /* TODO output your page here. You may use following sample code. */
            boolean error = false;
            boolean dupli = dao.checkEmail(email);

            if (email.length() == 0) {
                error = true;
                errDTO.setErrEmail("What is your email?");
            } else {
                if (dupli) {
                    error = true;
                    errDTO.setErrEmail("Email has been used");
                }
            }
            if (password.length() < 5) {
                error = true;
                errDTO.setErrPassword("Password must not less than 6 characters");
            }
            if (name.length() == 0) {
                error = true;
                errDTO.setErrName("What is your name ?");
            }
            if (confirm.length() < 5) {
                error = true;
                errDTO.setErrConfirm("Please type password again");
            } else {
                if (!password.equals(confirm)) {
                    error = true;
                    errDTO.setErrConfirm("Password and confirm must be the same");
                }
            }
            if (sentCode == null) {
                error = true;
                errDTO.setErrCode("Click send code to verify");
            } else {
                if (!sentCode.equals(verifyCode)) {
                    error = true;
                    errDTO.setErrCode("Wrong code");
                }
            }

            request.setAttribute("ERRDTO", errDTO);
            if (error == false) {
                String encodePass = DBHelper.encodePassword(password);
                boolean insert = dao.insertNewAccount(email, encodePass, name);
                if (insert) {
                    session.removeAttribute("SENTCODE");
                    url = LOGIN_PAGE;
                } else {
                    LOGGER.error("Signup Fail");
                }
            }
        } catch (NamingException e) {
            LOGGER.error("SignupServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("SignupServlet_SQLException " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
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
