/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controll.DB;
import controll.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ahmed
 */
@WebServlet(urlPatterns = {"/addTrip"})
public class addTrip extends HttpServlet {

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
        
            /* TODO output your page here. You may use following sample code. */
            PrintWriter out = response.getWriter();
            Connection con = DB.getActiveConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = null;
            
            String num = request.getParameter("trainNum");
            int trainNum = Integer.parseInt(num);
            
            String source = request.getParameter("source");
            String destination = request.getParameter("destination");

            String date = request.getParameter("date");
            Date d = Date.valueOf(date);

            String dep = request.getParameter("departureTime");
            //Time departureTime = Time.valueOf(dep);

            String arr = request.getParameter("arrivalTime");
            //Time arrivalTime = Time.valueOf(arr);
            
            int reservedSeats = Integer.parseInt(request.getParameter("seats"));
            
            String pr = request.getParameter("price");
            double price = Double.parseDouble(pr);

            String trnum = request.getParameter("tripNum");
            int tripNum = Integer.parseInt(trnum);
            //------------------------------------------------------
            rs = stmt.executeQuery("select * from train where trainNumber = '"+trainNum+"'");
            
            if(!rs.next()){
                request.setAttribute("message", "Train does not exist");
                request.getRequestDispatcher("/addTrip.jsp").forward(request, response);
            }
            else {
                String sql = "insert into trips values('" + trainNum + "', '" + source + "', '" + destination + "', '" + dep + "', "
                        + "'" + arr + "', '" + d + "', '" + price + "', '" + reservedSeats + "', '" + tripNum + "')";
                int rows = 0;
                try {
                    rows = stmt.executeUpdate(sql);

                } catch (Exception e) {
                    request.setAttribute("message", "Trip already exists");
                    request.getRequestDispatcher("/addTrip.jsp").forward(request, response);
                }
                if (rows > 0) {
                    request.setAttribute("message", "Trip added successfully");
                    request.getRequestDispatcher("/addTrip.jsp").forward(request, response);
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
            Logger.getLogger(addTrip.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(addTrip.class.getName()).log(Level.SEVERE, null, ex);
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
