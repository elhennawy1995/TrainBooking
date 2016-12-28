package controll;


import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class AllTrips {
    int trainNum, seats;
    String source, destination, depTime, arrivTime, date, tripNum;
    Double price;
    
    public AllTrips(){
        trainNum = 0;
        seats = 0;
        source = "";
        destination = "";
        depTime = "";
        arrivTime = "";
        date = "";
        price = 0.0;
        tripNum = "";
    }
    
    public void setTrainNum(int num){
        this.trainNum = num;
    }
    
    public int getTrainNum()  {
        return trainNum;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }
    
    public void setPrice(double price){
       this.price = price;
    }
    
    public double getPrice()  {
        return price;
    }
    
    public void setSrc (String source){
       this.source = source;
    }
    
    public String getSrc()  {
        return source;
    }
    
    public void setDest (String destination){
       this.destination = destination;
    }
    
    public String getDest()  {
        return destination;
    }
    
    public void setDepTime (String depTime){
       this.depTime = depTime;
    }
    
    public String getDepTime()  {
        return depTime;
    }
    
    public void setArrTime (String arrivTime){
       this.arrivTime = arrivTime;
    }
    
    public String getArrTime()  {
        return arrivTime;
    }
    
    public void setDate (String date){
       this.date = date;
    }
    
    public String getDate()  {
        return date;
    }
    
    public void setTripNum (String tripNum){
       this.tripNum = tripNum;
    }
    
    public String getTripNum()  {
        return tripNum;
    }
    
    public static ArrayList<AllTrips> showTrips() throws SQLException{
        Connection con = DB.getActiveConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = null;
        
        ArrayList<AllTrips> trip = new ArrayList<>();
//        ArrayList data = null;
        
        String query = ("select * from trips");
        rs = stmt.executeQuery(query);
        
        while (rs.next()){
            AllTrips tr = new AllTrips();
            
            tr.tripNum = rs.getString(9);// trip number
            tr.trainNum = rs.getInt(1);// train number
            tr.source = rs.getString(2);// source
            tr.destination = rs.getString(3);// destination
            tr.depTime = rs.getString(4);//departure time
            tr.arrivTime = rs.getString(5);// arrival time
            tr.date = rs.getString(6);// date
            tr.seats = rs.getInt(7);// reserved seats
            tr.price = rs.getDouble(8);//price
            
            trip.add(tr);
        }
        
        return trip;
    }
    
    public static AllTrips selectTrip(String tripNum) throws SQLException{
        Connection con = DB.getActiveConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = null;
        AllTrips trip = new AllTrips();
        
        String sql = "select * from trips where tripNum = '"+tripNum+"' ";
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            trip.tripNum = rs.getString(9);// trip number
            trip.trainNum = rs.getInt(1);// train number
            trip.source = rs.getString(2);// source
            trip.destination = rs.getString(3);// destination
            trip.depTime = rs.getString(4);//departure time
            trip.arrivTime = rs.getString(5);// arrival time
            trip.date = rs.getString(6);// date
            trip.seats = rs.getInt(7);// reserved seats
            trip.price = rs.getDouble(8);//price
        
        }
        return trip;
    }
    
    public static void deleteTrip(String tripNum) throws SQLException{
        Connection con = DB.getActiveConnection();
        Statement stmt = con.createStatement();
        
        String query = "delete from trips where tripNum = '"+tripNum+"' ";
        stmt.executeUpdate(query);
        
    }
    
    public static boolean updateTrip(String tripNum, String depTime, String arrTime, Date date, double price) throws SQLException{
        Connection con = DB.getActiveConnection();
        Statement stmt = con.createStatement();
        
        String query = "update trips set departureTime='"+depTime+"', arrivalTime='"+arrTime+"', date='"+date+"', price='"+price+"'"
                + " where tripNum = '"+tripNum+"' ";
        int i = stmt.executeUpdate(query);
        if(i>0)
            return true;
        return false;
        
    }
    
    public static void updateSeats(String tripNum, int seats) throws SQLException{
        Connection con = DB.getActiveConnection();
        Statement stmt = con.createStatement();
        int reserved = 0;

        AllTrips trip = AllTrips.selectTrip(tripNum);
        
        reserved = trip.getSeats();
        reserved+=seats;

        String query = "update trips set `reservedSeats` = '"+reserved+"'  where tripNum = '"+tripNum+"' ";
        stmt.executeUpdate(query);
        
    }
    public static ArrayList<AllTrips> search(String src, String dest) throws SQLException{
        Connection con = DB.getActiveConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = null;
        ArrayList<AllTrips> t = new ArrayList();
        
        String sql = "select * from trips where source = '"+src+"' and destination = '"+dest+"' ";
        rs=stmt.executeQuery(sql);
        
        while(rs.next()){
            AllTrips trip = new AllTrips();
            trip.tripNum = rs.getString(9);// trip number
            trip.trainNum = rs.getInt(1);// train number
            trip.source = rs.getString(2);// source
            trip.destination = rs.getString(3);// destination
            trip.depTime = rs.getString(4);//departure time
            trip.arrivTime = rs.getString(5);// arrival time
            trip.date = rs.getString(6);// date
            trip.seats = rs.getInt(7);// reserved seats
            trip.price = rs.getDouble(8);//price
            t.add(trip);
        }
        
        return t;
    }
}

