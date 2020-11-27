/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.util.EmailUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class VerifyServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(VerifyServlet.class);
    private String host = "smtp.gmail.com";
    private String port = "587";
    private String user = "longtran4949@gmail.com";
    private String pass = "weareONE492000";

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
        String subject = "Confirm account";
        String sentCode = EmailUtil.sendCode();
        String errEmail = null;
        HttpSession session = request.getSession();

        try {
            if (email == null) {
                errEmail = "What is your email?" ;  
            } else {
                EmailUtil.sendEmail(host, port, user, pass, email, subject, sentCode);
                session.setAttribute("SENTCODE", sentCode);
                System.out.println(sentCode);
            }

        } catch (AddressException e) {
            LOGGER.error("VerifyServlet_AddressException: " + e.getMessage());
            errEmail = "Illegal address";
        } catch (MessagingException e) {
            LOGGER.error("VerifyServlet_MessageingException: " + e.getMessage());
            errEmail = "Sending fail";
        } finally {
            request.setAttribute("ERREMAIL", errEmail);
            RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
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
