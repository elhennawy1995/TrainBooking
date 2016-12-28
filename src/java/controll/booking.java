/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controll;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ahmed
 */
public class booking {
    double charge;
    int bookID, tripID, userID, seats;
    
    public booking(){
        charge = 0.0;
        bookID = 0;
        tripID = 0;
        userID = 0;
        seats = 0;
        
    }
    
    public int getBookID(){
        return bookID;
    }
    
    public int getTripId(){
        return tripID;
    }
    
    public double getCharge(){
        return charge;
    }
    
    public int getUserId(){
        return userID;
    }
    
    public int getSeats(){
        return seats;
    }
    
    public static ArrayList<booking> showBookigs(int id) throws SQLException{
        ArrayList<booking> b = new ArrayList<>();
        Connection con = DB.getActiveConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = null;
        
        String query = ("select * from book where idUser = '"+id+"' ");
        rs = stmt.executeQuery(query);
        
        while (rs.next()){
            booking tr = new booking();

            tr.bookID = rs.getInt(1);
            tr.tripID = rs.getInt(2);
            tr.userID = rs.getInt(3);
            tr.seats = rs.getInt(4);
            tr.charge = rs.getDouble(5);

            b.add(tr);
        }
        
        return b;
    }
    
    public static void book(String tripID, int userID, int seats, double charge) throws SQLException{
        Connection con = DB.getActiveConnection();
        Statement stmt = con.createStatement();
        
        String sql = "insert into book (tripNumber, idUser, bookSeats, fees)"
                + " values('"+tripID+"', '"+userID+"', '"+seats+"', '"+charge+"' )";
        
        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
        stmt.executeUpdate(sql);
        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=1");
    }
    
    public static void deleteBook(int bookID) throws SQLException{
        Connection con = DB.getActiveConnection();
        Statement stmt = con.createStatement();
        
        String query = "delete from book where bookID = '"+bookID+"' ";
        stmt.executeUpdate(query);
        
    }
}
