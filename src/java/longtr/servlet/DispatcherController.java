/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@MultipartConfig(maxFileSize = 20848820)
public class DispatcherController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DispatcherController.class);
    private final String LOGIN_PAGE = "login.jsp";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String SEARCH_PAGE = "search.jsp";
    private final String SEARCH_CONTROLLER = "SearchServlet";
    private final String SIGNUP_CONTROLLER = "SignupServlet";
    private final String VERIFY_CONTROLLER = "VerifyServlet";
    private final String POST_CONTROLLER = "PostServlet";
    private final String VIEW_CONTROLLER = "ViewPostServlet";
    private final String COMMENT_CONTROLLER = "CommentServlet";
    private final String SENDEMO_CONTROLLER = "SendEmoServlet";
    private final String MANAGE_CONTROLLER = "ManagePostServlet";
    private final String DELETEPOST_CONTROLLER = "DeletePostServlet";
    private final String NOTI_CONTROLLER = "NotificationServlet";
    private final String STARTUP_CONTROLLER = "StartupServlet";
    private final String REMOVECOMMENT_CONTROLLER = "RemoveCommentServlet";

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
        HttpSession session = request.getSession();
        AccountDTO dto = (AccountDTO) session.getAttribute("ACCOUNT");
        String button = request.getParameter("btAction");
        String url = "login.html";

        try {
            /* TODO output your page here. You may use following sample code. */
            if (dto == null) {
                if(button == null){
                    url = STARTUP_CONTROLLER; 
                } else if (button.equals("Login")) {
                    url = LOGIN_CONTROLLER;
                } else if (button.equals("Signup")) {
                    url = SIGNUP_CONTROLLER;
                } else if (button.equals("SendCode")) {
                    url = VERIFY_CONTROLLER;
                } else {
                    url = LOGIN_PAGE;
                }
            } else {
                if (button == null) {
                    url = STARTUP_CONTROLLER;
                } else if (button.equals("Login")) {
                    url = LOGIN_CONTROLLER;
                } else if (button.equals("Logout")) {
                    url = LOGOUT_CONTROLLER;
                } else if (button.equals("Search")) {
                    url = SEARCH_CONTROLLER;
                } else if (button.equals("Post")) {
                    url = POST_CONTROLLER;
                } else if (button.equals("Read More")) {
                    url = VIEW_CONTROLLER;
                } else if (button.equals("Comment")) {
                    url = COMMENT_CONTROLLER;
                } else if (button.equals("SendEmo")) {
                    url = SENDEMO_CONTROLLER;
                } else if (button.equals("Manage")) {
                    url = MANAGE_CONTROLLER;
                } else if (button.equals("Delete")) {
                    url = DELETEPOST_CONTROLLER;
                } else if (button.equals("Notification")) {
                    url = NOTI_CONTROLLER;
                } else if (button.equals("Remove")) {
                    url = REMOVECOMMENT_CONTROLLER;
                }
            }
        } catch (NullPointerException ex) {
            LOGGER.error("DispatcherController_Null pointer exception");
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
