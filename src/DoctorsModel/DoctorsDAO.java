
package DoctorsModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import DBUtil.DBUtil;

public class DoctorsDAO {
    public static void insertDoctor(String fullName, String speciality, String doctorID, String clinicLocation, String daysAvailable, String timeAvailable, String mobileNo, String emailAddress, String userName, String password) throws Exception{
        String sql = "insert into doctor(fullName, speciality, doctorID, clinicLocation, daysAvailable, timeAvailable, mobileNumber, emailAddress, userName, password) values('"+fullName+"', '"+speciality+"', '"+doctorID+"', '"+clinicLocation+"','"+daysAvailable+"', '"+timeAvailable+"', '"+mobileNo+"', '"+emailAddress+"', '"+userName+"', '"+password+"')";
        try{
            DBUtil.dbExecuteQuery(sql);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, " Error Occured While Inserting Values into DB");
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void updateDoctor(String fullName, String speciality, String doctorID, String clinicLocation, String daysAvailable, String timeAvailable, String mobileNo, String emailAddress, String userName, String password) throws Exception{
        String sql = "update doctor set fullName = '"+fullName+"', speciality = '"+speciality+"', doctorID = '"+doctorID+"', clinicLocation = '"+clinicLocation+"', daysAvailable = '"+daysAvailable+"', timeAvailable = '"+timeAvailable+"', mobileNumber = '"+mobileNo+"', emailAddress = '"+emailAddress+"', userName = '"+userName+"', password = '"+password+"' where fullName = '"+fullName+"' ";
        try{
            DBUtil.dbExecuteQuery(sql);
        }catch(SQLException e){
            System.out.println("Error Occured While Updating the Record...");
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void deleteDoctor(String fullName) throws Exception{
        String sql = "DELETE FROM `doctor` WHERE `fullName` = '"+fullName+"';";
        
        try{
            DBUtil.dbExecuteQuery(sql);
        }catch(SQLException e){
            System.out.println("Record Cannot be Deleted");
            e.printStackTrace();
            throw e;
        }
    }
    // DELETE FROM yourTableName WHERE yourColumnName1=yourValue ORDER BY yourColumnName2 DESC LIMIT 1;
    public static void deleteLastDoctor(String fullName) throws Exception{
        String sql = "DELETE FROM `doctor` WHERE `fullName` = '"+fullName+"';";
        
        try{
            DBUtil.dbExecuteQuery(sql);
        }catch(SQLException e){
            System.out.println("Record Cannot be Deleted");
            e.printStackTrace();
            throw e;
        }
    }
    // to fetch data from database for displaying
    public static ObservableList<Doctors> getAllRecords() throws Exception{
        String sql = "select * from doctor";
        try{
            ResultSet rsSet = DBUtil.dbExecute(sql);
            // we send this resultSet to another method and from that method we will get each emp object and we pass it to controller
            ObservableList<Doctors>doctorList = getDoctorObjects(rsSet);
            return doctorList;
        }catch(SQLException e){
            System.out.println("Error Occured While Fetching the Records" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<Doctors> getLatestRecord() throws Exception{
        // SELECT    *FROM      your_table ORDER BY  your_auto_increment_field DESC LIMIT     1;
        String sql = "SELECT * FROM doctor ORDER BY iddoctor DESC LIMIT 1";
        try{
            ResultSet rsSet = DBUtil.dbExecute(sql);
            // we send this resultSet to another method and from that method we will get each emp object and we pass it to controller
            ObservableList<Doctors>doctorList = getDoctorObjects(rsSet);
            return doctorList;
        }catch(SQLException e){
            System.out.println("Error Occured While Fetching the Records" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    // this is the method which we create object for every calss
    private static ObservableList<Doctors>getDoctorObjects(ResultSet rsSet) throws SQLException {
        try{
            ObservableList<Doctors> doctorList = FXCollections.observableArrayList();
            while(rsSet.next()){
                Doctors doctor = new Doctors();
                
                doctor.setFullName(rsSet.getString("fullName"));
                doctor.setSpeciality(rsSet.getString("speciality"));
                doctor.setDoctorID(rsSet.getString("doctorID"));
                doctor.setClinicLocation(rsSet.getString("clinicLocation"));
                doctor.setDaysAvailable(rsSet.getString("daysAvailable"));
                doctor.setTimeAvailable(rsSet.getString("timeAvailable"));
                doctor.setMobileNo(rsSet.getString("mobileNumber"));
                doctor.setEmailAddress(rsSet.getString("emailAddress"));
                doctor.setUserName(rsSet.getString("userName"));
                doctor.setPassword(rsSet.getString("password"));
                
                doctorList.add(doctor);
            }
            return doctorList;
        }catch(SQLException e){
            System.out.println("Error Occured While Fetching Data");
            e.printStackTrace();
            throw e;
        }
    }
}
