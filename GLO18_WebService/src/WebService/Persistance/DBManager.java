package WebService.Persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

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

    public String login(String ID, String password) {

        String id = ID.toLowerCase();
        String loginResult = "";
        //Query for admin login
        if (id.startsWith("a")) {

            try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
                ResultSet result = statement.executeQuery("SELECT password FROM admin WHERE id = '" + ID + "'");

                StringBuilder sb = new StringBuilder();
                while (result.next()) {
                    sb.append(result.getString("password"));
                }
                loginResult = sb.toString();
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
            } catch (SQLException ex) {
                System.out.println("SQL exception");
                ex.printStackTrace();
            }
        }
        if (loginResult.equals(password)) {
            return "true";
        } else {
            return "false";
        }
    }

    public String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password) {
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

//main method for testing
//    public static void main(String[] args) {
//        DBManager db = new DBManager();
//        System.out.println(db.getTest());
//        db.setTest("mytest1","mytest2");
//        System.out.println(db.getTest());
//        
//    }
}
