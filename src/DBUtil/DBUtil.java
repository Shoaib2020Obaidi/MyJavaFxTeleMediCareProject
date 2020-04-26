
package DBUtil;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil{
    
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static Connection connection = null;
    private static final String connStr = "jdbc:mysql://localhost/freerider";
    
    public static void dbConnect() throws SQLException, ClassNotFoundException{
        try{
            Class.forName(JDBC_DRIVER);
        }catch(ClassNotFoundException e){
            System.out.println("MySQL JDBC Driver is Missing");
            e.printStackTrace();
            throw e;
        }
        System.out.println("JDBC Driver has been Registered");
        try{
            connection = DriverManager.getConnection(connStr, "root", "123Shahzad123");
        }catch(SQLException e){
            System.out.println("Connection Failed "+e);
            throw e;
        }
    }
    
    public static void dbDisconnect() throws Exception{
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch(Exception e){
            throw e;
        }
    }
    
    // insert, update and delete method
    public static void dbExecuteQuery(String sqlStmt) throws ClassNotFoundException, SQLException, Exception{
        Statement stmt = null;
        try{
            dbConnect();
            stmt = connection.createStatement();
            stmt.executeUpdate(sqlStmt);
        }catch(SQLException e){
            System.out.println("Problem Occured at DBExecute Operation "+e);
            throw e;
        }
        finally{
            if(stmt != null){
                stmt.close();
            }
            dbDisconnect();
        }
    }
    
    // retrive records
    public static ResultSet dbExecute(String sqlQuery) throws ClassNotFoundException, SQLException, Exception{
        Statement stmt = null;
        ResultSet rs = null;
        CachedRowSetImpl crs = null;
        
        try{
            dbConnect();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            crs = new CachedRowSetImpl();
            crs.populate(rs);
        }catch(SQLException e){
            System.out.println("Error in BDExecute Operation "+e);
            e.printStackTrace(); 
            throw e;
        }
        finally{
            if(rs != null){
                rs.close();
            }
            if(stmt != null){
                stmt.close();
            }
            dbDisconnect();
        }
        return crs;
    }
}
