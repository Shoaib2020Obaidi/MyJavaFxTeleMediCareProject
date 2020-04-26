
package UsersModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import DBUtil.DBUtil;

public class UsersDAO {
    public static void insertUser(String name, String password, String gender, String nationalID) throws Exception{
        String sql = "insert into project(name, password, gender, location) values('"+name+"', '"+password+"', '"+gender+"', '"+nationalID+"')";
        try{
            DBUtil.dbExecuteQuery(sql);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, " Error Occured While Inserting Values into DB");
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void updateUser(String name, String password, String gender, String nationalID) throws Exception{
        String sql = "update project set name = '"+name+"', password = '"+password+"', gender = '"+gender+"', location = '"+nationalID+"' where name = '"+name+"'";
        try{
            DBUtil.dbExecuteQuery(sql);
        }catch(SQLException e){
            System.out.println("Error Occured While Updating the Record...");
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void deleteUser(String name) throws Exception{
        String sql = "DELETE FROM `project` WHERE `name` = '"+name+"';";
        try{
            DBUtil.dbExecuteQuery(sql);
        }catch(SQLException e){
            System.out.println("Record Cannot be Deleted");
            e.printStackTrace();
            throw e;
        }
    }
    
    // to count total number of users
//    public static void countUsers() throws Exception{
//        String sql = "SELECT COUNT(*) FROM `project` ";
//        try{
//            DBUtil.dbExecuteQuery(sql);
//        }catch(SQLException e){
//            System.out.println("Record Cannot be Deleted");
//            e.printStackTrace();
//            throw e;
//        }
//    }
    
    // to fetch data from database for displaying
    public static ObservableList<Users> getAllRecords() throws Exception{
        String sql = "select * from project";
        try{
            ResultSet rsSet = DBUtil.dbExecute(sql);
            // we send this resultSet to another method and from that method we will get each emp object and we pass it to controller
            ObservableList<Users>userList = getUserObjects(rsSet);
            return userList;
        }catch(SQLException e){
            System.out.println("Error Occured While Fetching the Records" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    // this is the method which we create object for every calss
    private static ObservableList<Users>getUserObjects(ResultSet rsSet) throws SQLException {
        try{
            ObservableList<Users> userList = FXCollections.observableArrayList();
            while(rsSet.next()){
                Users user = new Users();
                
                // within the double quote should be the exact column name of table in DB
                user.setFirstName(rsSet.getString("name"));
                user.setPassword(rsSet.getString("password"));
                user.setGender(rsSet.getString("gender"));
                user.setNationalID(rsSet.getString("location"));
                
                userList.add(user);
            }
            return userList;
        }catch(SQLException e){
            System.out.println("Error Occured While Fetching Data");
            e.printStackTrace();
            throw e;
        }
    }
}
