/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import longtr.notification.NotificationDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class CommentServlet extends HttpServlet {
    
    private final Logger LOGGER = Logger.getLogger(CommentServlet.class);

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
        
        String detail = request.getParameter("txtDetail");
        Timestamp date = new Timestamp(System.currentTimeMillis());
        String owner = request.getParameter("txtOwner");
        String textDate = request.getParameter("txtDate");
        Timestamp dateOfArticle = Timestamp.valueOf(textDate);
        
        HttpSession session = request.getSession(false);
        AccountDTO sender = (AccountDTO) session.getAttribute("ACCOUNT");
        
        CommentDAO cDao = new CommentDAO();
        ServletContext sc = request.getServletContext();
        String url = "ViewPostServlet?txtOwner=" + owner + "&txtDate=" + textDate;
        
        try {
            /* TODO output your page here. You may use following sample code. */
            String errDetail = null;
            boolean error = false;
            if (detail.length() == 0) {
                error = true;
                errDetail = "Comment must not be blank";
            }
            if (detail.length() > 1000) {
                error = true;
                errDetail = "Comment contains 1000 characters";
            }
            if (!error) {
                CommentDTO cDto = new CommentDTO(detail, sender.getEmail(), owner, date, dateOfArticle);
                boolean check = cDao.addComment(cDto);
                if (check) {
                    if (sc.getAttribute(owner) == null) {
                        System.out.println("test 1");
                        List<NotificationDTO> listNoti = new ArrayList<>();
                        listNoti.add(cDto);
                        sc.setAttribute(owner, listNoti);
                    } else {
                        System.out.println("test 2");
                        List<NotificationDTO> listNoti2 = (List<NotificationDTO>) sc.getAttribute(owner);
                        listNoti2.add(cDto);
                        sc.setAttribute(owner, listNoti2);
                    }
                } else {
                    LOGGER.error("CommentServlet_ Cant not add comment");
                }
            }
            request.setAttribute("ERROR", errDetail);
            
        } catch (NamingException e) {
            LOGGER.error("CommentServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("CommentServlet_SQLException " + e.getMessage());
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
