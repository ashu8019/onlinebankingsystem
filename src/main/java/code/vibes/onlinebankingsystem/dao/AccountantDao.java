package code.vibes.onlinebankingsystem.dao;

import code.vibes.onlinebankingsystem.entity.Accountant;
import code.vibes.onlinebankingsystem.exception.AccountantException;
import code.vibes.onlinebankingsystem.exception.CustomerException;

public interface AccountantDao {

    public Accountant loginAccountant(String accountantUsername, String accountantPassword)
            throws AccountantException;

    public int addCustomer(String customerName, String customerMail,
                           String customerPassword, String customerMobile,
                           String customerAddress) throws CustomerException;

    // Corrected spelling
    public String addAccount(int customerBalance, int cid)
            throws CustomerException;

    public String updateCustomer(int customerAccountNumber, String customerAddress)
            throws CustomerException;

    public String deleteAccount(int customerAccountNumber)
            throws CustomerException;

    // 🔥 FIXED: changed return type to void
    public void viewAllCustomer()
            throws CustomerException;
}
