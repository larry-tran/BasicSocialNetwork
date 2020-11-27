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
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.comment.CommentDAO;
import longtr.comment.CommentDTO;
import longtr.post.PostDAO;
import longtr.post.PostDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class ViewPostServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(ViewPostServlet.class);

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

        String owner = request.getParameter("txtOwner");
        String textDate = request.getParameter("txtDate");
        Timestamp dateOfArticle = Timestamp.valueOf(textDate);

        HttpSession session = request.getSession(false);

        PostDAO pDao = new PostDAO();
        CommentDAO comDao = new CommentDAO();

        try {
            /* TODO output your page here. You may use following sample code. */
            PostDTO pDto = pDao.viewPost(owner, dateOfArticle);
            if (pDto != null) {
                comDao.loadComment(owner, dateOfArticle);
                List<CommentDTO> listComment = comDao.getListComment();
                session.setAttribute("POSTDTO", pDto);
                session.setAttribute("LISTCOMMENT", listComment);
            } else {
                LOGGER.error("ViewPostServlet_Null PostDTO");
            }
        } catch (NamingException e) {
            LOGGER.error("ViewPostServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("ViewPostServlet_SQLException " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher("view.jsp");
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
