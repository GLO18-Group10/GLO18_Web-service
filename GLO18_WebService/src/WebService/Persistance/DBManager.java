package WebService.Persistance;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Random;
/**
 *
 * @author Robin
 */
public class DBManager {

    private final String dbURL = "jdbc:postgresql://tek-mmmi-db0a.tek.c.sdu.dk:5432/si3_2018_group_10_db";
    private final String dbUsername = "si3_2018_group_10";
    private final String dbPassWord = "Punic3;drops";

    /**
     * this is test method to test the database connection
     *
     * @return a string of data from the database
     */
    public String getTest() {
        String testResult = "";
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM testTable");

            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(result.getString("col01") + ";");
                sb.append(result.getString("col02") + ";");
            }
            testResult = sb.toString();
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return testResult;
    }

    /**
     * a test method to set something in the database testTable
     */
    public void setTest(String c1, String c2) {
        ResultSet result = null;
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("INSERT INTO testTable VALUES (?, ?)");
            PStatement.setString(1, c1);
            PStatement.setString(2, c2);
            result = PStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Method to get the customer info
     *
     * @param customerID: the id of the customer that wants their info
     * @return a string of the data
     */
    public String getCustomerInfo(String customerID) {
        String customerInfo = "";
        ResultSet result = null;
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("SELECT name, birthday, phonenumber, address, email FROM customer WHERE id = (?)");
            PStatement.setString(1, customerID);
            result = PStatement.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(result.getString("name") + ";");
                sb.append(result.getString("birthday") + ";");
                sb.append(result.getString("phonenumber") + ";");
                sb.append(result.getString("address") + ";");
                sb.append(result.getString("email") + ";");
            }
            customerInfo = sb.toString();
        } catch (SQLException ex) {
            System.out.println("Error; getCustomerInfo; SQL exception");
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return customerInfo;
    }

    public String getCustomerIDs() {
        String IDs = "";
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT id FROM customer");

            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(result.getString("id") + ";");
            }
            IDs = sb.toString();
        } catch (SQLException ex) {
            System.out.println("Error; getCustomerIDs; SQL exception");
            ex.printStackTrace();
        }
        return IDs;
    }

    public String getAccountNos(String customerID) {
        String accountNos = "";
        ResultSet result = null;
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("SELECT bankaccountid FROM hasbankaccount WHERE ID =(?)");
            PStatement.setString(1, customerID);
            result = PStatement.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(result.getString("bankaccountid") + ";");
            }
            accountNos = sb.toString();
        } catch (SQLException ex) {
            System.out.println("Error; getAccountNos; SQL exception");
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        accountNos = accountNos.replace(" ", "");
        return accountNos;
    }

    public String getAccountBalance(String accountID) {
        String Balance = "";
        ResultSet result = null;
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("SELECT Balance FROM BankAccount WHERE ID =(?)");
            PStatement.setString(1, accountID);
            result = PStatement.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(result.getString("Balance"));
            }
            Balance = sb.toString();
        } catch (SQLException ex) {
            System.out.println("Error; getAccountBalance; SQL exception");
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Balance;
    }

    public boolean doesAccountExist(String accountID) {
        String count = "0";
        ResultSet result = null;
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("SELECT count(*) FROM BankAccount WHERE ID = (?)");
            PStatement.setString(1, accountID);
            result = PStatement.executeQuery();
            result.next();
            count = result.getString(1);
        } catch (SQLException ex) {
            System.out.println("Error; doesAccountExist; SQL exception");
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count.equals("1");
    }

    public void updateAccountBalance(String accountID, int amount) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("UPDATE BankAccount SET Balance = (?) WHERE ID = (?)");
            PStatement.setInt(1, amount);
            PStatement.setString(2, accountID);
            PStatement.executeUpdate();
            //statement.execute("UPDATE BankAccount SET Balance = '" + amount + "' WHERE ID = '" + accountID + "'");
        } catch (SQLException ex) {
            System.out.println("Error; updateAccountBalance; SQL exception");
            ex.printStackTrace();
        }
    }

    public String storeCustomerInfo(String ID, String name, String phoneNo, String address, String email) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("UPDATE Customer SET name = (?), phonenumber = (?), address = (?), email = (?) WHERE ID = (?)");
            PStatement.setString(1, name);
            PStatement.setString(2, phoneNo);
            PStatement.setString(3, address);
            PStatement.setString(4, email);
            PStatement.setString(5, ID);
            PStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error; storeCustomerInfo; SQL exception");
            ex.printStackTrace();
            return "false";
        }
        return "true";
    }

    public String login(String ID, String password) {

        String id = ID.toLowerCase();
        String loginResult = "";
        boolean isValid = false;
        //Query for admin login
        if (id.startsWith("a")) {
            ResultSet result = null;
            try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
                PreparedStatement PStatement = db.prepareStatement("SELECT password FROM admin WHERE id = (?)");
                PStatement.setString(1, ID);
                result = PStatement.executeQuery();
                StringBuilder sb = new StringBuilder();
                while (result.next()) {
                    sb.append(result.getString("password"));
                }
                loginResult = sb.toString();
                String[] hashAndSalt = loginResult.split(":");
                String hashedDB = hashAndSalt[0];
                String salt = hashAndSalt[1];
                isValid = validatePassword(password, hashedDB, salt);
            } catch (SQLException ex) {
                System.out.println("Error; login(admin); SQL exception");
                ex.printStackTrace();
            } finally {
                try {
                    result.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } // Query for customer login
        else if (id.startsWith("c")) {
            ResultSet result = null;
            try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
                PreparedStatement PStatement = db.prepareStatement("SELECT password FROM customer WHERE id = (?) AND isActive = true");
                PStatement.setString(1, ID);
                result = PStatement.executeQuery();
                StringBuilder sb = new StringBuilder();
                while (result.next()) {
                    sb.append(result.getString("password"));
                }
                loginResult = sb.toString();
                String[] hashAndSalt = loginResult.split(":");
                if (hashAndSalt.length > 1){
                String hashedDB = hashAndSalt[0];
                String salt = hashAndSalt[1];
                isValid = validatePassword(password, hashedDB, salt);
                }
            } catch (SQLException ex) {
                System.out.println("Error; login(client); SQL exception");
                ex.printStackTrace();
            } finally {
                try {
                    result.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (isValid) {
            return "true";
        } else {
            return "false";
        }
    }

    public void updatePassword(String ID, String password) {
        String hashedPassword = hashPassword(password);
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("UPDATE customer SET password = (?) WHERE id = (?)");
            PStatement.setString(1, hashedPassword);
            PStatement.setString(2, ID);
            PStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error; updatePassword; SQL exception");
            ex.printStackTrace();
        }
    }

    public String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password) {
        password = hashPassword(password);
        Date date = java.sql.Date.valueOf(birthday);
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("INSERT INTO customer (id, name, birthday, phonenumber, address, email, password) VALUES(?,?,?,?,?,?,?)");
            PStatement.setString(1, ID);
            PStatement.setString(2, name);
            PStatement.setDate(3, date);
            PStatement.setString(4, phonenumber);
            PStatement.setString(5, address);
            PStatement.setString(6, email);
            PStatement.setString(7, password);
            PStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error; createCustomer; SQL exception");
            ex.printStackTrace();
            return "false";
        }
        return "true";

    }

    public void openAccount(String ID) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("UPDATE customer SET isactive = true WHERE id = (?)");
            PStatement.setString(1, ID);
            PStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error; openAccount; SQL exception");
            ex.printStackTrace();
        }
    }

    public void closeAccount(String ID) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("UPDATE customer SET isactive = false WHERE ID = (?)");
            PStatement.setString(1, ID);
            PStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error; closeAccount; SQL exception");
            ex.printStackTrace();
        }
    }

    public String saveTransfer(String fromAccount, String toAccount, int amount, String text, LocalDateTime date) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("INSERT INTO transaction (message, amount, senderbankaccountid, receiverbankaccountid, date) VALUES (?,?,?,?,?)");
            PStatement.setString(1, text);
            PStatement.setInt(2, amount);
            PStatement.setString(3, fromAccount);
            PStatement.setString(4, toAccount);
            Timestamp timestamp = Timestamp.valueOf(date);
            PStatement.setTimestamp(5, timestamp); //Mulig fejl
            PStatement.executeUpdate();
            //String s = "INSERT INTO transaction (message, amount, senderbankaccountid, receiverbankaccountid, date)"
            //        + " VALUES ('" + text + "','" + amount + "','" + fromAccount + "','" + toAccount + "','" + date + "')";
        } catch (SQLException ex) {
            System.out.println("Error; saveTransfer; SQL exception");
            ex.printStackTrace();
            return "false";
        }
        return "true";
    }

    public String getTransactionHistory(String accountID) {
        String testResult = "";
        ResultSet result = null;
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            PreparedStatement PStatement = db.prepareStatement("SELECT * FROM transaction WHERE senderbankaccountid = (?) OR receiverbankaccountid = (?)");
            PStatement.setString(1, accountID);
            PStatement.setString(2, accountID);
            result = PStatement.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                String history = String.format("%-12s%-18s%-25s%8s    %s;",
                        result.getString("receiverbankaccountid").replace(" ", ""),
                        result.getString("senderbankaccountid").replace(" ", ""),
                        result.getString("date").substring(0, 16), result.getString("amount"), result.getString("message"));
                sb.append(history);
            }
            testResult = sb.toString();
        } catch (SQLException ex) {
            System.out.println("Error; getTransactionHistory; SQL exception");
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return testResult;
    }

    public String hashPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        byte[] hash = null;
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65537, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            try {
                hash = factory.generateSecret(spec).getEncoded();
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DatatypeConverter.printHexBinary(hash) + ":" + DatatypeConverter.printHexBinary(salt);
    }

    public boolean validatePassword(String originalPassword, String hashedPasswordDB, String saltDB) {
        byte[] saltDBInByte = DatatypeConverter.parseHexBinary(saltDB);
        byte[] hash = null;
        KeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), saltDBInByte, 65537, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            try {
                hash = factory.generateSecret(spec).getEncoded();
                String hex = DatatypeConverter.printHexBinary(hash);
                if (hex.equals(hashedPasswordDB)) {
                    return true;
                }
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public String checkBankAccountID(String ID) {
         ResultSet result = null;
         Random random = new Random();
         int i1 = random.nextInt(90000) + 10000;
         int i2 = random.nextInt(90000) + 10000;
         String bankaccountID = "6969" + i1 +i2;
         
         boolean test = true;
         
         while (test) {            
                
         try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
             
             
            PreparedStatement PStatement = db.prepareStatement("SELECT id FROM bankaccount WHERE ? = id");
            PStatement.setString(1, bankaccountID);
            
            result = PStatement.executeQuery();
            if(result.next()){
                i1 = random.nextInt(90000) + 10000;
                i2 = random.nextInt(90000) + 10000;
                bankaccountID = "6969" + i1 + i2;
                System.out.println(bankaccountID + "in while loop");
                db.close();
                test = true;
            }
            else {
                
                openBankAccount(ID, bankaccountID);
                
                test = false;
            
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        finally{
             try {
                 result.close();
             } catch (SQLException ex) {
                 Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
         }
         return "Complete";
        

    }
    
    public void openBankAccount(String ID, String bankAccountID){
        ResultSet result = null;
       
        
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            System.out.println("test5");
            
            PreparedStatement PStatement = db.prepareStatement("INSERT INTO bankaccount(id, balance) VALUES (?, ?); INSERT INTO hasbankaccount(id, bankaccountid) VALUES(?, ?)");
            PStatement.setString(1, bankAccountID);
            PStatement.setInt(2, 0);
            PStatement.setString(3, ID);
            PStatement.setString(4, bankAccountID);
            PStatement.executeUpdate();
            
            
           
            
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        
    
    
    }

//main method for testing
//    public static void main(String[] args) {
//        DBManager db = new DBManager();
//        String[] hashAndSalt = db.hashPassword("HEJsa").split(":");
//        System.out.println("HASH IN MAIN"+hashAndSalt[0]);
//        System.out.println("HASH SALT IN MAIN" +hashAndSalt[1]);
//        String hashedDB = hashAndSalt[0];
//        String salt = hashAndSalt[1];
//        db.validatePassword("HEJsa", hashedDB, salt);
    //System.out.println(db.getTest());
    //db.setTest("mytest1","mytest2");
    //System.out.println(db.getTest());
//    }
}
