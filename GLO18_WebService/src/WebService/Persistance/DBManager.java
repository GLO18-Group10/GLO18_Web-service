package WebService.Persistance;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

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
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            String s = "insert into testTable values('" + c1 + "','" + c2 + "')";
            statement.execute(s);

        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
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
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT name, birthday, phonenumber, address, email FROM customer WHERE id = '" + customerID + "'");

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
            System.out.println("SQL exception");
            ex.printStackTrace();
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
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return IDs;
    }

    public String getAccountNos(String customerID) {
        String accountNos = "";
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT bankaccountid FROM hasbankaccount WHERE id = '" + customerID + "'");

            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(result.getString("bankaccountid") + ";");
            }
            accountNos = sb.toString();
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        accountNos = accountNos.replace(" ", "");
        return accountNos;
    }

    public String getAccountBalance(String accountID) {
        String Balance = "";
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT Balance FROM BankAccount WHERE ID = '" + accountID + "'");

            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(result.getString("Balance"));
            }
            Balance = sb.toString();
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return Balance;
    }

    public boolean doesAccountExist(String accountID) {
        String count = "0";
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT count(*) FROM BankAccount WHERE ID = '" + accountID + "'");
            result.next();
            count = result.getString(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count.equals("1");
    }

    public void updateAccountBalance(String accountID, int amount) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            statement.execute("UPDATE BankAccount SET Balance = '" + amount + "' WHERE ID = '" + accountID + "'");
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
    }
public String storeCustomerInfo(String ID, String name, String phoneNo, String address, String email) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            statement.execute("UPDATE Customer SET name = '" + name + "', " + "phonenumber = '" + phoneNo + "', address = '"+address + "', email = '" + email + "' WHERE ID = '" + ID + "'");
        } catch (SQLException ex) {
            System.out.println("SQL exception");
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

            try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
                ResultSet result = statement.executeQuery("SELECT password FROM admin WHERE id = '" + ID + "'");

                StringBuilder sb = new StringBuilder();
                while (result.next()) {
                    sb.append(result.getString("password"));
                }
                loginResult = sb.toString();
                String[]hashAndSalt = loginResult.split(":");
                String hashedDB = hashAndSalt[0];
                String salt = hashAndSalt[1];
                isValid = validatePassword(password, hashedDB, salt);
            } catch (SQLException ex) {
                System.out.println("SQL exception");
                ex.printStackTrace();
            }
        } // Query for customer login
        else if (id.startsWith("c")) {

            try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
                ResultSet result = statement.executeQuery("SELECT password FROM customer WHERE id = '" + ID + "'");

                StringBuilder sb = new StringBuilder();
                while (result.next()) {
                    sb.append(result.getString("password"));
                }
                loginResult = sb.toString();
                String[]hashAndSalt = loginResult.split(":");
                String hashedDB = hashAndSalt[0];
                String salt = hashAndSalt[1];
                isValid = validatePassword(password, hashedDB, salt);
            } catch (SQLException ex) {
                System.out.println("SQL exception");
                ex.printStackTrace();
            }
        }
        if (isValid) {
            return "true";
        } else {
            return "false";
        }
    }

    public String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password) {
        password = hashPassword(password);
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            String s = "INSERT INTO customer (id, name, birthday, phonenumber, address, email, password) VALUES ('" + ID + "','" + name + "','" + birthday + "','" + phonenumber + "','" + address + "','" + email + "','" + password + "')";
            statement.execute(s);

        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
            return "false";
        }
        return "true";

    }

    public void openAccount(String ID) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            String s = "UPDATE customer SET isactive = true WHERE id = '" + ID + "'";
            statement.execute(s);

        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
    }

    public void closeAccount(String ID) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            String s = "UPDATE customer SET isactive = false WHERE id = '" + ID + "'";
            statement.execute(s);

        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
    }

    public String saveTransfer(String fromAccount, String toAccount, int amount, String text, LocalDateTime date) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            String s = "INSERT INTO transaction (message, amount, senderbankaccountid, receiverbankaccountid, date)"
                    + " VALUES ('" + text + "','" + amount + "','" + fromAccount + "','" + toAccount + "','" + date + "')";
            statement.execute(s);
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
            return "false";
        }
        return "true";

    }
    

    public String getTransactionHistory(String accountID) {
        String testResult = "";
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM transaction WHERE senderbankaccountid = '" + accountID + "'  OR receiverbankaccountid = '" + accountID + "'" );

            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(result.getString("receiverbankaccountid") + "     ");
                sb.append(result.getString("senderbankaccountid") + "         ");
                sb.append(result.getString("date") + "             ");
                sb.append(result.getString("amount") + "                          ");
                sb.append(result.getString("message") + ";");
                }
            testResult = sb.toString();
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return testResult;
    }
    
    public String hashPassword(String password){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        byte[] hash = null;
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(),salt,65537, 128);
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

    public boolean validatePassword(String originalPassword, String hashedPasswordDB, String saltDB){
        byte[] saltDBInByte = DatatypeConverter.parseHexBinary(saltDB);
        byte[] hash = null;
        KeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),saltDBInByte,65537, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            try {
                hash = factory.generateSecret(spec).getEncoded();
                String hex = DatatypeConverter.printHexBinary(hash);
                if(hex.equals(hashedPasswordDB)){
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
