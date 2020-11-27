/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longtr.article.ArticleDAO;
import longtr.post.PostDAO;
import longtr.post.PostDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class SearchServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(SearchServlet.class);

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

        String page = request.getParameter("numPage");
        String searchResult = request.getParameter("txtSearch");

        int numPage = 1;

        ArticleDAO artDao = new ArticleDAO();
        PostDAO pDao = new PostDAO();

//        String url = "DispatcherController?txtSearch=" + searchResult + "&btAction=Search";
        String url = "search.jsp";
        try {
            /* TODO output your page here. You may use following sample code. */
            if (page == null) {
                numPage = 1;
            } else {
                numPage = Integer.parseInt(page);
            }
            if (searchResult != null) {
                if (searchResult.length() > 0) {
                    int pageCount = artDao.pageCount(searchResult);
                    pDao.loadPost(searchResult, numPage);
                    List<PostDTO> listArticle = pDao.getListPost();
                    if (listArticle != null) {
                        request.setAttribute("LIST", listArticle);
                        request.setAttribute("PAGECOUNT", pageCount);
                    }
                }
            }
        } catch (NamingException e) {
            LOGGER.error("SearchServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("SearchServlet_SQLException " + e.getMessage());
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
