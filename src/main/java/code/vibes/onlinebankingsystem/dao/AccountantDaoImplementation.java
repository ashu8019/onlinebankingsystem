package code.vibes.onlinebankingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import code.vibes.onlinebankingsystem.databaseconnection.DatabaseConnection;
import code.vibes.onlinebankingsystem.entity.Accountant;
import code.vibes.onlinebankingsystem.exception.AccountantException;
import code.vibes.onlinebankingsystem.exception.CustomerException;

public class AccountantDaoImplementation implements AccountantDao {

    @Override
    public Accountant loginAccountant(String accountantUsername, String accountantPassword)
            throws AccountantException {

        try (Connection conn = DatabaseConnection.provideConnection()) {

            String query = "SELECT * FROM accountant WHERE accountantUsername = ? AND accountantPassword = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, accountantUsername);
            ps.setString(2, accountantPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Accountant(
                        rs.getString("accountantUsername"),
                        rs.getString("accountantEmail"),
                        rs.getString("accountantPassword"));
            } else {
                throw new AccountantException("Invalid Username or Password");
            }

        } catch (SQLException e) {
            throw new AccountantException("Database Error: " + e.getMessage());
        }
    }

    @Override
    public int addCustomer(String customerName, String customerMail,
                           String customerPassword, String customerMobile,
                           String customerAddress) throws CustomerException {

        try (Connection conn = DatabaseConnection.provideConnection()) {

            String query = "INSERT INTO customerinformation "
                    + "(customerName, customerMail, customerPassword, customerMobile, customerAddress) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, customerName);
            ps.setString(2, customerMail);
            ps.setString(3, customerPassword);
            ps.setString(4, customerMobile);
            ps.setString(5, customerAddress);

            int result = ps.executeUpdate();

            if (result > 0) {

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new CustomerException("Customer Not Added");

        } catch (SQLException e) {
            throw new CustomerException("Database Error: " + e.getMessage());
        }
    }

    @Override
    public String addAccount(int customerBalance, int cid) throws CustomerException {

        try (Connection conn = DatabaseConnection.provideConnection()) {

            String query = "INSERT INTO account (customerBalance, cid) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, customerBalance);
            ps.setInt(2, cid);

            int x = ps.executeUpdate();

            if (x > 0) {
                return "Account Added Successfully";
            } else {
                throw new CustomerException("Account Not Added");
            }

        } catch (SQLException e) {
            throw new CustomerException("Database Error: " + e.getMessage());
        }
    }

    @Override
    public String updateCustomer(int customerAccountNumber, String customerAddress)
            throws CustomerException {

        try (Connection conn = DatabaseConnection.provideConnection()) {

            String query = "UPDATE customerinformation i "
                    + "JOIN account a ON i.cid = a.cid "
                    + "SET i.customerAddress = ? "
                    + "WHERE a.customerAccountNumber = ?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, customerAddress);
            ps.setInt(2, customerAccountNumber);

            int x = ps.executeUpdate();

            if (x > 0) {
                return "Address Updated Successfully";
            } else {
                throw new CustomerException("Customer Update Failed");
            }

        } catch (SQLException e) {
            throw new CustomerException("Database Error: " + e.getMessage());
        }
    }

    @Override
    public String deleteAccount(int customerAccountNumber)
            throws CustomerException {

        try (Connection conn = DatabaseConnection.provideConnection()) {

            String query = "DELETE FROM account WHERE customerAccountNumber = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, customerAccountNumber);

            int x = ps.executeUpdate();

            if (x > 0) {
                return "Account Deleted Successfully";
            } else {
                throw new CustomerException("Deletion Failed: Account Not Found");
            }

        } catch (SQLException e) {
            throw new CustomerException("Database Error: " + e.getMessage());
        }
    }

    // 🔥 FULLY FIXED METHOD
    @Override
    public void viewAllCustomer() throws CustomerException {

        try (Connection conn = DatabaseConnection.provideConnection()) {

            String query = "SELECT * FROM customerinformation i "
                    + "INNER JOIN account a ON a.cid = i.cid";

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("***************************************");
                System.out.println("Account Number: " + rs.getInt("customerAccountNumber"));
                System.out.println("Customer Name: " + rs.getString("customerName"));
                System.out.println("Customer Balance: " + rs.getInt("customerBalance"));
                System.out.println("Customer Mail: " + rs.getString("customerMail"));
                System.out.println("Customer Mobile: " + rs.getString("customerMobile"));
                System.out.println("Customer Address: " + rs.getString("customerAddress"));
                System.out.println("***************************************");
            }

            if (!found) {
                System.out.println("No Customers Found!");
            }

        } catch (SQLException e) {
            throw new CustomerException("Error Fetching Customers: " + e.getMessage());
        }
    }
}
