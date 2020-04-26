package PatientModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import DBUtil.DBUtil;
import static projectmine.UserLoginController.passUserPatientName;
import static projectmine.DoctorLoginController.passUserName;
import static projectmine.UsersMainPage_AfterLoginController.passMyName;

public class PatientDAO {

    public static String delete;
    public static String str;

    // for making appointment with Doctors
    public static void makeAppointment(String fullName, int age, String disease, String date, String time, String mobileNo, String bloodType, String language, String address, String choosenDr) throws Exception {
        String sql = "INSERT INTO patientappointment(fullName, age, disease, date, time, mobileNo, bloodType, language, address, choosenDr) VALUES ('" + fullName + "', '" + age + "', '" + disease + "', '" + date + "', '" + time + "', '" + mobileNo + "', '" + bloodType + "', '" + language + "', '" + address + "', '" + choosenDr + "') ";
        try {
            DBUtil.dbExecuteQuery(sql);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error Occured While Inserting Values into DB");
            e.printStackTrace();
            throw e;
        }
    }

    // After Checking the patients, the Record goes to the History table
    public static void insertIntopatientHistoryTable(String fullName, int age, String disease, String date, String time, String mobileNo, String bloodType, String language, String address, String choosenDr) throws Exception {
        String sql = "INSERT INTO patientshistory(fullName, age, disease, date, time, mobileNo, bloodType, language, address, choosenDr) VALUES ('" + fullName + "', '" + age + "', '" + disease + "', '" + date + "', '" + time + "', '" + mobileNo + "', '" + bloodType + "', '" + language + "', '" + address + "', '" + choosenDr + "') ";
        try {
            DBUtil.dbExecuteQuery(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error Occured While Inserting Values into DB");
            e.printStackTrace();
            throw e;
        }
    }

    // update records
    public static void updatePatient(String fullName, int age, String disease, String date, String time, String mobileNo, String bloodType, String language, String address, String choosenDr) throws Exception {
        String sql = "update patientappointment set fullName = '" + fullName + "', age = '" + age + "', disease = '" + disease + "', date = '" + date + "', time = '" + time + "', mobileNo = '" + mobileNo + "', bloodType = '" + bloodType + "', language = '" + language + "', address = '" + address + "', choosenDr = '" + choosenDr + "' where fullName = '" + fullName + "' ";
        try {
            DBUtil.dbExecuteQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error Occured While Updating the Record...");
            e.printStackTrace();
            throw e;
        }
    }

    // delete Records
    public static void deletePatient(String fullName) throws Exception {
        delete = "DELETE FROM `patientappointment` WHERE `fullName` = '" + fullName + "'; ";
        try {
            DBUtil.dbExecuteQuery(delete);
        } catch (SQLException e) {
            System.out.println("Record Cannot be Deleted");
            e.printStackTrace();
            throw e;
        }
        System.out.println("I think it is correct value: " + str); // correct value
        str = fullName;
    }

    // show only info of one user in his page 
    public static ObservableList<Patient> getOneRecord() throws Exception {
        String strFn = passUserPatientName();
        String sql = "SELECT * FROM patientappointment WHERE fullName = '" + strFn + "' ";
        try {
            ResultSet rsSet = DBUtil.dbExecute(sql);
            // we send this resultSet to another method and from that method we will get each emp object and we pass it to controller
            ObservableList<Patient> patientListes = getPatientObjects(rsSet);
            return patientListes;
        } catch (SQLException e) {
            System.out.println("Error Occured While Fetching the Records" + e);
            e.printStackTrace();
            throw e;
        }
    }

    // for doctor main page to see his own pateints in the table
    public static ObservableList<Patient> getDrPatientsRecords() throws Exception {
        String drUN = passUserName();
        String sql = "SELECT * FROM patientappointment WHERE choosenDr = '" + drUN + "' ";
        try {
            ResultSet rsSet = DBUtil.dbExecute(sql);
            // we send this resultSet to another method and from that method we will get each emp object and we pass it to controller
            ObservableList<Patient> patientList = getPatientObjects(rsSet);
            return patientList;
        } catch (SQLException e) {
            System.out.println("Error Occured While Fetching the Records" + e);
            e.printStackTrace();
            throw e;
        }
    }

    // to fetch data from database for displaying
    public static ObservableList<Patient> getAllRecords() throws Exception {
        String sql = "select * from patientappointment";
        try {
            ResultSet rsSet = DBUtil.dbExecute(sql);
            // we send this resultSet to another method and from that method we will get each emp object and we pass it to controller
            ObservableList<Patient> patientList = getPatientObjects(rsSet);
            return patientList;
        } catch (SQLException e) {
            System.out.println("Error Occured While Fetching the Records" + e);
            e.printStackTrace();
            throw e;
        }
    }

    // get All History Records - any time user deletes (checks) his patient, it disappears from available patients' list and comes in history list
    public static ObservableList<Patient> getAllHistoryRecords() throws Exception {
        String sql = "select * from patientshistory";
        try {
            ResultSet rsSet = DBUtil.dbExecute(sql);
            // we send this resultSet to another method and from that method we will get each emp object and we pass it to controller
            ObservableList<Patient> patientList = getPatientObjects(rsSet);
            return patientList;
        } catch (SQLException e) {
            System.out.println("Error Occured While Fetching the Records" + e);
            e.printStackTrace();
            throw e;
        }
    }

    // this is the method which we create object for every calss
    public static ObservableList<Patient> getPatientObjects(ResultSet rsSet) throws SQLException {
        try {
            ObservableList<Patient> patientList = FXCollections.observableArrayList();
            while (rsSet.next()) {
                Patient patient = new Patient();

                patient.setPatFullName(rsSet.getString("fullName"));
                patient.setPatAge(rsSet.getString("age"));
                patient.setPatDisease(rsSet.getString("disease"));
                patient.setPatCurrentDate(rsSet.getString("date"));
                patient.setPatientCurrentTime(rsSet.getString("time"));
                patient.setPatMobileNo(rsSet.getString("mobileNo"));
                patient.setPatBloodType(rsSet.getString("bloodType"));
                patient.setPatLanguage(rsSet.getString("language"));
                patient.setPatAddress(rsSet.getString("address"));
                patient.setpreferedDoctor(rsSet.getString("choosenDr"));

                patientList.add(patient);
            }
            return patientList;
        } catch (SQLException e) {
            System.out.println("Error Occured While Fetching Data");
            e.printStackTrace();
            throw e;
        }
    }

    // for searching
    public static ObservableList<Patient> searchPatient(String fullName) throws Exception {
        String sql = "select * from patientappointment where fullName = " + fullName;

        try {
            ResultSet rsSet = DBUtil.dbExecute(sql);
            ObservableList<Patient> list = getPatientObjects(rsSet);
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Occured While Searching the Record " + e);
            e.printStackTrace();
            throw e;
        }
    }
}
