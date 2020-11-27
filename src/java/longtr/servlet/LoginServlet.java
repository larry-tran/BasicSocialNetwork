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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountDAO;
import longtr.account.AccountDTO;
import longtr.account.AccountErrorDTO;
import longtr.util.DBHelper;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class LoginServlet extends HttpServlet {

    final static Logger LOGGER = Logger.getLogger(LoginServlet.class);
    final private static String ERROR_PAGE = "login.jsp";
    final private static String SEARCH_PAGE = "search.jsp";
//    final private static String SEARCH_PAGE = "DispatcherController?btAction=Search";

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

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String encodePassword = DBHelper.encodePassword(password);

        AccountErrorDTO errDto = new AccountErrorDTO();
        
        String url = ERROR_PAGE;

        try {
            /* TODO output your page here. You may use following sample code. */
            boolean error = false;
            if (email.length() == 0) {
                error = true;
                errDto.setErrEmail("Fill your email");
            }
            if (encodePassword == null) {
                url = ERROR_PAGE;
            } else {
                AccountDAO dao = new AccountDAO();
                AccountDTO dto = dao.checkLogin(email, encodePassword);
                if (dto != null) {
                    Cookie cookie = new Cookie("ACCOUNT", email + "=" + password);
                    response.addCookie(cookie);
                    HttpSession session = request.getSession();
                    session.setAttribute("ACCOUNT", dto);
                    url = SEARCH_PAGE;
                }
                errDto.setErrPassword("Incorrect email and password");               
            }
            request.setAttribute("ERRORACCOUNT", errDto);
        } catch (NamingException e) {
            LOGGER.error("LoginServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("LoginServlet_SQLException " + e.getMessage());
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
