/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import longtr.account.AccountDTO;
import longtr.article.ArticleDAO;
import longtr.article.ArticleDTO;
import longtr.image.ImageDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@MultipartConfig(maxFileSize = 20848820)
public class PostServlet extends HttpServlet {

    private final String ERROR_PAGE = "post.jsp";
    private final String SEARCH_PAGE = "search.jsp";
    private final Logger LOGGER = Logger.getLogger(PostServlet.class);

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

        String url = ERROR_PAGE;
        HttpSession session = request.getSession(false);

        AccountDTO dto = (AccountDTO) session.getAttribute("ACCOUNT");
        Timestamp date = new Timestamp(System.currentTimeMillis());
        String title = request.getParameter("txtTitle");
        String des = request.getParameter("txtDes");

        List<Part> part = (List<Part>) request.getParts();

        InputStream input = null;

        ImageDAO imageDao = new ImageDAO();
        ArticleDAO dao = new ArticleDAO();

        ArticleDTO artDto = new ArticleDTO();

        boolean check1, check2 = true, check3 = true;
        boolean error = false;

        try {
            if (title.length() == 0) {
                error = true;
                artDto.setTitle("Title must not be blank");
            } else {
                if (title.length() > 200) {
                    error = true;
                    artDto.setTitle("Title contains 200 characters");
                }
            }
            if(des.length() == 0){
                error = true;
                artDto.setDescription("Description must not be blank");
            }else if (des.length() > 2000) {
                error = true;
                artDto.setDescription("Description contains 2000 characters");
            }
            if (error == false) {
                check1 = dao.insertArticle(dto.getEmail(), date, title, des);
                if (check1) {
                    for (Part prt : part) {
                        if (prt.getName().equalsIgnoreCase("file")) {
                            if (prt != null) {
                                if (prt.getSize() != 0) {
                                    input = prt.getInputStream();
                                    check2 = imageDao.insertImage2(dto.getEmail(), date, input);
                                    if (check2 == false) {
                                        LOGGER.error("PostServlet_StoreImage is failed");
                                        check3 = false;
                                    }
                                }else{
                                    check3 = true;
                                }
                            }
                        }
                    }
                    if (check3) {
                        url = SEARCH_PAGE;
                    }
                }
            }
            request.setAttribute("ERROR", artDto);
        } catch (NamingException e) {
            LOGGER.error("PostServlet_Naming: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("PostServlet_SQL: " + e.getMessage());
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
