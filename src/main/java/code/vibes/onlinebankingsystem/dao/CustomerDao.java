package code.vibes.onlinebankingsystem.dao;

import code.vibes.onlinebankingsystem.entity.Customer;
import code.vibes.onlinebankingsystem.exception.CustomerException;

public interface CustomerDao {

    public Customer loginCustomer(String customerUsername,
                                  String customerPassword,
                                  int accountNumber)
            throws CustomerException;

    public int viewBalance(int customerAccountNumber)
            throws CustomerException;

    public int Deposit(int customerAccountNumber, int amount)
            throws CustomerException;

    public int withdraw(int customerAccountNumber, int amount)
            throws CustomerException;

    public int Transfer(int fromAccountNumber, int amount, int toAccountNumber)
            throws CustomerException;
}
