/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountDTO;
import longtr.comment.CommentDAO;
import longtr.comment.CommentDTO;
import longtr.emotion.EmotionDAO;
import longtr.emotion.EmotionDTO;
import longtr.notification.NotificationDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class NotificationServlet extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(NotificationServlet.class);
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
        
        ServletContext sc = request.getServletContext();
        
        EmotionDAO eDao = new EmotionDAO();
        CommentDAO cDao = new CommentDAO();
        
        try {
            /* TODO output your page here. You may use following sample code. */           
            List<NotificationDTO> newNoti = new ArrayList<>();

            sc.removeAttribute(dto.getEmail());
            session.removeAttribute("NUMNOTI");
            List<EmotionDTO> emotionList = eDao.loadAuthEmotion(dto.getEmail());
            List<CommentDTO> commentList = cDao.loadAuth(dto.getEmail());
            
            List<NotificationDTO> notiList = new ArrayList<>();
            
            notiList.addAll(emotionList);
            notiList.addAll(commentList);
            
            Collections.sort(notiList, Collections.reverseOrder());
            
            session.setAttribute("NOTIFILIST", notiList);
           
        }catch (NamingException e) {
            LOGGER.error("NotificationServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("NotificationServlet_SQLException " + e.getMessage());
        } finally{
            RequestDispatcher rd = request.getRequestDispatcher("notification.jsp");
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
