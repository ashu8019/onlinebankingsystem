package code.vibes.onlinebankingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import code.vibes.onlinebankingsystem.databaseconnection.DatabaseConnection;
import code.vibes.onlinebankingsystem.entity.Customer;
import code.vibes.onlinebankingsystem.exception.CustomerException;

public class CustomerDaoImplementation implements CustomerDao {

    @Override
    public Customer loginCustomer(String customerUsername,
                                  String customerPassword,
                                  int accountNumber)
            throws CustomerException {

        Customer customer = null;

        try (Connection conn = DatabaseConnection.provideConnection()) {

            String query = "SELECT * FROM customerinformation i "
                    + "INNER JOIN account a ON i.cid = a.cid "
                    + "WHERE i.customerName = ? "
                    + "AND i.customerPassword = ? "
                    + "AND a.customerAccountNumber = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, customerUsername);
            ps.setString(2, customerPassword);
            ps.setInt(3, accountNumber);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = new Customer(
                        rs.getInt("customerAccountNumber"),
                        rs.getString("customerName"),
                        rs.getInt("customerBalance"),
                        customerPassword,
                        rs.getString("customerMail"),
                        rs.getString("customerMobile"),
                        rs.getString("customerAddress")
                );
            } else {
                throw new CustomerException("Invalid Customer Details!");
            }

        } catch (SQLException e) {
            throw new CustomerException("Database Error: " + e.getMessage());
        }

        return customer;
    }

    @Override
    public int viewBalance(int customerAccountNumber)
            throws CustomerException {

        try (Connection conn = DatabaseConnection.provideConnection()) {

            String query = "SELECT customerBalance FROM account WHERE customerAccountNumber = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, customerAccountNumber);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("customerBalance");
            } else {
                throw new CustomerException("Account not found!");
            }

        } catch (SQLException e) {
            throw new CustomerException("Database Error: " + e.getMessage());
        }
    }

    @Override
    public int Deposit(int customerAccountNumber, int amount)
            throws CustomerException {

        if (amount <= 0)
            throw new CustomerException("Deposit amount must be positive!");

        try (Connection conn = DatabaseConnection.provideConnection()) {

            String query = "UPDATE account SET customerBalance = customerBalance + ? WHERE customerAccountNumber = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, amount);
            ps.setInt(2, customerAccountNumber);

            int rows = ps.executeUpdate();

            if (rows == 0)
                throw new CustomerException("Account not found!");

        } catch (SQLException e) {
            throw new CustomerException("Database Error: " + e.getMessage());
        }

        return viewBalance(customerAccountNumber);
    }

    @Override
    public int withdraw(int customerAccountNumber, int amount)
            throws CustomerException {

        if (amount <= 0)
            throw new CustomerException("Withdrawal amount must be positive!");

        int balance = viewBalance(customerAccountNumber);

        if (balance < amount)
            throw new CustomerException("Insufficient Balance");

        try (Connection conn = DatabaseConnection.provideConnection()) {

            String query = "UPDATE account SET customerBalance = customerBalance - ? WHERE customerAccountNumber = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, amount);
            ps.setInt(2, customerAccountNumber);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new CustomerException("Database Error: " + e.getMessage());
        }

        return viewBalance(customerAccountNumber);
    }

    @Override
    public int Transfer(int fromAccountNumber, int amount, int toAccountNumber)
            throws CustomerException {

        if (amount <= 0)
            throw new CustomerException("Transfer amount must be positive!");

        int senderBalance = viewBalance(fromAccountNumber);

        if (senderBalance < amount)
            throw new CustomerException("Insufficient Balance");

        // Check receiver account exists
        viewBalance(toAccountNumber);

        withdraw(fromAccountNumber, amount);
        Deposit(toAccountNumber, amount);

        return viewBalance(fromAccountNumber);
    }
}
