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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountDAO;
import longtr.account.AccountDTO;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class StartupServlet extends HttpServlet {
    private final String SEARCH_PAGE = "search.jsp";
    private final String LOGIN_PAGE = "login.jsp";
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
        String url = LOGIN_PAGE;
        ServletContext sc = request.getServletContext();
        
        try {
            /* TODO output your page here. You may use following sample code. */
            Cookie[] cookies = request.getCookies();
            String s = null;
            if (cookies != null) {
                for (Cookie cooky : cookies) {
                    if (cooky.getName().equals("ACCOUNT")) {
                        s = cooky.getValue();
                    }
                }
                if (s != null) {
                    String[] arr = s.split("=");
                    String username = arr[0];
                    String password = arr[1];
                    
                    String encodePass = DBHelper.encodePassword(password);
                    AccountDAO dao = new AccountDAO();
                    AccountDTO dto = dao.checkLogin(username, encodePass);
                    if (dto != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("ACCOUNT", dto);
                        url = SEARCH_PAGE;
                    }
                }
            }
        } catch (SQLException ex) {
            log("StartupServlet_SQL: " + ex.getCause());
        } catch (NamingException ex) {
            log("StartupServlet_Naming: " + ex.getCause());
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
