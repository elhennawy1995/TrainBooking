

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author Ahmed
 */
@WebServlet(urlPatterns = {"/signup"})
public class signup extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String type = (String) request.getParameter("type");
        System.out.println(type);
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String userName = request.getParameter("userName");
        String pass = request.getParameter("password");

        Connection con = DB.getActiveConnection();
        Statement stmt = con.createStatement();
        
        if (type.equals("user")) {
            String sql = "insert into users (`name`,`email`, `userName`,`password`) "
                    + "VALUES  ('" + name + "', '" + email + "','" + userName + "', '" + pass + "')";

            int rows = 0;
            try {
                rows = stmt.executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "User already exists");
                RequestDispatcher view = request.getRequestDispatcher("signup.jsp");
                view.forward(request, response);
            }
            if (rows > 0) {
                request.setAttribute("message", "User Added");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
        else{
            String sql = "insert into users (`name`,`email`, `userName`,`password`,`type`) "
                    + "VALUES  ('" + name + "', '" + email + "','" + userName + "', '" + pass + "', '"+type+"' )";

            int rows = 0;
            try {
                rows = stmt.executeUpdate(sql);
            } catch (SQLException ex) {
                request.setAttribute("message", "Admin already exists");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            if (rows > 0) {
                request.setAttribute("message", "Admin Registered");
                request.getRequestDispatcher("alogin.jsp").forward(request, response);
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
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
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
