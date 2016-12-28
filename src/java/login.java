/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import controll.userData;
import controll.DB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Ahmed
 */
@WebServlet(urlPatterns = {"/login"})
public class login extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            String userName = request.getParameter("userName");
            String pass = request.getParameter("password");
            
            userData data = new userData();
            Connection con = DB.getActiveConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = null;
            
            String type = (String)request.getParameter("type");
            System.out.println(type);
            if (type.equals("user")) {

                try {
                    rs = stmt.executeQuery("select * from users where userName = '" + userName + "' and password = '" + pass + "' and type = '"+type+"' ");
                    if (rs.next()) {
                        HttpSession session = request.getSession(true);

                        data.setID(rs.getInt("userID"));
                        data.setName(rs.getString("name"));
                        data.setuserName(rs.getString("userName"));
                        data.setEmail(rs.getString("email"));
                        data.setPass(rs.getString("password"));

                        session.setAttribute("user", data);

                        RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
                        rd.include(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Invalid user or password");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                try {
                    rs = stmt.executeQuery("select * from users where userName = '" + userName + "' and password = '" + pass + "' and type = '"+type+"' ");
                    if (rs.next()) {
                        HttpSession session = request.getSession(true);

                        data.setID(rs.getInt("userID"));
                        data.setName(rs.getString("name"));
                        data.setuserName(rs.getString("userName"));
                        data.setEmail(rs.getString("email"));
                        data.setPass(rs.getString("password"));

                        session.setAttribute("admin", data);

                        RequestDispatcher rd = request.getRequestDispatcher("tripsInfo.jsp");
                        rd.include(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Invalid user or password");
                        RequestDispatcher rd = request.getRequestDispatcher("alogin.jsp");
                        rd.forward(request, response);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
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
