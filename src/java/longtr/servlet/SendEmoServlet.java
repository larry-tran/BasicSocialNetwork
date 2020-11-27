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
import longtr.emotion.EmotionDAO;
import longtr.emotion.EmotionDTO;
import longtr.notification.NotificationDTO;
import longtr.post.PostDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class SendEmoServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(SendEmoServlet.class);

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

        String btEmotion = request.getParameter("emo");

        HttpSession session = request.getSession(false);

        AccountDTO sender = (AccountDTO) session.getAttribute("ACCOUNT");
        PostDTO postDto = (PostDTO) session.getAttribute("POSTDTO");

        Timestamp date = new Timestamp(System.currentTimeMillis());

        ServletContext sc = request.getServletContext();

        EmotionDAO eDao = new EmotionDAO();
        EmotionDTO eDto = null;

        String url = "ViewPostServlet?txtOwner=" + postDto.getArticleDto().getOwner() + "&txtDate=" + postDto.getArticleDto().getDate();

        try {
            /* TODO output your page here. You may use following sample code. */
            if (postDto != null) {
                String emo = eDao.checkExistEmo(sender.getEmail(), postDto.getArticleDto().getOwner(), postDto.getArticleDto().getDate());
                if (emo == null) {
                    eDto = new EmotionDTO(btEmotion, sender.getEmail(), postDto.getArticleDto().getOwner(), date, postDto.getArticleDto().getDate());
                    boolean check = eDao.addEmotion(eDto);
                    if (check == false) {
                        LOGGER.error("SendEmoServlet_Can not add emo");
                    }
                } else if (emo.equals("LIKE")) {
                    if (btEmotion.equals("LIKE")) {
                        eDto = new EmotionDTO("UNCHECKE", sender.getEmail(), postDto.getArticleDto().getOwner(), date, postDto.getArticleDto().getDate());
                        boolean check = eDao.editEmotion(eDto);
                        if (check == false) {
                            LOGGER.error("SendEmoServlet_Can not edit emo");
                        }
                    } else {
                        eDto = new EmotionDTO("DISLIKE", sender.getEmail(), postDto.getArticleDto().getOwner(), date, postDto.getArticleDto().getDate());
                        boolean check = eDao.editEmotion(eDto);
                        if (check == false) {
                            LOGGER.error("SendEmoServlet_Can not edit emo");
                        }
                    }
                } else if (emo.equals("DISLIKE")) {
                    if (btEmotion.equals("LIKE")) {
                        eDto = new EmotionDTO("LIKE", sender.getEmail(), postDto.getArticleDto().getOwner(), date, postDto.getArticleDto().getDate());
                        boolean check = eDao.editEmotion(eDto);
                        if (check == false) {
                            LOGGER.error("SendEmoServlet_Can not edit emo");
                        }
                    } else {
                        eDto = new EmotionDTO("UNCHECKE", sender.getEmail(), postDto.getArticleDto().getOwner(), date, postDto.getArticleDto().getDate());
                        boolean check = eDao.editEmotion(eDto);
                        if (check == false) {
                            LOGGER.error("SendEmoServlet_Can not edit emo");
                        }
                    }
                } else if (emo.equals("UNCHECKE")) {
                    if (btEmotion.equals("LIKE")) {
                        eDto = new EmotionDTO("LIKE", sender.getEmail(), postDto.getArticleDto().getOwner(), date, postDto.getArticleDto().getDate());
                        boolean check = eDao.editEmotion(eDto);
                        if (check == false) {
                            LOGGER.error("SendEmoServlet_Can not edit emo");
                        }
                    } else {
                        eDto = new EmotionDTO("DISLIKE", sender.getEmail(), postDto.getArticleDto().getOwner(), date, postDto.getArticleDto().getDate());
                        boolean check = eDao.editEmotion(eDto);
                        if (check == false) {
                            LOGGER.error("SendEmoServlet_Can not edit emo");
                        }
                    }
                }

                if (sc.getAttribute(postDto.getArticleDto().getOwner()) == null) {
                    List<NotificationDTO> listNoti = new ArrayList<>();
                    listNoti.add(eDto);
                    sc.setAttribute(postDto.getArticleDto().getOwner(), listNoti);
                } else {
                    List<NotificationDTO> listNoti2 = (List<NotificationDTO>) sc.getAttribute(postDto.getArticleDto().getOwner());
                    listNoti2.add(eDto);
                    sc.setAttribute(postDto.getArticleDto().getOwner(), listNoti2);
                }
            }
        } catch (NamingException e) {
            LOGGER.error("EmotionServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("EmotionServlet_SQLException " + e.getMessage());
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
